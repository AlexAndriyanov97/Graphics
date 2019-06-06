package ru.nsu.fit.g16208.andriyanov.graphics.wireframe;

import ru.nsu.fit.g16208.andriyanov.graphics.model.Edge3D;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Point3D;
import ru.nsu.fit.g16208.andriyanov.graphics.model.RenderModel;

import java.awt.*;

public class Projection extends Camera {
    private Matrix projectionMatrix;

    public Projection(RenderModel renderModel, int width, int height) {
        super(renderModel);
        projectionMatrix = new Matrix(4, 4, new double[]{
                width / 2, 0., 0., width / 2,
                0., -height / 2, 0., height / 2,
                0., 0., 1., 0.,
                0., 0., 0., 1
        });
    }

    public Point projectPoint(Point3D worldPoint) {
        Point3D cameraCoord = toCameraCoordinateSystem(worldPoint);

        Point3D halfCubePoint = cameraCoord.transform(halfCubeMatrix);
        Point3D projectedPoint = halfCubePoint.transform(projectionMatrix);

        return new Point((int) projectedPoint.getX(), (int) projectedPoint.getY());
    }

    public Edge projectEdge(Edge3D worldEdge) {
        Point3D camPoint1 = toCameraCoordinateSystem(worldEdge.p1);
        Point3D camPoint2 = toCameraCoordinateSystem(worldEdge.p2);

        Point3D halfCubePoint1 = camPoint1.transform(halfCubeMatrix);
        Point3D halfCubePoint2 = camPoint2.transform(halfCubeMatrix);

        if (isInHalfCube(halfCubePoint1) && isInHalfCube(halfCubePoint2)) {
            Point3D projectedPoint1 = halfCubePoint1.transform(projectionMatrix);
            Point3D projectedPoint2 = halfCubePoint2.transform(projectionMatrix);
            return new Edge(
                    new Point((int) projectedPoint1.getX(), (int) projectedPoint1.getY()),
                    new Point((int) projectedPoint2.getX(), (int) projectedPoint2.getY())
            );
        }

        Point3D minPoint = halfCubePoint1.getZ() < halfCubePoint2.getZ() ? halfCubePoint1 : halfCubePoint2;
        Point3D maxPoint = halfCubePoint1.getZ() >= halfCubePoint2.getZ() ? halfCubePoint1 : halfCubePoint2;

        if (maxPoint.getZ() <= -1) {
            return null;
        }
        if (minPoint.getZ() < -1) {
            minPoint = findIntersection(
                    new Matrix(4, 1, new double[]{0., 0., 1., 1.}),
                    minPoint,
                    maxPoint
            );
        }

        if (minPoint.getZ() >= 0) {
            return null;
        }
        if (maxPoint.getZ() > 0) {
            maxPoint = findIntersection(
                    new Matrix(4, 1, new double[]{0., 0., 1., 0.}),
                    minPoint,
                    maxPoint
            );
        }

        Point3D projectedPoint1 = minPoint.transform(projectionMatrix);
        Point3D projectedPoint2 = maxPoint.transform(projectionMatrix);
        return new Edge(
                new Point((int) projectedPoint1.getX(), (int) projectedPoint1.getY()),
                new Point((int) projectedPoint2.getX(), (int) projectedPoint2.getY())
        );
    }

    public static Point3D findIntersection(Matrix plane, Point3D p1, Point3D p2) {
        double l = p1.getX() - p2.getX();
        double m = p1.getY() - p2.getY();
        double n = p1.getZ() - p2.getZ();

        double[] pArr = plane.getMatrixArray();
        double pointOnPlane = pArr[0] * p1.getX() + pArr[1] * p1.getY() + pArr[2] * p1.getZ() + pArr[3];
        double scalarProduct = l * pArr[0] + m * pArr[1] + n * pArr[2];
        if (scalarProduct == 0) {
            return null;
        }

        double tmpValue = pointOnPlane / scalarProduct;
        double x = p1.getX() - l * tmpValue;
        double y = p1.getY() - m * tmpValue;
        double z = p1.getZ() - n * tmpValue;

        return new Point3D(x, y, z);
    }
}
