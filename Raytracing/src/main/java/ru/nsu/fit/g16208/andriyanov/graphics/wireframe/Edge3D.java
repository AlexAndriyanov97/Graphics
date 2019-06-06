package ru.nsu.fit.g16208.andriyanov.graphics.wireframe;

import ru.nsu.fit.g16208.andriyanov.graphics.model.Point3D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Edge3D {

    public Point3D p1;
    public Point3D p2;

    public Edge3D(Point3D p1, Point3D p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void projectTo2D(Camera.Projector projector, BufferedImage image, Color color) {
        Graphics g = image.getGraphics();
        g.setColor(color);
        Point np1 = projector.projectPoint(p1);
        Point np2 = projector.projectPoint(p2);
        g.drawLine(np1.x, np1.y, np2.x, np2.y);
    }

    public Edge3D transform(Matrix matrix) {
        Point3D newP1 = p1.transform(matrix);
        Point3D newP2 = p2.transform(matrix);
        return new Edge3D(newP1, newP2);
    }
}
