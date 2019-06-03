package main.java.ru.nsu.fit.andriyanov.graphics.model;

public class ChangedPoint {

    private double x;
    private double y;
    private double value;

    public ChangedPoint(double x, double y, double value) {
        this.x=x;
        this.y=y;
        this.value=value;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getValue() {
        return value;
    }
}
