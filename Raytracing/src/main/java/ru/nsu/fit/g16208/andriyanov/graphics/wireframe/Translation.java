package ru.nsu.fit.g16208.andriyanov.graphics.wireframe;

public class Translation extends Matrix{
    private static Matrix compute(Vector center, Vector axisX, Vector axisY, Vector axisZ) {
        Matrix matrix = new Matrix(new double[][]{
                { axisX.getX(), axisX.getY(), axisX.getZ(), 0 },
                { axisY.getX(), axisY.getY(), axisY.getZ(), 0 },
                { axisZ.getX(), axisZ.getY(), axisZ.getZ(), 0 },
                { 0,            0,            0,            1 }
        });

        Matrix shift = Matrix.getSingleMatrix().shift(center.copy().resize(-1));
        return new Matrix(matrix.mainRotateFunc(shift));
    }

    public Translation(Vector center, Vector axisX, Vector axisY, Vector axisZ) {
        super(compute(center, axisX, axisY, axisZ));
    }
}
