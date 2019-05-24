package main.java.ru.nsu.fit.andriyanov.graphics.model;

import java.awt.*;
import java.util.Observable;

public class Camera extends Observable {

    private double frontZ = 5;
    private double backZ = frontZ + 3;

    private Matrix rotation = Matrix.getSingleMatrix();

    private double width = 1;
    private double height = 1;


    public final Vector position = new Vector(-10, 0, 0);
    private final Vector viewPoint = new Vector(0, 0, 0);
    private final Vector up = new Vector(0, 1, 0);

    private Color color = Color.WHITE;

    public Camera() {
    }


    public void move(double offset) {
        double moveZ = -offset * 0.05;

        if (frontZ + moveZ >= 1 && frontZ + moveZ <= 10 &&
                backZ + moveZ >= 1 && backZ + moveZ <= 10) {
            frontZ += moveZ;
            backZ += moveZ;
        }

        notifyObservers();
    }

    public void rotate(double angleX, double angleY) {
        rotation.rotate(angleX, angleY, 0);
    }

    public void reset() {
        rotation = Matrix.getSingleMatrix();
    }



    public void setRotation(Matrix rotation) {
        this.rotation = rotation;
    }

    public void setFrontZ(double frontZ) {
        this.frontZ = frontZ;

        notifyObservers();
    }

    public double getFrontZ() {
        return frontZ;
    }

    public void setBackZ(double backZ) {
        this.backZ = backZ;

        notifyObservers();
    }

    public double getBackZ() {
        return backZ;
    }



    public void setWidth(double width) {
        this.width = width;

        notifyObservers();
    }

    public double getWidth() {
        return width;
    }



    public void setHeight(double height) {
        this.height = height;

        notifyObservers();
    }

    public double getHeight() {
        return height;
    }


    public void setColor(Color color) {
        this.color = color;

        notifyObservers();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }

}
