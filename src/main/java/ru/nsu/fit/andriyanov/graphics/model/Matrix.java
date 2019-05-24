package main.java.ru.nsu.fit.andriyanov.graphics.model;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Matrix {
    private double[][] matrix;
    private int m;
    private int n;

    public Matrix(double[][] matrix) {
        this.m = matrix.length;
        this.n = matrix[0].length;

        for (int i = 0; i < m; i++) {
            if (matrix[i].length != n) {
                throw new IllegalArgumentException("Rows have not same size");
            }
        }
        this.matrix = matrix;

    }

    public Matrix(int m, int n) {
        this.m = m;
        this.n = n;
        this.matrix = new double[m][n];
    }

    public Matrix(Matrix matrix) {
        this.matrix = matrix.matrix;
        this.m = matrix.m;
        this.n = matrix.n;
    }

    public double getValue(int i,int j){
        return matrix[i][j];
    }

    public static Matrix getSingleMatrix() {
        Matrix singleMatrix = new Matrix(4, 4);

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                singleMatrix.matrix[i][j] = i == j ? 1.0D : 0.0D;
            }
        }
        return singleMatrix;
    }

    public Matrix rotateX(double angle) {
        Matrix rotateMatrix = new Matrix(new double[][]{
                {cos(angle), 0, sin(angle), 0},
                {0, 1, 0, 0},
                {-sin(angle), 0, cos(angle), 0},
                {0, 0, 0, 1}});
        matrix = rotateMatrix.mainRotateFunc(this);
        this.m = matrix.length;
        this.n = matrix[0].length;
        return this;
    }

    public Matrix rotateY(double angle) {
        Matrix rotateMatrix = new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, cos(angle), -sin(angle), 0},
                {0, sin(angle), cos(angle), 0},
                {0, 0, 0, 1}});
        matrix = rotateMatrix.mainRotateFunc(this);
        this.m = matrix.length;
        this.n = matrix[0].length;
        return this;
    }

    public Matrix rotateZ(double angle) {
        Matrix rotateMatrix = new Matrix(new double[][]{
                {cos(angle), -sin(angle), 0, 0},
                {sin(angle), cos(angle), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}});
        matrix = rotateMatrix.mainRotateFunc(this);
        this.m = matrix.length;
        this.n = matrix[0].length;
        return this;
    }


    public Matrix resize(double value) {
        Matrix resizeMatrix = new Matrix(new double[][]{
                {value, 0, 0, 0},
                {0, value, 0, 0},
                {0, 0, value, 0},
                {0, 0, 0, 1}});
        matrix = resizeMatrix.mainRotateFunc(this);
        this.m = matrix.length;
        this.n = matrix[0].length;
        return this;
    }

    public Matrix shift(Vector vec) {
        Matrix shiftMatrix = new Matrix(new double[][]{
                { 1, 0, 0, vec.getX() },
                { 0, 1, 0, vec.getY() },
                { 0, 0, 1, vec.getZ() },
                { 0, 0, 0, 1             } });
        matrix = shiftMatrix.mainRotateFunc(this);
        this.m = matrix.length;
        this.n = matrix[0].length;
        return this;
    }

    public Matrix apply(Matrix newMatrix){
        matrix = newMatrix.mainRotateFunc(this);
        this.m = matrix.length;
        this.n = matrix[0].length;
        return this;
    }

    public Matrix rotate(double angleX, double angleY, double angleZ) {
        rotateX(angleX);
        rotateY(angleY);
        rotateZ(angleZ);
        return this;
    }

    private double[][] mainRotateFunc(Matrix var1) {
        Matrix resultMatrix = new Matrix(this.m, var1.n);
        double[][] valuesOfMatrix = resultMatrix.matrix;
        double[] vector = new double[this.n];

        for (int i = 0; i < var1.n; ++i) {
            int j;
            for (j = 0; j < this.n; ++j) {
                vector[j] = var1.matrix[j][i];
            }

            for (j = 0; j < this.m; ++j) {
                double[] vectorC = this.matrix[j];
                double sum = 0.0D;

                for (int k = 0; k < this.n; ++k) {
                    sum += vectorC[k] * vector[k];
                }

                valuesOfMatrix[j][i] = sum;
            }
        }

        return resultMatrix.matrix;

    }

    public double[][] getValues() {
        return matrix;
    }

}
