package ru.nsu.fit.g16208.andriyanov.graphics.Controller;

import ru.nsu.fit.g16208.andriyanov.graphics.model.RayTracer;
import ru.nsu.fit.g16208.andriyanov.graphics.model.RenderFrame;
import ru.nsu.fit.g16208.andriyanov.graphics.model.RenderModel;
import ru.nsu.fit.g16208.andriyanov.graphics.model.SceneModel;
import ru.nsu.fit.g16208.andriyanov.graphics.view.MainFrame;
import ru.nsu.fit.g16208.andriyanov.graphics.view.SettingsView;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Camera;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Scene;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainFrameController {
    private RenderModel renderModel;
    private SceneModel sceneModel;
    private BufferedImage image;

    private final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

    private MainFrame mainFrame;
    private Camera camera;
    private Scene scene;

    public MainFrameController(RenderModel renderModel, SceneModel sceneModel) {
        this.renderModel = renderModel;
        this.sceneModel = sceneModel;

        setupFileChoosersDirs();
        mainFrame = new MainFrame(this);
        mainFrame.setSize(800,600);

    }


    private void setupFileChoosersDirs() {
        final File dataDir = new File(System.getProperty("user.dir"), "FIT_16208_Andriyanov_Raytracing_Data");
        if (dataDir.isDirectory()) {
            fileChooser.setCurrentDirectory(dataDir);
        }
    }

    public Camera getCamera() {
        if (camera == null && renderModel != null) {
            camera = new Camera(renderModel);
        }
        return camera;
    }

    public Scene getScene() {
        if (scene == null && sceneModel != null) {
            scene = sceneModel.getScene();
        }
        return scene;
    }

    public void render() {
        RayTracer rayTracer = new RayTracer(camera, sceneModel);
        image = rayTracer.renderScene(scene, renderModel.getDepth());

        RenderFrame rFrame = new RenderFrame(image);
    }


    public void onOpen() {
        try {
            int ret = fileChooser.showDialog(null, "Open scene");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                Loader loader = new Loader(file);
                sceneModel = loader.loadScene();
                scene = sceneModel.getScene();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can not open file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveRenderSettings() {
        try {
            int ret = fileChooser.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                Loader loader = new Loader();
                loader.saveRenderSettings(renderModel, file);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can not open file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void saveImage() {
        try {
            int ret = fileChooser.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                ImageIO.write(image, "jpg", file);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can not open file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void init() {
        scene.reset();
    }

    public void SettingsPressed() {
        SettingsView settingsView = new SettingsView(renderModel);
        settingsView.setLocationRelativeTo(mainFrame);
        settingsView.setSize(600, 600);
        settingsView.setVisible(true);
        mainFrame.repaint();
    }


    public void loadRenderSettings() {
        try {
            int ret = fileChooser.showDialog(null, "Open scene");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                Loader loader = new Loader(file);

                renderModel = loader.loadRenderSettings(file);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can not open file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
