package ru.nsu.fit.g16208.andriyanov.graphics.view;

import ru.nsu.fit.g16208.andriyanov.graphics.model.Camera;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Separator;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Spline;
import ru.nsu.fit.g16208.andriyanov.graphics.model.SplineManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.stream.Stream;

import static java.lang.Math.round;

public class SplineImage extends BufferedImage {

    private final static int COUNT = 100;

    private SplineManager splineOwner = SplineManager.getInstance();

    SplineImage(int width, int height, Point2D from, Point2D to, Spline spline) {
        super(width, height, TYPE_INT_RGB);

        double scaleX = (width - 1) / (to.getX() - from.getX());
        double scaleY = (height - 1) / (to.getY() - from.getY());

        Graphics2D graphics2D = createGraphics();
        graphics2D.setColor(Camera.getInstance().getColor());
        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        graphics2D.setStroke(new BasicStroke(0.5f));
        graphics2D.setColor(Color.LIGHT_GRAY);

        graphics2D.drawLine(0, height / 2, width, height / 2);
        graphics2D.drawLine(width / 2, 0, width / 2, height);

        graphics2D.setStroke(new BasicStroke(2f));

        if (splineOwner.getLengthFrom() > 0) {
            graphics2D.setColor(Color.DARK_GRAY);
            Separator sequence = new Separator(0, splineOwner.getLengthFrom(), COUNT);
            Stream.generate(() -> spline.getPointAtLength(sequence.next()))
                    .limit(COUNT)
                    .reduce((p1, p2) -> {
                        graphics2D.drawLine((int) round((p1.getX() - from.getX()) * scaleX),
                                (int) round((p1.getY() - from.getY()) * scaleY),
                                (int) round((p2.getX() - from.getX()) * scaleX),
                                (int) round((p2.getY() - from.getY()) * scaleY));
                        return p2;
                    });
        }

        if (splineOwner.getLengthFrom() < splineOwner.getLengthTo()) {
            graphics2D.setColor(spline.getColor());
            Separator sequence1 = new Separator(splineOwner.getLengthFrom(), splineOwner.getLengthTo(), COUNT);
            Stream.generate(() -> spline.getPointAtLength(sequence1.next()))
                    .limit(COUNT)
                    .reduce((p1, p2) -> {
                        graphics2D.drawLine((int) round((p1.getX() - from.getX()) * scaleX),
                                (int) round((p1.getY() - from.getY()) * scaleY),
                                (int) round((p2.getX() - from.getX()) * scaleX),
                                (int) round((p2.getY() - from.getY()) * scaleY));
                        return p2;
                    });
        }

        if (splineOwner.getLengthTo() < 1) {
            graphics2D.setColor(Color.DARK_GRAY);
            Separator sequence2 = new Separator(splineOwner.getLengthTo(), 1, COUNT);
            Stream.generate(() -> spline.getPointAtLength(sequence2.next()))
                    .limit(COUNT)
                    .reduce((p1, p2) -> {
                        graphics2D.drawLine((int) round((p1.getX() - from.getX()) * scaleX),
                                (int) round((p1.getY() - from.getY()) * scaleY),
                                (int) round((p2.getX() - from.getX()) * scaleX),
                                (int) round((p2.getY() - from.getY()) * scaleY));
                        return p2;
                    });
        }
    }
}
