package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Matrix;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Projection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Shape implements Drawable3D {
    private List<Edge3D> edgeList = new ArrayList<>();
    private Color LINE_COLOR = Color.GREEN;

    @Override
    public Shape transform(Matrix matrix) {
        Shape result = new Shape();
        for (Edge3D e : edgeList) {
            result.addEdge(e.transform(matrix));
        }
        return result;
    }

    @Override
    public void projectTo2D(Projection projector, BufferedImage image, Matrix parentMatrix) {
        for (Edge3D e : edgeList) {
            e.transform(parentMatrix).projectTo2D(projector, image, LINE_COLOR);
        }
    }

    public void addEdge(Edge3D edge) {
        edgeList.add(edge);
    }

    public List<Edge3D> getEdgeList() {
        return edgeList;
    }

}
