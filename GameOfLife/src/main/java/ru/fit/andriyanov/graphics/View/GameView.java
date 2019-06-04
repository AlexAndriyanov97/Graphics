package main.java.ru.fit.andriyanov.graphics.View;

import main.java.ru.fit.andriyanov.graphics.Controller.GameController;
import main.java.ru.fit.andriyanov.graphics.Model.Model;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private JMenuBar menuBar;
    private JToolBar functionBar;

    private HexField field;
    private Model gameModel;
    private GameController gameController;

    public GameView(Model model) {
        super("Example Menu System");// Call the JFrame constructor.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Specify an action for the close button.
        buildMenuBar(model);

        // Pack and display the window.
        pack();
        setSize(1000, 500); // set frame size
        setVisible(true);
    }

    private void buildMenuBar(Model model) {
        gameModel=model;
        // Create the file bar.
        menuBar = new JMenuBar();
        // Create the file and text menus.
        var file = new JMenu("File");
        menuBar.add(file);

        JMenuItem newItem = new JMenuItem("New");
        file.add(newItem);
        JMenuItem openItem = new JMenuItem("Open");
        file.add(openItem);
        JMenuItem saveItem = new JMenuItem("Save");
        file.add(saveItem);
        JMenuItem saveAsItem = new JMenuItem("Save As");
        file.add(saveAsItem);


        JMenu edit = new JMenu("Edit");
        menuBar.add(edit);

        JMenuItem xorItem = new JMenuItem("XOR");
        edit.add(xorItem);
        JMenuItem replaceItem = new JMenuItem("Replace");
        edit.add(replaceItem);
        JMenuItem clearItem = new JMenuItem("Clear");
        edit.add(clearItem);
        JMenuItem parametersItem = new JMenuItem("Parameters");
        edit.add(parametersItem);


        JMenu view = new JMenu("View");
        menuBar.add(view);

        JMenuItem toolBarItem = new JMenuItem("Toolbar");
        view.add(toolBarItem);
        JMenuItem statusBarItem = new JMenuItem("Status Bar");
        view.add(statusBarItem);
        JMenuItem detailsInformationItem = new JMenuItem("Display Impact Values");
        view.add(detailsInformationItem);


        JMenu simulation = new JMenu("Simulation");
        menuBar.add(simulation);

        JMenuItem runItem = new JMenuItem("Run");
        simulation.add(runItem);
        JMenuItem stepItem = new JMenuItem("Step");
        simulation.add(stepItem);


        var help = new JMenu("Help");
        menuBar.add(help);


        setJMenuBar(menuBar);


        functionBar = new JToolBar();
        functionBar.setLayout(new BoxLayout(functionBar, BoxLayout.X_AXIS));
        functionBar.setPreferredSize(new Dimension(0, 40));

        ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/new.png"));
        Image iconNew = icon.getImage().getScaledInstance(30, 30, 0);
        JButton newButton = new JButton(new ImageIcon(iconNew));
        newButton.setToolTipText("New");
        functionBar.add(newButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/load.png"));
        Image iconOpen = icon.getImage().getScaledInstance(30, 30, 0);
        JButton openButton = new JButton(new ImageIcon(iconOpen));
        openButton.setToolTipText("Open");
        functionBar.add(openButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/save.png"));
        Image iconSave = icon.getImage().getScaledInstance(30, 30, 0);
        JButton saveButton = new JButton(new ImageIcon(iconSave));
        saveButton.setToolTipText("Save");
        functionBar.add(saveButton);


        JButton impactValuesButton = new JButton("0.3");
        impactValuesButton.setToolTipText("Display impact Values");
        functionBar.add(impactValuesButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/intersect.png"));
        Image iconIntersect = icon.getImage().getScaledInstance(30, 30, 0);
        JButton xorButton = new JButton(new ImageIcon(iconIntersect));
        xorButton.setToolTipText("Xor");
        functionBar.add(xorButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/repeat.png"));
        Image iconRepeat = icon.getImage().getScaledInstance(30, 30, 0);
        JButton replaceButton = new JButton(new ImageIcon(iconRepeat));
        replaceButton.setToolTipText("Replace");
        functionBar.add(replaceButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/delete.png"));
        Image iconDelete = icon.getImage().getScaledInstance(30, 30, 0);
        JButton clearButton = new JButton(new ImageIcon(iconDelete));
        clearButton.setToolTipText("Clear");
        functionBar.add(clearButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/settings.png"));
        Image iconSettings = icon.getImage().getScaledInstance(30, 30, 0);
        JButton parametersButton = new JButton(new ImageIcon(iconSettings));
        parametersButton.setToolTipText("Settings");
        functionBar.add(parametersButton);

        icon = new ImageIcon(this.getClass().getResource("/resources/Run.gif"));
        Image iconRun = icon.getImage().getScaledInstance(30, 30, 0);
        JButton runButton = new JButton(new ImageIcon(iconRun));
        runButton.setToolTipText("Run");
        functionBar.add(runButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/next.png"));
        Image iconNext = icon.getImage().getScaledInstance(30, 30, 0);
        JButton stepButton = new JButton(new ImageIcon(iconNext));
        stepButton.setToolTipText("Step");
        functionBar.add(stepButton);

        icon = new ImageIcon(this.getClass().getResource("/resources/info.png"));
        Image iconInfo = icon.getImage().getScaledInstance(30, 30, 0);
        JButton helpButton = new JButton(new ImageIcon(iconInfo));
        helpButton.setToolTipText("About");
        functionBar.add(helpButton);


        functionBar.setVisible(true);
        add(functionBar, BorderLayout.PAGE_START);

        gameController = new GameController(gameModel, this);
        field = new HexField(gameModel, gameController);

        JScrollPane scrollPane = new JScrollPane(field);
        scrollPane.setPreferredSize(field.getPreferredSize());
        add(new JPanel().add(scrollPane));
    }


    public HexField getField() {
        return field;
    }
}