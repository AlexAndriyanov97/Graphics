package main.java.ru.nsu.fit.andriyanov.graphics.view;

import main.java.ru.nsu.fit.andriyanov.graphics.controller.FigureController;
import main.java.ru.nsu.fit.andriyanov.graphics.model.Spline;
import main.java.ru.nsu.fit.andriyanov.graphics.model.SplineFigure3D;
import main.java.ru.nsu.fit.andriyanov.graphics.model.SplineManager;
import main.java.ru.nsu.fit.andriyanov.graphics.tool.FileReader;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

public class MainView extends JFrame {

    private MainPanel mainPanel;

    private Map<Spline, SplineFigure3D> figures = new HashMap<>();

    {
        mainPanel = new MainPanel();
        SplineManager splineOwner = SplineManager.getInstance();
        splineOwner.addActionOnAdd(spline -> {
            SplineFigure3D figure3D = new SplineFigure3D(spline);
            figures.put(spline, figure3D);

            mainPanel.addFigure(figure3D);
            splineOwner.addObserver(figure3D);
        });
        splineOwner.addActionOnRemove(spline -> {
            SplineFigure3D figure3D = figures.remove(spline);

            mainPanel.removeFigure(figure3D);
            splineOwner.deleteObserver(figure3D);
        });

        Observer updater = (o, arg) -> mainPanel.update();
        splineOwner.addObserver(updater);
    }

    private SplineDialog splineDialog = new SplineDialog(this, spline -> {
        FigureController figureMover = FigureController.getInstance();
        if (spline == null)
            figureMover.setFigure(null);
        else
            figureMover.setFigure(figures.get(spline));
        mainPanel.update();
    });


    public MainView() {
        buildMenuBar();
    }

    private void buildMenuBar() {
        setSize(800, 600);
        setLocationByPlatform(true);
        setTitle("Wireframe");
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> {
            loadFile();
        });
        file.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> {
            saveFile();
        });
        file.add(saveItem);


        JMenuItem edit = new JMenu("Edit");
        menuBar.add(edit);

        JMenuItem init = new JMenuItem("Init");
        init.addActionListener(e -> {
            mainPanel.resetFigures();
        });
        edit.add(init);

        JMenuItem settings = new JMenuItem("Settings");
        settings.addActionListener(e -> {
            getSplineView();
        });
        edit.add(settings);


        JMenu about = new JMenu("About");
        JMenuItem helpItem = new JMenuItem("About");
        helpItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Андриянов Алексей Фит 3 курс 16208"));
        about.add(helpItem);
        menuBar.add(about);
        setJMenuBar(menuBar);


        JToolBar functionBar = new JToolBar();
        functionBar.setLayout(new BoxLayout(functionBar, BoxLayout.X_AXIS));
        functionBar.setPreferredSize(new Dimension(0, 40));

        ImageIcon icon = new ImageIcon(this.getClass().getResource("/main/resources/Open.png"));
        Image iconOpen = icon.getImage().getScaledInstance(30, 30, 0);
        JButton openButton = new JButton(new ImageIcon(iconOpen));
        openButton.setToolTipText("Open");
        openButton.addActionListener(e -> {
            loadFile();
        });
        functionBar.add(openButton);


        icon = new ImageIcon(this.getClass().getResource("/main/resources/Save.png"));
        Image iconSave = icon.getImage().getScaledInstance(30, 30, 0);
        JButton saveButton = new JButton(new ImageIcon(iconSave));
        saveButton.setToolTipText("Save");
        saveButton.addActionListener(e -> {
            saveFile();
        });
        functionBar.add(saveButton);


        icon = new ImageIcon(this.getClass().getResource("/main/resources/Init.png"));
        Image iconInit = icon.getImage().getScaledInstance(30, 30, 0);
        JButton initButton = new JButton(new ImageIcon(iconInit));
        initButton.setToolTipText("Init");
        initButton.addActionListener(e -> {
            mainPanel.resetFigures();
        });
        functionBar.add(initButton);


        icon = new ImageIcon(this.getClass().getResource("/main/resources/Settings.png"));
        Image iconSettings = icon.getImage().getScaledInstance(30, 30, 0);
        JButton settingsButton = new JButton(new ImageIcon(iconSettings));
        settingsButton.setToolTipText("Settings");
        settingsButton.addActionListener(e -> {
            getSplineView();
        });
        functionBar.add(settingsButton);


        functionBar.setVisible(true);
        add(functionBar, BorderLayout.PAGE_START);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        constraints.gridwidth = 3;
        constraints.weightx = 100;
        constraints.weighty = 100;
        constraints.fill = GridBagConstraints.BOTH;

        JScrollPane jScrollPane = new JScrollPane(panel);
        jScrollPane.setPreferredSize(panel.getPreferredSize());
        add(new JPanel().add(jScrollPane));
        mainPanel.setVisible(true);
        add(mainPanel, BorderLayout.CENTER);
    }


    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser("user.dir");
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;

        try {
            InputStream stream = new FileInputStream(fileChooser.getSelectedFile());
            FileReader.loadFile(stream, figures);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Can not open file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser("user.dir");
        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        try {
            OutputStream stream = new FileOutputStream(fileChooser.getSelectedFile());
            FileReader.saveFile(stream, figures);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Can not open file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getSplineView() {
        splineDialog.setVisible(true);
    }
}