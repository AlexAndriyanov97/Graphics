package ru.nsu.fit.g16208.andriyanov.graphics.wireframe;

import ru.nsu.fit.g16208.andriyanov.graphics.model.CoordinateSystem;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Drawable3D;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Point3D;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class Scene implements Drawable3D {
    private List<Drawable3D> childList = new LinkedList<>();
    private CoordinateSystem coordinateSystem;

    public Scene(CoordinateSystem coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
    }

    @Override
    public Scene transform(Matrix matrix) {
        Scene result = new Scene(coordinateSystem);
        for (Drawable3D c : childList) {
            result.addChild(c.transform(matrix));
        }
        return result;
    }

    @Override
    public void projectTo2D(Camera.Projector projector, BufferedImage image, Matrix parentMatrix) {
        for (Drawable3D c : childList) {
            c.projectTo2D(projector, image, coordinateSystem.getMatrix().multiply(parentMatrix));
        }
    }

    public void reset(){
        coordinateSystem.reset();
    }

    public void addChild(Drawable3D child) {
        childList.add(child);
    }

    public void removeChild(Drawable3D d) {
        childList.remove(d);
    }

    public List<Drawable3D> getChildList() {
        return childList;
    }

    public void rotateX(double theta) {
        coordinateSystem.rotateX(theta);
    }

    public void rotateY(double theta) {
        coordinateSystem.rotateY(theta);
    }

    public void rotateZ(double theta) {
        coordinateSystem.rotateZ(theta);
    }

    public void moveTo(Point3D point3D) {
        coordinateSystem.moveTo(point3D);
    }

    public Matrix getCoordinateSystemMatrix() {
        return coordinateSystem.getMatrix();
    }
}