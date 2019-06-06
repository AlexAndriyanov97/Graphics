package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Matrix;

import java.util.List;

public class Box extends RenderableBaseImpl {
    private Point3D minPoint;
    private Point3D maxPoint;

    public Box(Point3D minPoint, Point3D maxPoint) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;

        double dx = maxPoint.getX() - minPoint.getX();
        double dy = maxPoint.getY() - minPoint.getY();
        double dz = maxPoint.getZ() - minPoint.getZ();

        final int VERTEX_NUMBER = 8;
        Point3D[] points = new Point3D[VERTEX_NUMBER];
        for (int vertexID = 0; vertexID < VERTEX_NUMBER; vertexID++) {
            double newX = minPoint.getX() + ((vertexID & 0b1) != 0 ? dx : 0);
            double newY = minPoint.getX() + ((vertexID & 0b10) != 0 ? dy : 0);
            double newZ = minPoint.getX() + ((vertexID & 0b100) != 0 ? dz : 0);
            points[vertexID] = new Point3D(newX, newY, newZ);
        }

        addEdge(new Edge3D(points[0], points[1]));
        addEdge(new Edge3D(points[0], points[2]));
        addEdge(new Edge3D(points[0], points[4]));

        addEdge(new Edge3D(points[1], points[3]));
        addEdge(new Edge3D(points[1], points[5]));

        addEdge(new Edge3D(points[2], points[6]));
        addEdge(new Edge3D(points[2], points[3]));

        addEdge(new Edge3D(points[4], points[5]));
        addEdge(new Edge3D(points[4], points[6]));

        addEdge(new Edge3D(points[3], points[7]));
        addEdge(new Edge3D(points[5], points[7]));
        addEdge(new Edge3D(points[6], points[7]));

    }

    @Override
    public Box transform(Matrix matrix) {
        Box result = new Box(minPoint, maxPoint);
        List<Edge3D> edgeList = getEdgeList();
        for (Edge3D e : edgeList) {
            result.addEdge(e.transform(matrix));
        }
        return result;
    }

    @Override
    public List<Double> findIntersection(Ray ray) {
        return null;
    }

    @Override
    public Point3D getNormal(Point3D point) {
        return null;
    }
}