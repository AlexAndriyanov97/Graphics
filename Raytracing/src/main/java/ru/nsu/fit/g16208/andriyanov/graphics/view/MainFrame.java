package ru.nsu.fit.g16208.andriyanov.graphics.view;

import ru.nsu.fit.g16208.andriyanov.graphics.Controller.MainFrameController;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Screen3D;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends BaseFrame {

    private Screen3D screen3D;
    private MainFrameController controller;

    public MainFrame(MainFrameController controller) {
        this.controller = controller;
        setTitle("Raytracing");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);


        if (controller.getCamera() != null || controller.getScene() != null) {
            screen3D = new Screen3D(controller.getCamera(), controller.getScene());
            add(screen3D);
        }


        JMenu fileMenu = createMenu("File");

        createMenuItem(fileMenu, "Open", this::onOpen);

        createMenuItem(fileMenu, "Save render settings", this::saveRenderSettings);

        createMenuItem(fileMenu, "Save image", this::saveImage);

        createMenuItem(fileMenu, "Load render settings", this::loadRenderSettings);

        JMenu editMenu = createMenu("Edit");

        createMenuItem(editMenu, "init", this::init);

        createMenuItem(editMenu, "Render", this::render);

        JMenu settingsMenu = createMenu("Settings");

        createMenuItem(settingsMenu, "Settings", this::settings);


        createToolItem("/Open.png","Open",this::onOpen);

        createToolItem("/Save.png","Save render settings",this::saveRenderSettings);

        createToolItem("/saveImage.png","Save image",this::saveImage);

        createToolItem("/load.png","Load render settings",this::loadRenderSettings);

        createToolItem("/init.png","Init",this::init);

        createToolItem("/Run.gif","Render",this::render);


        createToolItem("/Settings.png","Settings",this::settings);



        functionBar.setVisible(true);
        add(functionBar, BorderLayout.PAGE_START);

        setJMenuBar(menuBar);
        setVisible(true);
    }

    private void onOpen() {
        controller.onOpen();
        updatePanel();
    }

    private void loadRenderSettings(){
        controller.loadRenderSettings();
        updatePanel();
    }

    private void saveRenderSettings() {
        if (controller.getScene() != null && controller.getCamera() != null) {
            controller.saveRenderSettings();
        }
    }

    private void saveImage() {
        if (controller.getScene() != null && controller.getCamera() != null) {
            controller.saveImage();
        }
    }

    private void init() {
        if (controller.getScene() != null && controller.getCamera() != null) {
            controller.init();
            updatePanel();
        }
    }

    public void render() {
        if (controller.getScene() != null && controller.getCamera() != null) {
            controller.render();
        }
    }

    public void settings() {
        if (controller.getScene() != null && controller.getCamera() != null) {
            controller.SettingsPressed();
        }
    }

    private void updatePanel() {
        if (screen3D != null) {
            remove(screen3D);
        }
        if (controller.getCamera() != null) {
            screen3D = new Screen3D(controller.getCamera(), controller.getScene());
            add(screen3D);
            revalidate();
        }
    }


}
