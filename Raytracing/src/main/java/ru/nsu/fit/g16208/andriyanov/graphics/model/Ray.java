package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Vector;

public class Ray {
    Vector from;
    Vector direction;
    float[] intense = { 0, 0, 0 };

    Ray(Vector from, Vector direction) {
        this.from = from;
        this.direction = direction;
    }
}
