package ru.nsu.fit.g16208.andriyanov.graphics.model;

import java.awt.*;

public class RenderModel extends Observable<RenderEvents>{
    private double zn = 5;
    private double zf = 15;
    private double sw = 10;
    private double sh = 10;

    private Point3D center = new Point3D(-10., 0., 0.);
    private Point3D up = new Point3D(0., 1., 0.);
    private Point3D direction = new Point3D(0., 0., 0.);
    private int Depth;

    private Color backgroundColor = Color.BLACK;

    public double getZn() {
        return zn;
    }

    public void setZn(double zn) {
        notifyListeners(RenderEvents.ZN_CHANGED, zn);
        this.zn = zn;
    }

    public double getZf() {
        return zf;
    }

    public void setZf(double zf) {
        notifyListeners(RenderEvents.ZF_CHANGED, zf);
        this.zf = zf;
    }


    public int getDepth() {
        return Depth;
    }

    public void setDepth(int depth) {
        Depth = depth;
    }

    public double getSw() {
        return sw;
    }

    public void setSw(double sw) {
        this.sw = sw;
    }

    public double getSh() {
        return sh;
    }

    public void setSh(double sh) {
        this.sh = sh;
    }

    public Point3D getCenter() {
        return center;
    }

    public void setCenter(Point3D center) {
        this.center = center;
    }

    public Point3D getUp() {
        return up;
    }

    public void setUp(Point3D up) {
        this.up = up;
    }

    public Point3D getDirection() {
        return direction;
    }

    public void setDirection(Point3D direction) {
        this.direction = direction;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}