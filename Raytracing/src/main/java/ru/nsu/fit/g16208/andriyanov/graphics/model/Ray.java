package ru.nsu.fit.g16208.andriyanov.graphics.model;

import java.util.List;

public class Ray {
    private Point3D startPoint;
    private Point3D direction;

    public Ray(Point3D startPoint, Point3D direction) {
        this.startPoint = startPoint;
        this.direction =scale(direction, 1 / direction.norm());
    }

    public Point3D get(double t) {
        return new Point3D(
                startPoint.getX() + direction.getX() * t,
                startPoint.getY() + direction.getY() * t,
                startPoint.getZ() + direction.getZ() * t
        );
    }

    public List<Double> findIntersection(Renderable renderable) {
        return renderable.findIntersection(this);
    }

    public Point3D getStartPoint() {
        return startPoint;
    }

    public Point3D getDirection() {
        return direction;
    }

    public static Point3D scale(Point3D p, double k) {
        return new Point3D(p.getX() * k, p.getY() * k, p.getZ() * k);
    }


}
