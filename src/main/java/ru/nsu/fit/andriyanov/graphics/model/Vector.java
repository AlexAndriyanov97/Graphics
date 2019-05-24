package main.java.ru.nsu.fit.andriyanov.graphics.model;

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


    public Vector copy() {
        return new Vector(copy());
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
}
