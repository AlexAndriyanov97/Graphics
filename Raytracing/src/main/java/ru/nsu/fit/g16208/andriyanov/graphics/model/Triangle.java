package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Matrix;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Triangle extends RenderableBaseImpl {
    private Point3D[] points;
    private Point3D normal;

    public Triangle(Point3D... points) {
        this.points = points;

        addEdge(new Edge3D(points[0], points[1]));
        addEdge(new Edge3D(points[1], points[2]));
        addEdge(new Edge3D(points[2], points[0]));
    }

    @Override
    public Triangle transform(Matrix matrix) {
        Triangle result = new Triangle(
                points[0].transform(matrix),
                points[1].transform(matrix),
                points[2].transform(matrix)
        );

        result.setDiffuseCoeff(RGB.RED, getDiffuseCoeff(RGB.RED));
        result.setDiffuseCoeff(RGB.GREEN, getDiffuseCoeff(RGB.GREEN));
        result.setDiffuseCoeff(RGB.BLUE, getDiffuseCoeff(RGB.BLUE));

        result.setMirroredCoef(RGB.RED, getMirroredCoeff(RGB.RED));
        result.setMirroredCoef(RGB.GREEN, getMirroredCoeff(RGB.GREEN));
        result.setMirroredCoef(RGB.BLUE, getMirroredCoeff(RGB.BLUE));

        return result;
    }

    @Override
    public List<Double> findIntersection(Ray ray) {
        Point3D normal = getNormal(points[0]);

        double dotProduct = dotProduct(normal, ray.getDirection());
        if (dotProduct >= 0) {
            return new ArrayList<>();
        }

        double d =
                -normal.getX() * points[0].getX()
                        - normal.getY() * points[0].getY()
                        - normal.getZ() * points[0].getZ();

        double t = -(dotProduct(normal, ray.getStartPoint()) + d);
        t = t / dotProduct;

        int longestAxis = findLongestAxis(normal);
        Point2D.Double points2D[] = new Point2D.Double[3];
        for (int i = 0; i < 3; i++) {
            points2D[i] = getPoint2D(points[i], longestAxis);
        }

        Point3D intersectionPoint = ray.get(t);
        Point2D.Double intersection2D = getPoint2D(intersectionPoint, longestAxis);

        double triangleArea = triangleArea(points2D[0], points2D[1], points2D[2]);
        double a = triangleArea(intersection2D, points2D[1], points2D[2]) / triangleArea;
        double b = triangleArea(points2D[0], intersection2D, points2D[2]) / triangleArea;
        double c = triangleArea(points2D[0], points2D[1], intersection2D) / triangleArea;

        if (a < 0 || b < 0 || c < 0) {
            return new ArrayList<>();
        }

        return Collections.singletonList(t);
    }

    public static double triangleArea(Point2D.Double a, Point2D.Double b, Point2D.Double c) {
        return 0.5 * ((b.x - a.x)*(c.y - a.y) - (c.x - a.x)*(b.y - a.y));
    }



    @Override
    public Point3D getNormal(Point3D point) {
        if (normal == null) {
            Point3D v1 = sum(
                    points[1],
                    scale(points[0], -1)
            );
            Point3D v2 = sum(
                    points[2],
                    scale(points[0], -1)
            );

            normal = (Point3D) v1.crossProduct(v2);
            normal = scale(normal, 1 / normal.norm());
        }
        return normal;
    }

    private int findLongestAxis(Point3D p) {
        double max = Math.max(p.getX(), Math.max(p.getY(), p.getZ()));
        if (max == p.getX()) {
            return 0;
        }
        if (max == p.getY()) {
            return 1;
        }
        return 2;
    }

    private Point2D.Double getPoint2D(Point3D p, int without) {
        switch (without) {
            case 0:
                return new Point2D.Double(p.getY(), p.getZ());
            case 1:
                return new Point2D.Double(p.getX(), p.getZ());
            case 2:
                return new Point2D.Double(p.getX(), p.getY());
            default:
                return null;
        }
    }

    public static Point3D scale(Point3D p, double k) {
        return new Point3D(p.getX() * k, p.getY() * k, p.getZ() * k);
    }



    public Point3D sum(Point3D p1, Point3D p2) {
        return new Point3D(
                p1.getX() + p2.getX(),
                p1.getY() + p2.getY(),
                p1.getZ() + p2.getZ()
        );
    }

    public static double dotProduct(Point3D p1, Point3D p2) {
        return p1.getX() * p2.getX() + p1.getY() * p2.getY() + p1.getZ() * p2.getZ();
    }
}
