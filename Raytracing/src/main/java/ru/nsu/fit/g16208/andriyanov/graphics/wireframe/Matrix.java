package ru.nsu.fit.g16208.andriyanov.graphics.wireframe;

import ru.nsu.fit.g16208.andriyanov.graphics.model.Point3D;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Matrix {
    private int width;
    private int height;
    private double[] matrixArray;

    public Matrix(int width, int height, double[] matrixArray) {
        this.width = width;
        this.height = height;
        this.matrixArray = matrixArray;
    }

    public Matrix multiply(Matrix other) {
        int nWidth = other.width;
        int nHeight = height;

        double[] result = new double[nWidth * nHeight];
        double[] otherArray = other.matrixArray;

        for (int i = 0; i < nHeight; i++) {
            for (int j = 0; j < nWidth; j++) {
                for (int k = 0; k < width; k++) {
                    result[i * nWidth + j] += matrixArray[i * width + k] * otherArray[k * nWidth + j];
                }
            }
        }
        return new Matrix(nWidth, nHeight, result);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                stringBuilder.append(matrixArray[i * width + j]).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public double[] getMatrixArray() {
        return matrixArray;
    }

    public Matrix transpose() {
        double[] transposeArray = new double[height * width];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                transposeArray[i * height + j] = matrixArray[j * width + i];
            }
        }
        return new Matrix(height, width, transposeArray);
    }

    public Point3D toPoint3D() {
        if (width != 1 || height < 3) {
            return null;
        }

        double coef = 1.;
        if (height == 4) {
            coef = matrixArray[3];
        }

        return new Point3D(
                matrixArray[0] / coef,
                matrixArray[1] / coef,
                matrixArray[2] / coef
        );
    }

    public static Matrix getSingleMatrix() {
        return new Matrix(4,4, new double[]{
                1., 0., 0., 0.,
                0., 1., 0., 0.,
                0., 0., 1., 0.,
                0., 0., 0., 1.
        });
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double get(int i, int j) {
        return matrixArray[i*width + j];
    }
}