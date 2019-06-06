package ru.nsu.fit.g16208.andriyanov.graphics.wireframe;

import ru.nsu.fit.g16208.andriyanov.graphics.model.RenderModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class Screen3D extends JPanel {
    private BufferedImage canvas;
    private Camera camera;
    private Scene scene;

    private Point anchor = null;

    public Screen3D(Camera camera, Scene scene) {
        this.camera = camera;
        this.scene = scene;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                anchor = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                onMouseDragged(e);
            }
        });

        addMouseWheelListener(this::onMouseWheel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        updateCanvas();
        g.drawImage(canvas, 0, 0, canvas.getWidth(), canvas.getHeight(), this);
    }

    private void updateCanvas() {
        canvas = camera.getImage(scene, getSize().width, getSize().height);
    }

    private void onMouseDragged(MouseEvent e) {
        Point currentPoint = e.getPoint();
        int dx = currentPoint.x - anchor.x;
        int dy = currentPoint.y - anchor.y;

        double dFiY = (double) dx * 2 * Math.PI / getSize().width;
        double dFiX = (double) dy * 2 * Math.PI / getSize().height;

        scene.rotateZ(dFiX);
        scene.rotateY(dFiY);
        repaint();
        anchor = currentPoint;
    }

    private void onMouseWheel(MouseWheelEvent e) {
        RenderModel model = camera.getRenderModel();
        model.setZn(model.getZn() * (1 + e.getWheelRotation() * 0.1));
        model.setZf(model.getZf() * (1 + e.getWheelRotation() * 0.1));
        repaint();
    }


}

