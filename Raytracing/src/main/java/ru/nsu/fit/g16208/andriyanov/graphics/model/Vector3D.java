package ru.nsu.fit.g16208.andriyanov.graphics.model;

public interface Vector3D {
    Vector3D crossProduct(Vector3D other);
    Vector3D sum(Vector3D other);
    Vector3D multiply(double n);
    double norm();
}
