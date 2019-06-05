package ru.nsu.fit.g16208.andriyanov.graphics.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Spline {
    private Color color;
    {
        Random random = new Random();
        color = new Color(random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255));
    }

    private ArrayList<Point2D> points = new ArrayList<>();
    private ArrayList<Segment> segments = new ArrayList<>();

    public static Spline getEmptySpline() {
        Spline spline = new Spline();
        spline.points.clear();
        spline.segments.clear();

        return spline;
    }

    public Spline() {
        addPoint(new Point2D.Double(0.9165151, -0.4));
        addPoint(new Point2D.Double(0.9797959, -0.2));
        addPoint(new Point2D.Double(1.0, 0.0));
        addPoint(new Point2D.Double(0.9797959, 0.2));
    }

    public void addPoint(Point2D point) {
        points.add(point);

        if (points.size() < 4)
            return;

        Point2D[] segPoints = points.stream()
                .skip(points.size() - 4)
                .toArray(Point2D[]::new);
        segments.add(new Segment(segPoints));
    }

    public void removePoint() {
        if (points.size() <= 4)
            return;

        points.remove(points.size() - 1);
        segments.remove(segments.size() - 1);
    }

    public List<Point2D> getPoints() {
        return points;
    }

    public Point2D getPointAtLength(double t) {
        double[] lengths = segments.stream()
                .mapToDouble(Segment::getLength)
                .toArray();

        double pos = t * Arrays.stream(lengths).sum();

        int k = 0;
        while (k < lengths.length - 1 && pos > lengths[k])
            pos -= lengths[k++];

        Segment segment = segments.get(k);
        return segment.getP(pos / lengths[k]);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
