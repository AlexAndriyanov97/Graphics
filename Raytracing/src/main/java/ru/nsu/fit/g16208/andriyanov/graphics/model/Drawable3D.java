package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Camera;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Matrix;

import java.awt.image.BufferedImage;

public interface Drawable3D {
    Drawable3D transform(Matrix matrix);
    void projectTo2D(Camera.Projector projector, BufferedImage image, Matrix parentMatrix);

}
