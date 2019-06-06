package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Matrix;

import java.util.ArrayList;
import java.util.List;

public class Quadrangle extends RenderableBaseImpl {
    private Point3D[] points;
    private Triangle triangle1;
    private Triangle triangle2;

    public Quadrangle(Point3D... points) {
        this.points = points;

        triangle1 = new Triangle(points[0], points[1], points[2]);
        triangle2 = new Triangle(points[0], points[2], points[3]);

        addEdge(new Edge3D(points[0], points[1]));
        addEdge(new Edge3D(points[1], points[2]));
        addEdge(new Edge3D(points[2], points[3]));
        addEdge(new Edge3D(points[3], points[0]));
    }

    @Override
    public Quadrangle transform(Matrix matrix) {
        Quadrangle result = new Quadrangle(
                points[0].transform(matrix),
                points[1].transform(matrix),
                points[2].transform(matrix),
                points[3].transform(matrix)
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
        List<Double> intersection = triangle1.findIntersection(ray);
        if (intersection.size() == 1) {
            return intersection;
        }
        intersection = triangle2.findIntersection(ray);
        if (intersection.size() == 1) {
            return intersection;
        }
        return new ArrayList<>();
    }

    @Override
    public Point3D getNormal(Point3D point) {
        return triangle1.getNormal(point);
    }
}

