package ru.nsu.fit.g16208.andriyanov.graphics.wireframe;

import ru.nsu.fit.g16208.andriyanov.graphics.model.RenderModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Screen3D extends JPanel {

    private final static int BORDER = 20;
    private BufferedImage canvas;
    private Camera camera;
    private Scene scene;


    private int size;

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

    public void update(){
        size = Math.min(getWidth(),getHeight())-BORDER*2;
        if(size<=0){
            return;
        }

        int width = size;
        int height = size;
        setSize(width,height);
        SwingUtilities.invokeLater(this::repaint);
    }


}

