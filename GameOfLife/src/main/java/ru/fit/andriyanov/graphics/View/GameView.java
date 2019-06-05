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
        gameModel = model;
        // Create the file bar.
        menuBar = new JMenuBar();
        // Create the file and text menus.
        var file = new JMenu("File");
        menuBar.add(file);

        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(e -> onNew());
        file.add(newItem);
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> onOpen());
        file.add(openItem);
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> onSave());
        file.add(saveItem);
        JMenuItem saveAsItem = new JMenuItem("Save As");
        saveAsItem.addActionListener(e -> onSave());
        file.add(saveAsItem);


        JMenu edit = new JMenu("Edit");
        menuBar.add(edit);

        JMenuItem xorItem = new JMenuItem("XOR");
        xorItem.addActionListener(e -> {
            gameController.onXor();
        });
        edit.add(xorItem);
        JMenuItem replaceItem = new JMenuItem("Replace");
        replaceItem.addActionListener(e -> gameController.onXor());
        edit.add(replaceItem);
        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(e -> onClear());
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
        runItem.addActionListener(e -> {
            gameController.onRun();
        });
        simulation.add(runItem);
        JMenuItem stepItem = new JMenuItem("Step");
        stepItem.addActionListener(e -> {
            gameController.onStop();
        });
        simulation.add(stepItem);


        var help = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("About");
        helpItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Андриянов Алексей Фит 3 курс 16208"));
        help.add(helpItem);
        menuBar.add(help);


        setJMenuBar(menuBar);


        functionBar = new JToolBar();
        functionBar.setLayout(new BoxLayout(functionBar, BoxLayout.X_AXIS));
        functionBar.setPreferredSize(new Dimension(0, 40));

        ImageIcon icon = new ImageIcon(this.getClass().getResource("/main/resources/new.png"));
        Image iconNew = icon.getImage().getScaledInstance(30, 30, 0);
        JButton newButton = new JButton(new ImageIcon(iconNew));
        newButton.setToolTipText("New");
        newButton.addActionListener(e -> onNew());
        functionBar.add(newButton);


        icon = new ImageIcon(this.getClass().getResource("/main/resources/load.png"));
        Image iconOpen = icon.getImage().getScaledInstance(30, 30, 0);
        JButton openButton = new JButton(new ImageIcon(iconOpen));
        openButton.setToolTipText("Open");
        openButton.addActionListener(e -> onOpen());
        functionBar.add(openButton);


        icon = new ImageIcon(this.getClass().getResource("/main/resources/save.png"));
        Image iconSave = icon.getImage().getScaledInstance(30, 30, 0);
        JButton saveButton = new JButton(new ImageIcon(iconSave));
        saveButton.setToolTipText("Save");
        saveButton.addActionListener(e -> onSave());
        functionBar.add(saveButton);


        JButton impactValuesButton = new JButton("0.3");
        impactValuesButton.setToolTipText("Display impact Values");
        impactValuesButton.addActionListener(e -> onImpactPressed());
        functionBar.add(impactValuesButton);


        icon = new ImageIcon(this.getClass().getResource("/main/resources/intersect.png"));
        Image iconIntersect = icon.getImage().getScaledInstance(30, 30, 0);
        JButton xorButton = new JButton(new ImageIcon(iconIntersect));
        xorButton.setToolTipText("Xor");
        xorButton.addActionListener(e -> gameController.onXor());
        functionBar.add(xorButton);


        icon = new ImageIcon(this.getClass().getResource("/main/resources/repeat.png"));
        Image iconRepeat = icon.getImage().getScaledInstance(30, 30, 0);
        JButton replaceButton = new JButton(new ImageIcon(iconRepeat));
        replaceButton.setToolTipText("Replace");
        replaceButton.addActionListener(e -> gameController.onXor());
        functionBar.add(replaceButton);


        icon = new ImageIcon(this.getClass().getResource("/main/resources/delete.png"));
        Image iconDelete = icon.getImage().getScaledInstance(30, 30, 0);
        JButton clearButton = new JButton(new ImageIcon(iconDelete));
        clearButton.setToolTipText("Clear");
        clearButton.addActionListener(e -> onClear());
        functionBar.add(clearButton);


        icon = new ImageIcon(this.getClass().getResource("/main/resources/settings.png"));
        Image iconSettings = icon.getImage().getScaledInstance(30, 30, 0);
        JButton parametersButton = new JButton(new ImageIcon(iconSettings));
        parametersButton.setToolTipText("Settings");
        parametersButton.addActionListener(e -> onSettingsPressed());
        functionBar.add(parametersButton);

        icon = new ImageIcon(this.getClass().getResource("/main/resources/Run.gif"));
        Image iconRun = icon.getImage().getScaledInstance(30, 30, 0);
        JButton runButton = new JButton(new ImageIcon(iconRun));
        runButton.setToolTipText("Run");
        runButton.addActionListener(e -> {
            if (!runButton.isSelected()) {
                gameController.onRun();
            }
            else {
                gameController.onStop();
            }
        });
        functionBar.add(runButton);


        icon = new ImageIcon(this.getClass().getResource("/main/resources/next.png"));
        Image iconNext = icon.getImage().getScaledInstance(30, 30, 0);
        JButton stepButton = new JButton(new ImageIcon(iconNext));
        stepButton.setToolTipText("Step");
        stepButton.addActionListener(e->onNext());
        functionBar.add(stepButton);

        icon = new ImageIcon(this.getClass().getResource("/main/resources/info.png"));
        Image iconInfo = icon.getImage().getScaledInstance(30, 30, 0);
        JButton helpButton = new JButton(new ImageIcon(iconInfo));
        helpButton.setToolTipText("About");
        helpButton.addActionListener(e-> JOptionPane.showMessageDialog(this, "Андриянов Алексей Фит 3 курс 16208"));
        functionBar.add(helpButton);


        functionBar.setVisible(true);
        add(functionBar, BorderLayout.PAGE_START);

        gameController = new GameController(gameModel, this);
        field = new HexField(gameModel, gameController);

        file.setVisible(true);
        add(field,BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(field);
        scrollPane.setPreferredSize(field.getPreferredSize());
        add(new JPanel().add(scrollPane));

    }


    public HexField getField() {
        return field;
    }

    public void onNew() {
        gameController.onNew();
    }

    public void onOpen() {
        gameController.onOpen();
    }

    public void onSave() {
        gameController.onSave();
    }


    public void onNext() {
        gameController.onNext();
    }


    public void onImpactPressed() {
        gameController.onImpactPressed();
    }

    public void onClear() {
        gameController.onClear();
    }


    public void onSettingsPressed() {
        gameController.onSettingsPressed();
    }
}