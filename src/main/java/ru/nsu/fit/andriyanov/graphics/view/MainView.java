package main.java.ru.nsu.fit.andriyanov.graphics.view;

import main.java.ru.nsu.fit.andriyanov.graphics.controller.MainController;
import main.java.ru.nsu.fit.andriyanov.graphics.model.ChangedPoint;
import main.java.ru.nsu.fit.andriyanov.graphics.model.Func;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private JMenuBar menuBar;
    private JToolBar functionBar;
    private MainController controller;
    private ValueBar valueBar;

    private LegendView legendView;
    private IsolineView isolineView;


    public MainView(MainController controller, Func functionModel, Func legend) {
        this.controller = controller;
        BuildMenuBar(functionModel, legend);

    }

    private void BuildMenuBar(Func function, Func legend) {

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
        showGridButton.addActionListener(e -> controller.GridPressed());
        functionBar.add(showGridButton);

        icon = new ImageIcon(this.getClass().getResource("/resources/Isoline32.png"));
        var iconShowIsolines = icon.getImage().getScaledInstance(30, 30, 0);
        JButton showIsolinesButton = new JButton(new ImageIcon(iconShowIsolines));
        showIsolinesButton.setToolTipText("Show Isolines");
        showIsolinesButton.addActionListener(e -> controller.IsolinePressed());
        functionBar.add(showIsolinesButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/Interpolation32.png"));
        var iconInterpolation = icon.getImage().getScaledInstance(30, 30, 0);
        JButton interpolationButton = new JButton(new ImageIcon(iconInterpolation));
        interpolationButton.setToolTipText("Interpolation");
        interpolationButton.addActionListener(e -> controller.InterpolatePressed());
        functionBar.add(interpolationButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/PaintMode32.png"));
        var iconPaint = icon.getImage().getScaledInstance(30, 30, 0);
        JButton paintButton = new JButton(new ImageIcon(iconPaint));
        paintButton.setToolTipText("Paint");
        paintButton.addActionListener(e -> controller.PaintPressed());
        functionBar.add(paintButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/Dots32.png"));
        var iconDots = icon.getImage().getScaledInstance(30, 30, 0);
        JButton dotsButton = new JButton(new ImageIcon(iconDots));
        dotsButton.setToolTipText("Dots");
        dotsButton.addActionListener(e -> controller.DotsPressed());
        functionBar.add(dotsButton);


        icon = new ImageIcon(this.getClass().getResource("/resources/Settings32.png"));
        var iconSettings = icon.getImage().getScaledInstance(30, 30, 0);
        JButton settingsButton = new JButton(new ImageIcon(iconSettings));
        settingsButton.setToolTipText("Settings");
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

        isolineView = new IsolineView(function);
        isolineView.SetFunctionOfListenerChangedPoint(this::GetValueByChangedPosition);
        panel.add(isolineView, constraints);
        constraints.gridy = 4;
        constraints.gridheight = 1;
        constraints.weighty = 30;
        constraints.anchor = GridBagConstraints.PAGE_END;

        //legendView = new LegendView(legend);
        //panel.add(legendView,constraints);


        JScrollPane jScrollPane = new JScrollPane(panel);
        jScrollPane.setPreferredSize(panel.getPreferredSize());
        add(new JPanel().add(jScrollPane));

        valueBar = new ValueBar();
        add(valueBar, BorderLayout.SOUTH);

    }


    public void GetValueByChangedPosition(ChangedPoint point) {
        valueBar.SetText(String.format("[x: %.1f, y: %.1f] => %.1f", point.getX(), point.getValue(), point.getValue()));
    }

    public void ChangeGridState() {
        isolineView.ChangeGridActivated();
    }

    public void ChangeInterpolate() {
        isolineView.ChangeInterpolate();
    }

    public void ChangePaintState() {
        isolineView.ChangePaintActivated();
    }

    public void ChangeDotsState() {
        isolineView.ChangeDotsActivated();
    }

    public void ChangeIsolineState() {
        isolineView.ChangeIsolineActivated();
    }
}
