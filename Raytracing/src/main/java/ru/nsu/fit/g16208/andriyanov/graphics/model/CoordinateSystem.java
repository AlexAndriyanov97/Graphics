package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Matrix;

public class CoordinateSystem {

    private Matrix rotation;
    private Matrix translation;

    private double thetaX;
    private double thetaY;
    private double thetaZ;

    private Point3D center;


    public CoordinateSystem(Matrix rotation, Point3D center) {
        this.rotation = rotation;
        this.center = center;

        updateTranslation();
    }

    public void rotateY(double theta) {
        thetaX += theta;
        Matrix rotMatrix = new Matrix(4, 4,
                new double[]{
                        1., 0., 0., 0.,
                        0., Math.cos(theta), -Math.sin(theta), 0.,
                        0., Math.sin(theta), Math.cos(theta), 0.,
                        0., 0., 0., 1.
                });
        rotation = rotMatrix.multiply(rotation);
    }

    public void rotateX(double theta) {
        thetaY += theta;
        Matrix rotMatrix = new Matrix(4, 4,
                new double[]{
                        Math.cos(theta), 0., Math.sin(theta), 0.,
                        0., 1., 0., 0.,
                        -Math.sin(theta), 0., Math.cos(theta), 0.,
                        0., 0., 0., 1.
                });
        rotation = rotMatrix.multiply(rotation);
    }

    public void reset(){
        rotation = new Matrix(4,4,new double[]{
                1,0,0,0,
                0,1,0,0,
                0,0,-1,0,
                0,0,0,-1
        });
    }

    public void rotateZ(double theta) {
        thetaZ += theta;
        Matrix rotMatrix = new Matrix(4, 4,
                new double[]{
                        Math.cos(theta), -Math.sin(theta), 0., 0.,
                        Math.sin(theta), Math.cos(theta), 0., 0.,
                        0., 0., 1., 0.,
                        0., 0., 0., 1
                });
        rotation = rotMatrix.multiply(rotation);
    }

    public void moveTo(Point3D point) {
        center = point;
        updateTranslation();
    }

    public Point3D getCenter() {
        return center;
    }

    public Matrix getMatrix() {
        return rotation.multiply(translation    );
    }

    public void setThetaX(double thetaX) {
        rotateX(thetaX - this.thetaX);
    }

    public void setThetaY(double thetaY) {
        rotateY(thetaY - this.thetaY);
    }

    public void setThetaZ(double thetaZ) {
        rotateZ(thetaZ - this.thetaZ);
    }

    public double getThetaX() {
        return Math.atan2(rotation.get(2,1), rotation.get(2,2));
    }

    public double getThetaY() {
        return Math.atan2(
                -rotation.get(2,0),
                Math.sqrt(Math.pow(rotation.get(2,1), 2) +
                        Math.pow(rotation.get(2,2), 2)));
    }

    public double getThetaZ() {
        return Math.atan2(rotation.get(1,0), rotation.get(0,0));
    }

    private void updateTranslation() {
        translation = new Matrix(4, 4,
                new double[]{
                        1., 0., 0., -center.getX(),
                        0., 1., 0., -center.getY(),
                        0., 0., 1., -center.getZ(),
                        0., 0., 0., 1.
                });
    }


    public Matrix getRotation() {
        return rotation;
    }
}
