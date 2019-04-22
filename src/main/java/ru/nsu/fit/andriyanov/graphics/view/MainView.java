package main.java.ru.nsu.fit.andriyanov.graphics.view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private JMenuBar menuBar;
    private JToolBar functionBar;

    public MainView() {

    }
    private void BuildMenuBar() {

        // Create the file bar.
        menuBar = new JMenuBar();
        // Create the file and text menus.
        var file = new JMenu("File");
        menuBar.add(file);

        var openItem = new JMenuItem("Open");
        file.add(openItem);


        var view = new JMenu("View");
        menuBar.add(view);

        var toolBarItem = new JMenuItem("Toolbar");
        view.add(toolBarItem);
        var statusBarItem = new JMenuItem("Status Bar");
        view.add(statusBarItem);
        var detailsInformationItem = new JMenuItem("Display Impact Values");
        view.add(detailsInformationItem);


        var settings = new JMenu("Settings");
        menuBar.add(settings);

        var settingsItem = new JMenuItem("Settings");
        settings.add(settingsItem);


        var help = new JMenu("Help");
        menuBar.add(help);


        setJMenuBar(menuBar);


        functionBar = new JToolBar();
        functionBar.setLayout(new BoxLayout(functionBar, BoxLayout.X_AXIS));
        functionBar.setPreferredSize(new Dimension(0, 40));

        ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/Open32.png"));
        var iconOpen = icon.getImage().getScaledInstance(30, 30, 0);
        JButton openButton = new JButton(new ImageIcon(iconOpen));
        openButton.setToolTipText("Open");
        functionBar.add(openButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/Grid32.png"));
        var iconShowGrid = icon.getImage().getScaledInstance(30, 30, 0);
        JButton showGridButton = new JButton(new ImageIcon(iconShowGrid));
        showGridButton.setToolTipText("Show grid");
        functionBar.add(showGridButton);

        icon = new ImageIcon(this.getClass().getResource("/resources/Isoline32.png"));
        var iconShowIsolines = icon.getImage().getScaledInstance(30, 30, 0);
        JButton showIsolinesButton = new JButton(new ImageIcon(iconShowIsolines));
        showIsolinesButton.setToolTipText("Show Isolines");
        functionBar.add(showIsolinesButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/Interpolation32.png"));
        var iconInterpolation = icon.getImage().getScaledInstance(30, 30, 0);
        JButton interpolationButton = new JButton(new ImageIcon(iconInterpolation));
        interpolationButton.setToolTipText("Interpolation");
        functionBar.add(interpolationButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/PaintMode32.png"));
        var iconPaint = icon.getImage().getScaledInstance(30, 30, 0);
        JButton paintButton = new JButton(new ImageIcon(iconPaint));
        paintButton.setToolTipText("Paint");
        functionBar.add(paintButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/Dots32.png"));
        var iconDots = icon.getImage().getScaledInstance(30, 30, 0);
        JButton dotsButton = new JButton(new ImageIcon(iconDots));
        dotsButton.setToolTipText("Dots");
        functionBar.add(dotsButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/Settings32.png"));
        var iconSettings= icon.getImage().getScaledInstance(30,30,0);
        JButton settingsButton = new JButton(new ImageIcon(iconSettings));
        settingsButton.setToolTipText("Settings");
        functionBar.add(settingsButton);


        functionBar.setVisible(true);
        add(functionBar, BorderLayout.PAGE_START);

        JPanel content = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        constraints.gridwidth = 3;
        constraints.weightx = 100;
        constraints.weighty = 100;
        constraints.fill = GridBagConstraints.BOTH;


    }
}
