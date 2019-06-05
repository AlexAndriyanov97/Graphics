package ru.nsu.fit.g16208.andriyanov.graphics.model;

public class Vector extends Matrix {

    public Vector(double x, double y, double z) {
        super(new double[][]{{x}, {y}, {z}, {1}});
    }

    public Vector(Matrix matrix) {
        super(matrix);
    }


    public double getX() {
        return this.getValue(0, 0);
    }

    public double getY() {
        return this.getValue(1, 0);
    }

    public double getZ() {
        return this.getValue(2, 0);
    }


    public Vector normalize() {
        double norm = getMatrix(0, 2, 0, 0).norm();

        return this.resize(1 / norm);
    }

    public Vector crossProduct(Vector other) {
        Matrix matrix = new Vector(getY() * other.getZ() - getZ() * other.getY(),
                getZ() * other.getX() - getX() * other.getZ(),
                getX() * other.getY() - getY() * other.getX());

        instance(matrix);
        return this;
    }

    public Vector translateTo(Translation translation) {
        return apply(translation);
    }

    public Vector translateFrom(Translation translation) {
        Matrix transposed = translation. transpose();
        Matrix newMatrix = new Matrix(transposed.mainRotateFunc(this));
        instance(newMatrix);
        return this;
    }

    public Vector copy() {
        return new Vector(super.copy());
    }

    public static Vector zero() {
        return new Vector(0, 0, 0);
    }

    @Override
    public Vector resize(double value) {
        return (Vector) super.resize(value);
    }

    @Override
    public Vector shift(Vector vec) {
        return (Vector) super.shift(vec);
    }

    @Override
    public Vector apply(Matrix newMatrix) {
        return (Vector) super.apply(newMatrix);
    }

    @Override
    public Vector rotate(double angleX, double angleY, double angleZ) {
        return (Vector) super.rotate(angleX, angleY, angleZ);
    }


    public Vector project(Projection projection) {
        double z = getZ();

        apply(projection);
        Matrix matrix = mult(1 / z);
        instance(matrix);
        return this;
    }
}
