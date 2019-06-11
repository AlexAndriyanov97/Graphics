package ru.nsu.fit.g16208.andriyanov.graphics.wireframe;

import ru.nsu.fit.g16208.andriyanov.graphics.model.Drawable3D;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Point3D;
import ru.nsu.fit.g16208.andriyanov.graphics.model.RenderEvents;
import ru.nsu.fit.g16208.andriyanov.graphics.model.RenderModel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera {
    private Matrix translation;
    private Matrix rotation;
    private Matrix halfCubeMatrix;

    private RenderModel renderModel;

    public Camera(RenderModel renderModel) {
        this(renderModel.getCenter(), renderModel.getUp(), renderModel.getDirection());
        this.renderModel = renderModel;
        renderModel.registerListener(RenderEvents.ZN_CHANGED, this::znChanged);
        renderModel.registerListener(RenderEvents.ZF_CHANGED, this::zfChanged);
        updateHalfCubeMatrix();
    }

    private Camera(Point3D center, Point3D up, Point3D direction) {
        Point3D zAxis = (Point3D) center.sum(direction.multiply(-1));
        zAxis = (Point3D) zAxis.multiply(1. / zAxis.norm());

        Point3D xAxis = (Point3D) up.crossProduct(zAxis);
        xAxis = (Point3D) xAxis.multiply(1. / xAxis.norm());

        Point3D yAxis = (Point3D) zAxis.crossProduct(xAxis);
        yAxis = (Point3D) yAxis.multiply(1. / yAxis.norm());

        rotation = new Matrix(4, 4,
                new double[]{
                        xAxis.getX(), yAxis.getX(), zAxis.getX(), 0.,
                        xAxis.getY(), yAxis.getY(), zAxis.getY(), 0.,
                        xAxis.getZ(), yAxis.getZ(), zAxis.getZ(), 0.,
                        0., 0., 0., 1.
                }).transpose();
        translation = new Matrix(4, 4,
                new double[]{
                        1., 0., 0., -center.getX(),
                        0., 1., 0., -center.getY(),
                        0., 0., 1., -center.getZ(),
                        0., 0., 0., 1.
                });
    }

    public void rotateX(double theta) {
        Matrix rotMatrix = new Matrix(4, 4,
                new double[]{
                        1., 0., 0., 0.,
                        0., Math.cos(theta), -Math.sin(theta), 0.,
                        0., Math.sin(theta), Math.cos(theta), 0.,
                        0., 0., 0., 1.
                });
        rotation = rotMatrix.multiply(rotation);
    }

    public void rotateY(double theta) {
        Matrix rotMatrix = new Matrix(4, 4,
                new double[]{
                        Math.cos(theta), 0., Math.sin(theta), 0.,
                        0., 1., 0., 0.,
                        -Math.sin(theta), 0., Math.cos(theta), 0.,
                        0., 0., 0., 1.
                });
        rotation = rotMatrix.multiply(rotation);
    }


    public void rotateZ(double theta) {
        Matrix rotMatrix = new Matrix(4, 4,
                new double[]{
                        Math.cos(theta), -Math.sin(theta), 0., 0.,
                        Math.sin(theta), Math.cos(theta), 0., 0.,
                        0., 0., 1., 0.,
                        0., 0., 0., 1
                });
        rotation = rotMatrix.multiply(rotation);
    }

    public BufferedImage getImage(Drawable3D drawable3D, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());

        drawable3D.projectTo2D(new Projector(width, height), image, Matrix.getSingleMatrix());
        return image;
    }

    public RenderModel getRenderModel() {
        return renderModel;
    }

    public void znChanged(Object o) {
        updateHalfCubeMatrix();
    }

    public void zfChanged(Object o) {
        updateHalfCubeMatrix();
    }

    private void updateHalfCubeMatrix() {
        double near = -renderModel.getZn();
        double far = -renderModel.getZf();
        double sW = renderModel.getSw();
        double sH = renderModel.getSh();

        halfCubeMatrix = new Matrix(4, 4, new double[]{
                2 * near / sW, 0., 0., 0.,
                0., 2 * near / sH, 0., 0.,
                0., 0., near / (far - near), -far * near / (far - near),
                0., 0., 1., 0
        });
    }

    public Point3D toCameraCoordinateSystem(Point3D worldPoint) {
        Matrix transVector = translation.multiply(worldPoint.toHomogeneousVector());
        Matrix rotVector = rotation.multiply(transVector);
        return rotVector.toPoint3D();
    }

    public Drawable3D toCameraCoordinateSystem(Drawable3D d, Matrix sceneMatrix) {
        return d.transform(sceneMatrix).transform(translation).transform(rotation);
    }

    public class Projector {
        private Matrix projectionMatrix;

        public Projector(int width, int height) {
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


        public Point3D findIntersection(Matrix plane, Point3D p1, Point3D p2) {
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


        private boolean isInHalfCube(Point3D p) {
            if (p.getX() < -1 || p.getX() > 1) {
                return false;
            }
            if (p.getY() < -1 || p.getY() > 1) {
                return false;
            }
            if (p.getZ() < -1 || p.getZ() > 0) {
                return false;
            }
            return true;
        }
    }
}
