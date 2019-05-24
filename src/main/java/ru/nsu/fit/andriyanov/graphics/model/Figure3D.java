package main.java.ru.nsu.fit.andriyanov.graphics.model;

import java.awt.*;
import java.util.ArrayList;

public class Figure3D {
    private Matrix rotation = Matrix.getSingleMatrix();
    private Vector center = Vector.zero();

    private Edge[] axises = new Edge[3];
    private ArrayList<Edge> edges = new ArrayList<>();


    public static Figure3D getBrick(double sizeX, double sizeY, double sizeZ) {
        Vector p000 = new Vector( sizeX,  sizeY,  sizeZ);
        Vector p001 = new Vector( sizeX,  sizeY, -sizeZ);
        Vector p010 = new Vector( sizeX, -sizeY,  sizeZ);
        Vector p011 = new Vector( sizeX, -sizeY, -sizeZ);
        Vector p100 = new Vector(-sizeX,  sizeY,  sizeZ);
        Vector p101 = new Vector(-sizeX,  sizeY, -sizeZ);
        Vector p110 = new Vector(-sizeX, -sizeY,  sizeZ);
        Vector p111 = new Vector(-sizeX, -sizeY, -sizeZ);

        return new Figure3D()
                .addEdge(new Edge(p000, p001))
                .addEdge(new Edge(p000, p010))
                .addEdge(new Edge(p000, p100))
                .addEdge(new Edge(p001, p011))
                .addEdge(new Edge(p001, p101))
                .addEdge(new Edge(p010, p011))
                .addEdge(new Edge(p010, p110))
                .addEdge(new Edge(p011, p111))
                .addEdge(new Edge(p100, p101))
                .addEdge(new Edge(p100, p110))
                .addEdge(new Edge(p101, p111))
                .addEdge(new Edge(p110, p111));
    }

    private Figure3D() {
        axises[0] = new Edge(
                Vector.zero(),
                new Vector(0.2, 0,0), Color.RED);

        axises[1] = new Edge(
                Vector.zero(),
                new Vector(0, 0.2, 0), Color.GREEN);

        axises[2] = new Edge(
                Vector.zero(),
                new Vector(0, 0, 0.2), Color.BLUE);
    }


    public void rotate(Matrix matrix) {
        rotation.apply(matrix);
    }

    public void shift(Vector vector) {
        center.shift(vector);
    }


    protected void clear() {
        edges.clear();
    }

    private Figure3D addEdge(Edge edge) {
        Edge tmp = new Edge(
                edge.getPoints()[0].copy(),
                edge.getPoints()[1].copy(), edge.getColor());
        edges.add(tmp);

        return this;
    }


    public Matrix getRotation() {
        return rotation;
    }

    public Vector getCenter() {
        return center;
    }


}
