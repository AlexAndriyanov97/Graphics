package ru.nsu.fit.g16208.andriyanov.graphics.view;

import ru.nsu.fit.g16208.andriyanov.graphics.model.Spline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class SplinePanel extends JPanel {


    private final static int DIM = 10;
    private Point2D start;
    private Point2D end;

    private Spline spline;

    private JPanel imagePanel;

    SplinePanel(Spline spline,
                Runnable updateAction, Runnable removeAction) {
        this.spline = spline;
        recountBorders();

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        createImagePanel(updateAction);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.BOTH;
        add(imagePanel, constraints);


        JButton removeFigureButton = new JButton("remove figure");
        removeFigureButton.addActionListener(e -> removeAction.run());

        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.05;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        add(removeFigureButton, constraints);

        JButton colorButton = new JButton("color");
        colorButton.addActionListener(e -> {
            spline.setColor(JColorChooser.showDialog(this, "Spline color", spline.getColor()));
            updateAction.run();
        });

        add(colorButton, constraints);

        JButton removePointButton = new JButton("remove point");
        removePointButton.addActionListener(e -> {
            spline.removePoint();
            updateAction.run();
        });

        add(removePointButton, constraints);
    }

    private void recountBorders() {
        double maxX = 1.0;
        double maxY = 1.0;
        for (Point2D point2D : spline.getPoints()) {
            maxX = Double.max(maxX, Math.abs(point2D.getX()));
            maxY = Double.max(maxY, Math.abs(point2D.getY()));
        }

        double max = Double.max(maxX, maxY);
        max += 2 * DIM / ((double) getWidth()) * max;

        start = new Point2D.Double(-max, -max);
        end = new Point2D.Double(max, max);
    }

    private Point2D formatToScreen(Point2D oldPoint) {
        double kX = imagePanel.getWidth() / (end.getX() - start.getX());
        double kY = imagePanel.getHeight() / (end.getY() - start.getY());

        return new Point2D.Double((oldPoint.getX() - start.getX()) * kX,
                (oldPoint.getY() - start.getY()) * kY);
    }

    private Point2D formatToSpline(Point2D oldPoint) {
        double kX = (end.getX() - start.getX()) / imagePanel.getWidth();
        double kY = (end.getY() - start.getY()) / imagePanel.getHeight();

        return new Point2D.Double(oldPoint.getX() * kX + start.getX(),
                oldPoint.getY() * kY + start.getY());
    }

    private Point2D findPoint(Point2D point) {
        return spline.getPoints().stream()
                .filter(splinePoint -> formatToScreen(splinePoint).distance(point) < DIM / 2)
                .findFirst().orElse(null);
    }

    private void createImagePanel(Runnable updateAction) {
        final Point2D[] closest = { null };

        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                recountBorders();

                BufferedImage image = new SplineImage(getWidth(), getHeight(), start, end, spline);
                g.drawImage(image, 0, 0, this);

                spline.getPoints().forEach(point2D -> {
                    if (point2D == closest[0])
                        g.setColor(Color.RED);
                    else
                        g.setColor(Color.WHITE);

                    Point2D formatted = formatToScreen(point2D);
                    g.drawOval((int) formatted.getX() - DIM / 2,
                            (int) formatted.getY() - DIM / 2,
                            DIM, DIM);
                });
            }
        };

        final Point2D[] lastPoint = { null };

        imagePanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastPoint[0] == null)
                    return;

                lastPoint[0].setLocation(formatToSpline(e.getPoint()));
                closest[0] = lastPoint[0];

                updateAction.run();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                closest[0] = findPoint(e.getPoint());
                imagePanel.repaint();
            }
        });

        imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON3)
                    return;

                Point2D newPoint = formatToSpline(e.getPoint());
                spline.addPoint(newPoint);

                closest[0] = newPoint;
                updateAction.run();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1)
                    return;

                lastPoint[0] = findPoint(e.getPoint());
                closest[0] = lastPoint[0];
                imagePanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1)
                    return;

                lastPoint[0] = null;
            }
        });
    }
}
