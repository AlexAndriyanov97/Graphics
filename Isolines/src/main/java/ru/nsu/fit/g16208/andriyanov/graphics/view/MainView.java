package ru.nsu.fit.g16208.andriyanov.graphics.view;

import ru.nsu.fit.g16208.andriyanov.graphics.controller.MainController;
import ru.nsu.fit.g16208.andriyanov.graphics.model.ChangedPoint;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Func;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

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
        menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> {
            try {
                controller.OpenPressed();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        });
        file.add(openItem);


        JMenuItem view = new JMenu("View");
        menuBar.add(view);

        JMenuItem gridItem = new JMenuItem("Show Grid");
        gridItem.addActionListener(e -> controller.GridPressed());
        view.add(gridItem);

        JMenuItem isolineItem = new JMenuItem("Show Isolines");
        isolineItem.addActionListener(e -> controller.IsolinePressed());
        view.add(isolineItem);

        JMenuItem interpolationItem = new JMenuItem("Interpolation");
        interpolationItem.addActionListener(e -> controller.InterpolatePressed());
        view.add(interpolationItem);

        JMenuItem paintModeItem = new JMenuItem("Paint Mode");
        paintModeItem.addActionListener(e -> controller.PaintPressed());
        view.add(paintModeItem);

        JMenuItem dotsModeItem = new JMenuItem("Dots Mode");
        dotsModeItem.addActionListener(e -> controller.DotsPressed());
        view.add(dotsModeItem);


        JMenu settings = new JMenu("Settings");
        menuBar.add(settings);

        JMenuItem settingsItem = new JMenuItem("Settings");
        settings.add(settingsItem);
        settingsItem.addActionListener(e -> controller.SettingsPressed());


        JMenu help = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(e->JOptionPane.showMessageDialog(this  ,"Андриянов Алексей Фит 3 курс 16208"));
        help.add(helpItem);
        menuBar.add(help);
        setJMenuBar(menuBar);


        functionBar = new JToolBar();
        functionBar.setLayout(new BoxLayout(functionBar, BoxLayout.X_AXIS));
        functionBar.setPreferredSize(new Dimension(0, 40));

        ImageIcon icon = new ImageIcon(this.getClass().getResource("/Open32.png"));
        Image iconOpen = icon.getImage().getScaledInstance(30, 30, 0);
        JButton openButton = new JButton(new ImageIcon(iconOpen));
        openButton.setToolTipText("Open");
        openButton.addActionListener(e -> {
            try {
                controller.OpenPressed();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        });
        functionBar.add(openButton);


        icon = new ImageIcon(this.getClass().getResource("/Grid32.png"));
        Image iconShowGrid = icon.getImage().getScaledInstance(30, 30, 0);
        JButton showGridButton = new JButton(new ImageIcon(iconShowGrid));
        showGridButton.setToolTipText("Show grid");
        showGridButton.addActionListener(e -> controller.GridPressed());
        functionBar.add(showGridButton);

        icon = new ImageIcon(this.getClass().getResource("/Isoline32.png"));
        Image iconShowIsolines = icon.getImage().getScaledInstance(30, 30, 0);
        JButton showIsolinesButton = new JButton(new ImageIcon(iconShowIsolines));
        showIsolinesButton.setToolTipText("Show Isolines");
        showIsolinesButton.addActionListener(e -> controller.IsolinePressed());
        functionBar.add(showIsolinesButton);


        icon = new ImageIcon(this.getClass().getResource("/Interpolation32.png"));
        Image iconInterpolation = icon.getImage().getScaledInstance(30, 30, 0);
        JButton interpolationButton = new JButton(new ImageIcon(iconInterpolation));
        interpolationButton.setToolTipText("Interpolation");
        interpolationButton.addActionListener(e -> controller.InterpolatePressed());
        functionBar.add(interpolationButton);


        icon = new ImageIcon(this.getClass().getResource("/PaintMode32.png"));
        Image iconPaint = icon.getImage().getScaledInstance(30, 30, 0);
        JButton paintButton = new JButton(new ImageIcon(iconPaint));
        paintButton.setToolTipText("Paint");
        paintButton.addActionListener(e -> controller.PaintPressed());
        functionBar.add(paintButton);


        icon = new ImageIcon(this.getClass().getResource("/Dots32.png"));
        Image iconDots = icon.getImage().getScaledInstance(30, 30, 0);
        JButton dotsButton = new JButton(new ImageIcon(iconDots));
        dotsButton.setToolTipText("Dots");
        dotsButton.addActionListener(e -> controller.DotsPressed());
        functionBar.add(dotsButton);


        icon = new ImageIcon(this.getClass().getResource("/Settings32.png"));
        Image iconSettings = icon.getImage().getScaledInstance(30, 30, 0);
        JButton settingsButton = new JButton(new ImageIcon(iconSettings));
        settingsButton.setToolTipText("Settings");
        settingsButton.addActionListener(e -> controller.SettingsPressed());
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
        constraints.anchor = GridBagConstraints.SOUTH;

        legendView = new LegendView(legend);
        panel.add(legendView, constraints);


        JScrollPane jScrollPane = new JScrollPane(panel);
        jScrollPane.setPreferredSize(panel.getPreferredSize());
        add(new JPanel().add(jScrollPane));

        valueBar = new ValueBar();
        add(valueBar, BorderLayout.SOUTH);
    }


    public void GetValueByChangedPosition(ChangedPoint point) {
        valueBar.SetText(String.format("[x: %.1f, y: %.1f] => %.1f", point.getX(), point.getY(), point.getValue()));
    }

    public void ChangeGridState() {
        isolineView.ChangeGridActivated();
    }

    public void ChangeInterpolate() {
        isolineView.ChangeInterpolate();
        legendView.ChangeInterpolate();
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
