package main.java.ru.nsu.fit.andriyanov.graphics.view;

import main.java.ru.nsu.fit.andriyanov.graphics.model.Camera;
import main.java.ru.nsu.fit.andriyanov.graphics.model.Separator;
import main.java.ru.nsu.fit.andriyanov.graphics.model.Spline;
import main.java.ru.nsu.fit.andriyanov.graphics.model.SplineManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.stream.Stream;

import static java.lang.Math.round;

public class SplineImage extends BufferedImage {

    private final static int COUNT = 100;

    private SplineManager splineOwner = SplineManager.getInstance();

    SplineImage(int width, int height, Point2D from, Point2D to, Spline spline) {
        super(width, height, TYPE_INT_RGB);

        double scaleX = (width - 1) / (to.getX() - from.getX());
        double scaleY = (height - 1) / (to.getY() - from.getY());

        Graphics2D g2D = createGraphics();
        g2D.setColor(Camera.getInstance().getColor());
        g2D.fillRect(0, 0, getWidth(), getHeight());

        g2D.setStroke(new BasicStroke(0.5f));
        g2D.setColor(Color.LIGHT_GRAY);

        g2D.drawLine(0, height / 2, width, height / 2);
        g2D.drawLine(width / 2, 0, width / 2, height);

        g2D.setStroke(new BasicStroke(2f));

        if (splineOwner.getLengthFrom() > 0) {
            g2D.setColor(Color.DARK_GRAY);
            Separator sequence = new Separator(0, splineOwner.getLengthFrom(), COUNT);
            Stream.generate(() -> spline.getPointAtLength(sequence.next()))
                    .limit(COUNT)
                    .reduce((p1, p2) -> {
                        g2D.drawLine((int) round((p1.getX() - from.getX()) * scaleX),
                                (int) round((p1.getY() - from.getY()) * scaleY),
                                (int) round((p2.getX() - from.getX()) * scaleX),
                                (int) round((p2.getY() - from.getY()) * scaleY));
                        return p2;
                    });
        }

        if (splineOwner.getLengthFrom() < splineOwner.getLengthTo()) {
            g2D.setColor(spline.getColor());
            Separator sequence1 = new Separator(splineOwner.getLengthFrom(), splineOwner.getLengthTo(), COUNT);
            Stream.generate(() -> spline.getPointAtLength(sequence1.next()))
                    .limit(COUNT)
                    .reduce((p1, p2) -> {
                        g2D.drawLine((int) round((p1.getX() - from.getX()) * scaleX),
                                (int) round((p1.getY() - from.getY()) * scaleY),
                                (int) round((p2.getX() - from.getX()) * scaleX),
                                (int) round((p2.getY() - from.getY()) * scaleY));
                        return p2;
                    });
        }

        if (splineOwner.getLengthTo() < 1) {
            g2D.setColor(Color.DARK_GRAY);
            Separator sequence2 = new Separator(splineOwner.getLengthTo(), 1, COUNT);
            Stream.generate(() -> spline.getPointAtLength(sequence2.next()))
                    .limit(COUNT)
                    .reduce((p1, p2) -> {
                        g2D.drawLine((int) round((p1.getX() - from.getX()) * scaleX),
                                (int) round((p1.getY() - from.getY()) * scaleY),
                                (int) round((p2.getX() - from.getX()) * scaleX),
                                (int) round((p2.getY() - from.getY()) * scaleY));
                        return p2;
                    });
        }
    }
}
