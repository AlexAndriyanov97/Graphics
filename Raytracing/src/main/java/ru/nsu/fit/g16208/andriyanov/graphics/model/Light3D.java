package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Camera;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Matrix;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Light3D implements Drawable3D {
    private Point3D position;
    private double red;
    private double green;
    private double blue;

    public Light3D(Point3D position, double red, double green, double blue) {
        this.position = position;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Point3D getPosition() {
        return position;
    }

    public double getIntensity(RGB channel) {
        switch (channel) {
            case RED: return red;
            case GREEN: return green;
            case BLUE: return blue;
            default: return 0;
        }
    }

    @Override
    public Light3D transform(Matrix matrix) {
//        position = position.transform(matrix);
//        return this;
        return new Light3D(position.transform(matrix), red, green, blue);
    }

    @Override
    public void projectTo2D(Camera.Projector projector, BufferedImage image, Matrix parentMatrix) {
        double size = 0.2;
        Edge3D e1 = new Edge3D(
                sum(position, new Point3D(0,0,-size)),
                sum(position, new Point3D(0,0,size)));
        Edge3D e2 = new Edge3D(
                sum(position, new Point3D(0,-size, 0)),
                sum(position, new Point3D(0,size,0)));
        Edge3D e3 = new Edge3D(
                sum(position, new Point3D(-size,0, 0)),
                sum(position, new Point3D(size,0,0)));

        e1.transform(parentMatrix).projectTo2D(projector, image, Color.RED);
        e2.transform(parentMatrix).projectTo2D(projector, image, Color.RED);
        e3.transform(parentMatrix).projectTo2D(projector, image, Color.RED);
    }

    private   Point3D sum(Point3D p1, Point3D p2) {
        return new Point3D(
                p1.getX() + p2.getX(),
                p1.getY() + p2.getY(),
                p1.getZ() + p2.getZ()
        );
    }


    public void updatePosition(Matrix matrix) {
        position = position.transform(matrix);
    }
}

