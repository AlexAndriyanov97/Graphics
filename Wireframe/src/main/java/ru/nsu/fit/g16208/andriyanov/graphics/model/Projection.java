package ru.nsu.fit.g16208.andriyanov.graphics.model;

public class Projection  extends Matrix {

    private static Matrix compute(double frontZ, double backZ, double width, double height) {
        double depth = backZ - frontZ;
        return new Matrix(new double[][]{
                { 2 * frontZ / width, 0,                   0,              0                       },
                { 0,                  2 * frontZ / height, 0,              0                       },
                { 0,                  0,                   frontZ / depth, -frontZ * backZ / depth },
                { 0,                  0,                   1,              0                       }
        });
    }

    public Projection(double frontZ, double backZ, double width, double height) {
        super(compute(frontZ, backZ, width, height));
    }
}
