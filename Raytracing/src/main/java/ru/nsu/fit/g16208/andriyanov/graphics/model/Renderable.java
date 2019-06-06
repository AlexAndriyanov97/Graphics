package ru.nsu.fit.g16208.andriyanov.graphics.model;

import java.util.List;

public interface Renderable {
    List<Double> findIntersection(Ray ray);
    double getDiffuseCoeff(RGB color);
    double getMirroredCoeff(RGB color);
    double getPower();

    Point3D getNormal(Point3D point);
}