package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Matrix;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Scene;

import java.util.ArrayList;
import java.util.List;

public class SceneModel {
    private double aR = 20;
    private double aG = 20;
    private double aB = 20;

    private double kA = 0.8;

    private List<Light3D> lights;
    private Scene scene;

    public SceneModel() {
        lights = new ArrayList<>();
        lights.add(
                new Light3D(
                        new Point3D(-1,1,-3),
                        0,0,255
                )
        );
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        if (scene == null) {
            scene = new Scene(new CoordinateSystem(Matrix.getSingleMatrix(), new Point3D(0, 0, 0)));
            scene.addChild(
                    new Sphere(
                            new Point3D(0,0,0),
                            2
                    )
            );

            scene.addChild(
                    new Sphere(
                            new Point3D(5,0,0),
                            2
                    )
            );

            scene.addChild(
                    new Sphere(
                            new Point3D(7, 5, 0),
                            5
                    )
            );

            for (Light3D light3D : lights) {
                scene.addChild(light3D);
            }
        }
        return scene;
    }

    public double getAmbientLight(RGB channel) {
        switch (channel) {
            case RED: return aR;
            case GREEN: return aG;
            case BLUE: return aB;
            default: return 0;
        }
    }

    public void setAmbientLight(RGB channel, double value) {
        switch (channel) {
            case RED:
                aR = value;
                break;
            case GREEN:
                aG = value;
                break;
            case BLUE:
                aB = value;
                break;
        }
    }


    public double getKa() {
        return kA;
    }

    public void setKa(double kA) {
        this.kA = kA;
    }

    public List<Light3D> getLights() {
        return lights;
    }

    public void setLights(List<Light3D> lights) {
        this.lights = lights;
    }


}
