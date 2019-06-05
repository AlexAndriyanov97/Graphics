package ru.nsu.fit.g16208.andriyanov.graphics.model;

import java.awt.geom.Point2D;
import java.util.stream.Stream;

public class Segment {


    private final static int LENGTH_COUNT = 20;

    private Point2D[] points;
    private double length;

    Segment(Point2D[] points) {
        this.points = points;
    }

    Point2D getP(double t) {
        // Полседние достижения индусских инженеров в области математики.
        double[] k = new double[]{
                -1 * t * t * t + 3 * t * t + -3 * t + 1,
                3 * t * t * t + -6 * t * t + 0 * t + 4,
                -3 * t * t * t + 3 * t * t + 3 * t + 1,
                1 * t * t * t + 0 * t * t + 0 * t + 0,
        };

        Point2D.Double result = new Point2D.Double();
        for (int i = 0; i < points.length; i++) {
            result.x += points[i].getX() * k[i] / 6;
            result.y += points[i].getY() * k[i] / 6;
        }

        return result;
    }

    double getLength() {
        Separator sequence = new Separator(0, 1, LENGTH_COUNT);
        length = 0;
        Stream.generate(() -> getP(sequence.next()))
                .limit(LENGTH_COUNT)
                .reduce((p1, p2) -> {
                    length += p1.distance(p2);
                    return p2;
                });
        return length;
    }
}
