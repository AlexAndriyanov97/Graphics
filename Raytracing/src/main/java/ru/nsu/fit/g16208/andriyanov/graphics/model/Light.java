package ru.nsu.fit.g16208.andriyanov.graphics.model;

public class Light {
    private Vector position;
    private float[] intense;

    public Light(Vector position, float[] intense) {
        this.position = position;
        this.intense = intense;
    }

    Vector getPosition() {
        return position;
    }

    float[] getIntense() {
        return intense;
    }
}
