package main.java.ru.nsu.fit.andriyanov.graphics.model;

import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Stream;

public class SplineFigure3D extends Figure3D implements Observer {


    private SplineManager splineOwner = SplineManager.getInstance();

    private Spline spline;

    SplineFigure3D(Spline spline) {
        this.spline = spline;
        recount();
    }

    private void recount() {
        clear();

        Separator baseSequence = new Separator(splineOwner.getLengthFrom(), splineOwner.getLengthTo(),
                splineOwner.getLengthCount());
        Stream.generate(baseSequence::next)
                .limit(splineOwner.getLengthCount())
                .reduce((l1, l2) -> {
                    Point2D first = spline.getPointAtLength(l1);
                    rotateVector(new Vector(first.getX(), -first.getY(), 0));

                    Separator offSequence = new Separator(l1, l2, splineOwner.getLengthK());
                    Stream.generate(offSequence::next)
                            .limit(splineOwner.getLengthK())
                            .map(length -> spline.getPointAtLength(length))
                            .map(point2D -> new Vector(point2D.getX(), -point2D.getY(), 0))
                            .reduce((v1, v2) -> {
                                rotateEdge(new Edge(v1, v2, spline.getColor()));
                                return v2;
                            });

                    return l2;
                });

        Point2D last = spline.getPointAtLength(splineOwner.getLengthTo());
        rotateVector(new Vector(last.getX(), -last.getY(), 0));
    }

    private void rotateVector(Vector vector) {
        Separator rotateSequence = new Separator(splineOwner.getRotateFrom(), splineOwner.getRotateTo(),
                splineOwner.getRotateCount() + 1);
        Stream.generate(rotateSequence::next)
                .limit(splineOwner.getRotateCount() + 1)
                .map(angle -> vector.copy().rotate(angle, 0, 0))
                .reduce((v1, v2) -> {
                    addEdge(new Edge(v1, v2, spline.getColor()));
                    return v2;
                });
    }

    private void rotateEdge(Edge edge) {
        Separator rotateSequence = new Separator(splineOwner.getRotateFrom(), splineOwner.getRotateTo(),
                splineOwner.getRotateCount() + 1);
        Stream.generate(rotateSequence::next)
                .limit(splineOwner.getRotateCount() + 1)
                .map(angle -> {
                    Vector p1 = edge.getPoints()[0].copy().rotate(angle, 0, 0);
                    Vector p2 = edge.getPoints()[1].copy().rotate(angle, 0, 0);

                    return new Edge(p1, p2, spline.getColor());
                })
                .forEach(this::addEdge);
    }

    @Override
    public void update(Observable o, Object arg) {
        recount();
    }

}
