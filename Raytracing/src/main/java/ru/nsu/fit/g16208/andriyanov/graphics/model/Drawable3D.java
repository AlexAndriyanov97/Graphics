package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Matrix;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Projection;

import java.awt.image.BufferedImage;

public interface Drawable3D {
    Drawable3D transform(Matrix matrix);
    void projectTo2D(Projection projector, BufferedImage image, Matrix parentMatrix);

}
