package ru.nsu.fit.g16208.andriyanov.graphics.wireframe;

import ru.nsu.fit.g16208.andriyanov.graphics.model.Drawable3D;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Point3D;
import ru.nsu.fit.g16208.andriyanov.graphics.model.RenderEvents;
import ru.nsu.fit.g16208.andriyanov.graphics.model.RenderModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;

public class Camera {

    private Matrix translation;
    private Matrix rotation;
    protected Matrix halfCubeMatrix;

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
        g.setColor(renderModel.getBackgroundColor());
        g.fillRect(0, 0, image.getWidth(), image.getHeight());

        drawable3D.projectTo2D(new Projection(width, height), image, Matrix.getSingleMatrix());
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

    public boolean isInHalfCube(Point3D p) {
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

