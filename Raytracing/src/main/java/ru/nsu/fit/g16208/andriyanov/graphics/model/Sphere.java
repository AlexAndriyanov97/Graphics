package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Figure3D;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Sphere extends RenderableBaseImpl {
    private final int n = 40;

    private List<Function<Double, Double>> functions = new ArrayList<>();

    private Point3D center;
    private double r;

    public Sphere(Point3D center, double r) {
        this.center = center;
        this.r = r;

        functions.add(0, (x) -> Math.sqrt(r * r - Math.pow(x - center.getX(), 2)) + center.getY());
        functions.add(1, (y) -> Math.sqrt(r * r - Math.pow(y - center.getY(), 2)) + center.getZ());
        functions.add(2, (x) -> Math.sqrt(r * r - Math.pow(x - center.getX(), 2)) + center.getZ());

        functions.add(3, (x) -> -Math.sqrt(r * r - Math.pow(x - center.getX(), 2)) + center.getY());
        functions.add(4, (y) -> -Math.sqrt(r * r - Math.pow(y - center.getY(), 2)) + center.getZ());
        functions.add(5, (x) -> -Math.sqrt(r * r - Math.pow(x - center.getX(), 2)) + center.getZ());

        for (int i = 0; i < 6; i++) {
            drawCircle(functions.get(i), (i + 2) % 3);
        }
    }

    private void drawCircle(Function<Double, Double> f, int fix) {
        double step = 2 * r / n;

        Point3D lastPoint = null;

        double[] axis = new double[3];

        axis[fix] = getCoord(center, fix);
        int nFix1 = 0;
        int nFix2 = 0;
        switch (fix) {
            case 0:
                nFix1 = 1;
                nFix2 = 2;
                break;
            case 1:
                nFix1 = 0;
                nFix2 = 2;
                break;
            case 2:
                nFix1 = 0;
                nFix2 = 1;
        }
        for (axis[nFix1] = getCoord(center, nFix1) - r; axis[nFix1] <= getCoord(center, nFix1) + r; axis[nFix1] += step) {
            axis[nFix2] = f.apply(axis[nFix1]);
            if (lastPoint == null) {
                lastPoint = new Point3D(axis[0], axis[1], axis[2]);
                continue;
            }
            Point3D newPoint = new Point3D(axis[0], axis[1], axis[2]);
            addEdge(new Edge3D(newPoint, lastPoint));
            lastPoint = newPoint;
        }
    }

    private Double getCoord(Point3D p, int index) {
        switch (index) {
            case 0:
                return p.getX();
            case 1:
                return p.getY();
            case 2:
                return p.getZ();
        }
        return null;
    }

    @Override
    public List<Double> findIntersection(Ray ray) {
        Point3D r0 = ray.getStartPoint();
        Point3D rd = ray.getDirection();

        double b = dotProduct(
                rd,
                sum(
                        r0,
                        scale(center, -1)
                )
        );

        Point3D tmpSub = sum(
                r0,
                scale(center, -1)
        );

        double c = dotProduct(tmpSub, tmpSub) - r*r;

        double d = b*b - c;

        if (d < 0) {
            return new ArrayList<>();
        }

        List<Double> result = new ArrayList<>();
        double t0 = -b - Math.sqrt(b*b - c);

        if (t0 >= 0) {
            result.add(t0);
            return result;
        }

        double t1 = -b + Math.sqrt(b*b - c);

        if (t1 >= 0) {
            result.add(t1);
            return result;
        }
        return result;
    }

    @Override
    public Sphere transform(Matrix matrix) {
        return new Sphere(center.transform(matrix), r);
    }


    @Override
    public Point3D getNormal(Point3D point) {
        Point3D normal = sum(
                point,
                scale(center, -1)
        );
        return scale(normal, 1 / normal.norm());
    }


    public static Point3D sum(Point3D p1, Point3D p2) {
        return new Point3D(
                p1.getX() + p2.getX(),
                p1.getY() + p2.getY(),
                p1.getZ() + p2.getZ()
        );
    }
    public static double dotProduct(Point3D p1, Point3D p2) {
        return p1.getX() * p2.getX() + p1.getY() * p2.getY() + p1.getZ() * p2.getZ();
    }
    public static Point3D scale(Point3D p, double k) {
        return new Point3D(p.getX() * k, p.getY() * k, p.getZ() * k);
    }



}