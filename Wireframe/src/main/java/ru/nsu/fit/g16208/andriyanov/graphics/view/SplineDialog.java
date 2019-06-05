package ru.nsu.fit.g16208.andriyanov.graphics.view;

import ru.nsu.fit.g16208.andriyanov.graphics.model.Camera;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Spline;
import ru.nsu.fit.g16208.andriyanov.graphics.model.SplineManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class SplineDialog extends JDialog {


    private static int figureCounter = 0;

    private JTabbedPane tabbedPane;
    private List<Spline> splines = new ArrayList<>();
    private Map<Spline, JPanel> splinePanels = new HashMap<>();

    private SplineManager splineOwner = SplineManager.getInstance();
    private Camera camera = Camera.getInstance();

    private Consumer<Spline> actionOnSelect;

    public SplineDialog(JFrame parent, Consumer<Spline> actionOnSelect) {
        super(parent, "Spline");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                actionOnSelect.accept(null);
            }
        });

        setSize(500, 500);
        setResizable(false);

        setLocationRelativeTo(parent);

        this.actionOnSelect = actionOnSelect;

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;

        tabbedPane = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == -1)
                actionOnSelect.accept(null);
            else
                actionOnSelect.accept(splines.get(tabbedPane.getSelectedIndex()));
        });

        Observer repainter = (o, arg) -> SwingUtilities.invokeLater(tabbedPane::repaint);
        splineOwner.addObserver(repainter);

        constraints.weighty = 1;
        add(tabbedPane, constraints);

        constraints.weighty = 0.05;
        add(createButtonsPanel(), constraints);

        splineOwner.addActionOnAdd(this::addSplinePanel);
        splineOwner.addActionOnRemove(this::removeSplinePanel);

        splineOwner.addSpline(new Spline());

        actionOnSelect.accept(null);
    }

    private void addSplinePanel(Spline spline) {
        JPanel panel = new SplinePanel(spline, splineOwner::notifyObservers,
                () -> splineOwner.removeSpline(spline));
        splines.add(spline);
        splinePanels.put(spline, panel);

        tabbedPane.add(String.format("%d", figureCounter++), panel);
    }

    private void removeSplinePanel(Spline spline) {
        JPanel panel = splinePanels.remove(spline);
        splines.remove(spline);

        tabbedPane.remove(panel);
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();

        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();


        JLabel nLabel = new JLabel("n");

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 0.1;
        buttonsPanel.add(nLabel, constraints);

        JSpinner nSpinner = new JSpinner(new SpinnerNumberModel(splineOwner.getLengthCount(), 1, 20, 1));
        nSpinner.addChangeListener(e -> splineOwner.setLengthCount((Integer) nSpinner.getValue()));

        splineOwner.addObserver((o, arg) -> nSpinner.setValue(splineOwner.getLengthCount()));

        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.weightx = 1;
        buttonsPanel.add(nSpinner, constraints);

        JLabel mLabel = new JLabel("m");

        constraints.weightx = 0.1;
        buttonsPanel.add(mLabel, constraints);

        JSpinner mSpinner = new JSpinner(new SpinnerNumberModel(splineOwner.getRotateCount(), 1, 30, 1));
        mSpinner.addChangeListener(e -> splineOwner.setRotateCount((Integer) mSpinner.getValue()));

        splineOwner.addObserver((o, arg) -> mSpinner.setValue(splineOwner.getRotateCount()));

        constraints.weightx = 0.9;
        buttonsPanel.add(mSpinner, constraints);

        JLabel kLabel = new JLabel("k");

        constraints.weightx = 0.1;
        buttonsPanel.add(kLabel, constraints);

        JSpinner kSpinner = new JSpinner(new SpinnerNumberModel(splineOwner.getLengthK(), 1, 10, 1));
        kSpinner.addChangeListener(e -> splineOwner.setLengthK((Integer) kSpinner.getValue()));

        splineOwner.addObserver((o, arg) -> kSpinner.setValue(splineOwner.getLengthK()));

        constraints.weightx = 0.9;
        buttonsPanel.add(kSpinner, constraints);

        JLabel aLabel = new JLabel("a");

        constraints.gridy = 1;
        constraints.weightx = 0.1;
        buttonsPanel.add(aLabel, constraints);

        JSpinner aSpinner = new JSpinner(new SpinnerNumberModel(splineOwner.getLengthFrom(), 0, 1, 0.01));
        aSpinner.addChangeListener(e -> {
            double a = (double) aSpinner.getValue();
            if (a > splineOwner.getLengthTo())
                aSpinner.setValue(splineOwner.getLengthFrom());
            else
                splineOwner.setLengthFrom(a);
        });

        splineOwner.addObserver((o, arg) -> aSpinner.setValue(splineOwner.getLengthFrom()));

        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.weightx = 1;
        buttonsPanel.add(aSpinner, constraints);

        JLabel bLabel = new JLabel("b");

        constraints.weightx = 0.1;
        buttonsPanel.add(bLabel, constraints);

        JSpinner bSpinner = new JSpinner(new SpinnerNumberModel(splineOwner.getLengthTo(), 0, 1, 0.01));
        bSpinner.addChangeListener(e -> {
            double b = (double) bSpinner.getValue();
            if (splineOwner.getLengthFrom() > b)
                bSpinner.setValue(splineOwner.getLengthTo());
            else
                splineOwner.setLengthTo(b);
        });

        splineOwner.addObserver((o, arg) -> bSpinner.setValue(splineOwner.getLengthTo()));

        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.weightx = 1;
        buttonsPanel.add(bSpinner, constraints);

        JLabel cLabel = new JLabel("c");

        constraints.gridy = 1;
        constraints.weightx = 0.1;
        buttonsPanel.add(cLabel, constraints);

        JSpinner cSpinner = new JSpinner(new SpinnerNumberModel(splineOwner.getRotateFrom(), 0, 2 * Math.PI, 0.01));
        cSpinner.addChangeListener(e -> {
            double c = (double) cSpinner.getValue();
            if (c > splineOwner.getRotateTo())
                cSpinner.setValue(splineOwner.getRotateFrom());
            else
                splineOwner.setRotateFrom(c);
        });

        splineOwner.addObserver((o, arg) -> cSpinner.setValue(splineOwner.getRotateFrom()));

        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.weightx = 0.9;
        buttonsPanel.add(cSpinner, constraints);

        JLabel dLabel = new JLabel("d");

        constraints.weightx = 0.1;
        buttonsPanel.add(dLabel, constraints);

        JSpinner dSpinner = new JSpinner(new SpinnerNumberModel(splineOwner.getRotateTo(), 0, 2 * Math.PI, 0.01));
        dSpinner.addChangeListener(e -> {
            double d = (double) dSpinner.getValue();
            if (splineOwner.getRotateFrom() > d)
                dSpinner.setValue(splineOwner.getRotateTo());
            else
                splineOwner.setRotateTo(d);
        });

        splineOwner.addObserver((o, arg) -> dSpinner.setValue(splineOwner.getRotateTo()));

        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.weightx = 0.9;
        buttonsPanel.add(dSpinner, constraints);

        JLabel swLabel = new JLabel("sw");

        constraints.gridy = 2;
        constraints.weightx = 0.1;
        buttonsPanel.add(swLabel, constraints);

        JSpinner swSpinner = new JSpinner(new SpinnerNumberModel(camera.getWidth(), 0.5, 2, 0.1));
        swSpinner.addChangeListener(e -> camera.setWidth((Double) swSpinner.getValue()));

        camera.addObserver((o, arg) -> swSpinner.setValue(camera.getWidth()));

        constraints.weightx = 1;
        buttonsPanel.add(swSpinner, constraints);

        JLabel shLabel = new JLabel("sh");

        constraints.weightx = 0.1;
        buttonsPanel.add(shLabel, constraints);

        JSpinner shSpinner = new JSpinner(new SpinnerNumberModel(camera.getHeight(), 0.5, 2, 0.1));
        shSpinner.addChangeListener(e -> camera.setHeight((Double) shSpinner.getValue()));

        camera.addObserver((o, arg) -> shSpinner.setValue(camera.getHeight()));

        constraints.weightx = 1;
        buttonsPanel.add(shSpinner, constraints);

        JLabel zfLabel = new JLabel("zf");

        constraints.weightx = 0.1;
        buttonsPanel.add(zfLabel, constraints);

        JSpinner zfSpinner = new JSpinner(new SpinnerNumberModel(camera.getFrontZ(), 1, 10, 0.1));
        zfSpinner.addChangeListener(e -> {
            double zf = (double) zfSpinner.getValue();
            if (zf > camera.getBackZ())
                zfSpinner.setValue(camera.getFrontZ());
            else
                camera.setFrontZ(zf);
        });

        camera.addObserver((o, arg) -> zfSpinner.setValue(camera.getFrontZ()));

        constraints.weightx = 0.9;
        buttonsPanel.add(zfSpinner, constraints);

        JLabel zbLabel = new JLabel("zb");

        constraints.weightx = 0.1;
        buttonsPanel.add(zbLabel, constraints);

        JSpinner zbSpinner = new JSpinner(new SpinnerNumberModel(camera.getBackZ(), 1, 10, 0.1));
        zbSpinner.addChangeListener(e -> {
            double zb = (double) zbSpinner.getValue();
            if (camera.getFrontZ() > zb)
                zbSpinner.setValue(camera.getBackZ());
            else
                camera.setBackZ(zb);
        });

        camera.addObserver((o, arg) -> zbSpinner.setValue(camera.getBackZ()));

        constraints.weightx = 0.9;
        buttonsPanel.add(zbSpinner, constraints);

        JButton backgroundColorButton = new JButton("background color");
        backgroundColorButton.addActionListener(e -> {
            camera.setColor(JColorChooser.showDialog(this, "Background color", camera.getColor()));
            tabbedPane.repaint();
        });

        constraints.gridx = 5;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        buttonsPanel.add(backgroundColorButton, constraints);

        JButton addButton = new JButton("add");
        addButton.addActionListener(e -> splineOwner.addSpline(new Spline()));

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        buttonsPanel.add(addButton, constraints);

        return buttonsPanel;
    }

    @Override
    public void setVisible(boolean flag) {
        super.setVisible(flag);
        if (!flag)
            return;

        JPanel panel = splinePanels.values().stream()
                .findAny().orElse(null);
        if (panel != null)
            setSize(panel.getHeight(), getHeight());

        if (tabbedPane.getSelectedIndex() == -1)
            actionOnSelect.accept(null);
        else
            actionOnSelect.accept(splines.get(tabbedPane.getSelectedIndex()));
    }

}