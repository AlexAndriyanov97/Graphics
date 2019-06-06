package ru.nsu.fit.g16208.andriyanov.graphics;

import ru.nsu.fit.g16208.andriyanov.graphics.Controller.Loader;
import ru.nsu.fit.g16208.andriyanov.graphics.Controller.MainFrameController;
import ru.nsu.fit.g16208.andriyanov.graphics.model.RenderModel;
import ru.nsu.fit.g16208.andriyanov.graphics.model.SceneModel;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        RenderModel renderModel = new RenderModel();
//        SceneModel sceneModel = new SceneModel();
        Loader loader = new Loader(new File(System.getProperty("/Test.scene")), new File("/StandfordBunny.render"));
        SceneModel sceneModel = null;
        try {
            sceneModel = loader.loadScene();
            renderModel = loader.loadRenderSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainFrameController mfController = new MainFrameController(renderModel, sceneModel);
    }
}
