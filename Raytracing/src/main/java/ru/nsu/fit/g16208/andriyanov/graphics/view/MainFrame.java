package ru.nsu.fit.g16208.andriyanov.graphics.view;

import ru.nsu.fit.g16208.andriyanov.graphics.Controller.MainFrameController;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Screen3D;

import javax.swing.*;

public class MainFrame extends BaseFrame {

    private Screen3D screen3D;
    private MainFrameController controller;

    public MainFrame(MainFrameController controller) {
        this.controller = controller;
        setTitle("Raytracing");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);

        screen3D = new Screen3D(controller.getCamera(), controller.getScene());
        add(screen3D);


        JMenu fileMenu = createMenu("File");

        createMenuItem(fileMenu, "Open", this::onOpen);

        createMenuItem(fileMenu, "Save render settings", controller::saveRenderSettings);

        createMenuItem(fileMenu,"Save image",controller::saveImage);

        createMenuItem(fileMenu, "Load render settings", controller::loadRenderSettings);

        JMenu editMenu = createMenu("Edit");

        createMenuItem(editMenu, "init", this::init);

        createMenuItem(editMenu, "Render", controller::render);

        JMenu settingsMenu = createMenu("Settings");

        createMenuItem(settingsMenu,"Settings", controller::SettingsPressed);




        setJMenuBar(menuBar);
        setVisible(true);
    }

    private void onOpen(){
        controller.onOpen();
        updatePanel();
    }

    private void init(){
        controller.init();
        updatePanel();
    }

    private void updatePanel() {
        remove(screen3D);
        screen3D = new Screen3D(controller.getCamera(), controller.getScene());
        add(screen3D);
        revalidate();
    }


}
