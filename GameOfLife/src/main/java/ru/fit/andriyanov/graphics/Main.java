package main.java.ru.fit.andriyanov.graphics;

import javax.swing.*;
import java.awt.*;

class GUImenu extends JFrame {
    private JMenuBar menuBar;
    private JToolBar functionBar;

    public GUImenu() {
        super("Example Menu System");// Call the JFrame constructor.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Specify an action for the close button.
        buildMenuBar();

        // Pack and display the window.
        pack();
        setSize(1000, 500); // set frame size
        setVisible(true);
    }

    private void buildMenuBar() {

        // Create the file bar.
        menuBar = new JMenuBar();
        // Create the file and text menus.
        var file = new JMenu("File");
        menuBar.add(file);

        var newItem = new JMenuItem("New");
        file.add(newItem);
        var openItem = new JMenuItem("Open");
        file.add(openItem);
        var saveItem = new JMenuItem("Save");
        file.add(saveItem);
        var saveAsItem = new JMenuItem("Save As");
        file.add(saveAsItem);


        var edit = new JMenu("Edit");
        menuBar.add(edit);

        var xorItem = new JMenuItem("XOR");
        edit.add(xorItem);
        var replaceItem = new JMenuItem("Replace");
        edit.add(replaceItem);
        var clearItem = new JMenuItem("Clear");
        edit.add(clearItem);
        var parametersItem = new JMenuItem("Parameters");
        edit.add(parametersItem);


        var view = new JMenu("View");
        menuBar.add(view);

        var toolBarItem = new JMenuItem("Toolbar");
        view.add(toolBarItem);
        var statusBarItem = new JMenuItem("Status Bar");
        view.add(statusBarItem);
        var detailsInformationItem = new JMenuItem("Display Impact Values");
        view.add(detailsInformationItem);


        var simulation = new JMenu("Simulation");
        menuBar.add(simulation);

        var runItem = new JMenuItem("Run");
        simulation.add(runItem);
        var stepItem = new JMenuItem("Step");
        simulation.add(stepItem);


        var help = new JMenu("Help");
        menuBar.add(help);


        setJMenuBar(menuBar);


        functionBar = new JToolBar();
        functionBar.setLayout(new BoxLayout(functionBar, BoxLayout.X_AXIS));
        functionBar.setPreferredSize(new Dimension(0, 40));

        ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/new.png"));
        var iconNew = icon.getImage().getScaledInstance(30, 30, 0);
        JButton newButton = new JButton(new ImageIcon(iconNew));
        newButton.setToolTipText("New");
        functionBar.add(newButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/load.png"));
        var iconOpen = icon.getImage().getScaledInstance(30, 30, 0);
        JButton openButton = new JButton(new ImageIcon(iconOpen));
        openButton.setToolTipText("Open");
        functionBar.add(openButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/save.png"));
        var iconSave = icon.getImage().getScaledInstance(30, 30, 0);
        JButton saveButton = new JButton(new ImageIcon(iconSave));
        saveButton.setToolTipText("Save");
        functionBar.add(saveButton);


        JButton impactValuesButton = new JButton("0.3");
        impactValuesButton.setToolTipText("Display impact Values");
        functionBar.add(impactValuesButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/intersect.png"));
        var iconIntersect = icon.getImage().getScaledInstance(30, 30, 0);
        JButton xorButton = new JButton(new ImageIcon(iconIntersect));
        xorButton.setToolTipText("Xor");
        functionBar.add(xorButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/repeat.png"));
        var iconRepeat = icon.getImage().getScaledInstance(30, 30, 0);
        JButton replaceButton = new JButton(new ImageIcon(iconRepeat));
        replaceButton.setToolTipText("Replace");
        functionBar.add(replaceButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/delete.png"));
        var iconDelete = icon.getImage().getScaledInstance(30, 30, 0);
        JButton clearButton = new JButton(new ImageIcon(iconDelete));
        clearButton.setToolTipText("Clear");
        functionBar.add(clearButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/settings.png"));
        var iconSettings = icon.getImage().getScaledInstance(30, 30, 0);
        JButton parametersButton = new JButton(new ImageIcon(iconSettings));
        parametersButton.setToolTipText("Settings");
        functionBar.add(parametersButton);


        // TODO Найти иконку для run.

        icon = new ImageIcon(this.getClass().getResource("/resources/Run.gif"));
        var iconRun= icon.getImage().getScaledInstance(30,30,0);
        JButton runButton = new JButton(new ImageIcon(iconRun));
        runButton.setToolTipText("Run");
        functionBar.add(runButton);



        icon = new ImageIcon(this.getClass().getResource("/resources/next.png"));
        var iconNext= icon.getImage().getScaledInstance(30,30,0);
        JButton stepButton = new JButton(new ImageIcon(iconNext));
        stepButton.setToolTipText("Step");
        functionBar.add(stepButton);

        icon = new ImageIcon(this.getClass().getResource("/resources/info.png"));
        var iconInfo= icon.getImage().getScaledInstance(30,30,0);
        JButton helpButton = new JButton(new ImageIcon(iconInfo));
        helpButton.setToolTipText("About");
        functionBar.add(helpButton);


        functionBar.setVisible(true);
        add(functionBar, BorderLayout.PAGE_START);
    }

    public static void main(String[] args) {
        new GUImenu();
    }
}