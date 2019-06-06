package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Camera;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Matrix;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Point3D implements Vector3D {
    private double x;
    private double y;
    private double z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(Matrix matrix) {
        this(
                matrix.getMatrixArray()[0],
                matrix.getMatrixArray()[1],
                matrix.getMatrixArray()[2]
        );
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Matrix toVector() {
        return new Matrix(1, 3, new double[]{
                x,
                y,
                z
        });
    }

    public Matrix toHomogeneousVector() {
        return new Matrix(1, 4, new double[]{
                x,
                y,
                z,
                1
        });
    }


    public void projectTo2D(Camera.Projector projector, BufferedImage image) {
        Point p = projector.projectPoint(this);
        image.setRGB(p.x, p.y, Color.WHITE.getRGB());
    }

    public Point3D transform(Matrix matrix) {
        return matrix.multiply(toHomogeneousVector()).toPoint3D();


    }

    @Override
    public Vector3D crossProduct(Vector3D other) {
        Point3D p = (Point3D) other;
        return new Point3D(
                y * p.z - z * p.y,
                z * p.x - x * p.z,
                x * p.y - y * p.x
        );
    }

    @Override
    public Vector3D sum(Vector3D other) {
        Point3D p = (Point3D) other;
        return new Point3D(
                x + p.x,
                y + p.y,
                z + p.z
        );
    }

    @Override
    public Vector3D multiply(double n) {
        return new Point3D(
                n * x,
                n * y,
                n * z
        );
    }

    @Override
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }
}