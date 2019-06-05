package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.controller.FigureController;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class Figure2D extends BufferedImage {

    private final static double FROM = -1;
    private final static double TO   = 1;

    private Translation translation;
    private Projection projection;

    public Figure2D(int width, int height, Matrix toScene, Camera camera, Figure3D figure3D) {
        super(width, height, TYPE_INT_ARGB);

        translation = new Translation(camera.position, camera.axisX, camera.axisY, camera.axisZ);
        projection = new Projection(camera.getFrontZ(), camera.getBackZ(),
                camera.getWidth(), camera.getHeight());

        double scaleX = width / (TO - FROM);
        double scaleY = height / (TO - FROM);

        Graphics2D g2D = createGraphics();
        if (figure3D == FigureController.getInstance().getFigure())
            g2D.setStroke(new BasicStroke(2));

        List<Edge> edges = figure3D.getEdges(toScene, camera);
        for (Edge edge : edges) {
            Point2D.Double[] edgeProjection = projectEdge(edge);
            if (edgeProjection == null)
                continue;

            int x1 = (int) Math.round((edgeProjection[0].getX() - FROM) * scaleX);
            int y1 = (int) Math.round((edgeProjection[0].getY() - FROM) * scaleY);
            int x2 = (int) Math.round((edgeProjection[1].getX() - FROM) * scaleX);
            int y2 = (int) Math.round((edgeProjection[1].getY() - FROM) * scaleY);

            g2D.setColor(edge.getColor());

            g2D.drawLine(x1, y1, x2, y2);
        }
    }

    private Point2D.Double[] projectEdge(Edge edge) {
        Vector from = edge.getPoints()[0].copy()
                .translateTo(translation)
                .project(projection);
        Vector to = edge.getPoints()[1].copy()
                .translateTo(translation)
                .project(projection);

        double[] x = { from.getX(), to.getX() };
        double[] y = { from.getY(), to.getY() };
        double[] z = { from.getZ(), to.getZ() };

        try {
            clipping(x, y, z, FROM, TO);
            clipping(y, x, z, FROM, TO);
            clipping(z, x, y, 0, 1);
        } catch (Exception e) {
            return null;
        }

        return new Point2D.Double[]{ new Point2D.Double(x[0], y[0]), new Point2D.Double(x[1], y[1]) };
    }

    private void clipping(double[] main, double[] off1, double[] off2,
                          double min, double max){

        int iMin = (main[0] < main[1]) ? 0 : 1;
        int iMax = 1 - iMin;

        if (main[iMin] < min) {
            refreshValue(main, off1, off2, min, iMin);
        }

        if (main[iMax] > max) {
            refreshValue(main, off1, off2, max, iMax);
        }
    }

    private void refreshValue(double[] main, double[] off1, double[] off2, double max, int iMax) {
        double k = (main[iMax] - max) / (main[1] - main[0]);
        off1[iMax] = off1[iMax] - k * (off1[1] - off1[0]);
        off2[iMax] = off2[iMax] - k * (off2[1] - off2[0]);
    }
}
