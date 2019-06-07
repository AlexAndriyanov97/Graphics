package ru.nsu.fit.g16208.andriyanov.graphics;

import ru.nsu.fit.g16208.andriyanov.graphics.Controller.Loader;
import ru.nsu.fit.g16208.andriyanov.graphics.Controller.MainFrameController;
import ru.nsu.fit.g16208.andriyanov.graphics.model.RenderModel;
import ru.nsu.fit.g16208.andriyanov.graphics.model.SceneModel;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        RenderModel renderModel = null;
        SceneModel sceneModel = null;

        try {
            File scene = new File("Raytracing/FIT_16208_Andriyanov_Raytracing_Data/StandfordBunny.scene");
            File render = new File("Raytracing/FIT_16208_Andriyanov_Raytracing_Data/StandfordBunny.render");
            Loader loader = new Loader(scene, render);
            sceneModel = loader.loadScene();
            renderModel = loader.loadRenderSettings();
        } catch (IOException ignored) {
        }
        MainFrameController mfController = new MainFrameController(renderModel, sceneModel);
    }
}
