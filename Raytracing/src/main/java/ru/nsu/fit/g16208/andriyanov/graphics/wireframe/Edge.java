package ru.nsu.fit.g16208.andriyanov.graphics.wireframe;

import java.awt.*;

public class Edge {
    private Vector[] points = new Vector[2];
    private Color color = Color.WHITE;

    public Edge(Vector p1, Vector p2, Color color) {
        points[0] = p1;
        points[1] = p2;

        this.color = color;
    }

    public Edge(Vector p1, Vector p2) {
        points[0] = p1;
        points[1] = p2;
    }

    public Vector[] getPoints() {
        return points;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
