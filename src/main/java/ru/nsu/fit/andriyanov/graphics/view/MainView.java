package main.java.ru.nsu.fit.andriyanov.graphics.view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JMenuBar menuBar;
    private JToolBar functionBar;

    public MainView() {
    }

    private void buildMenuBar(){
        menuBar = new JMenuBar();
        JMenu file = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> {
        });
        file.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> {
        });
        file.add(saveItem);


        JMenuItem edit = new JMenu("Edit");
        menuBar.add(edit);

        JMenuItem init = new JMenuItem("Init");
        init.addActionListener(e -> {});
        edit.add(init);

        JMenuItem settings = new JMenuItem("Settings");
        settings.addActionListener(e -> {});
        edit.add(settings);



        JMenu about = new JMenu("About");
        JMenuItem helpItem = new JMenuItem("About");
        helpItem.addActionListener(e->JOptionPane.showMessageDialog(this  ,"Андриянов Алексей Фит 3 курс 16208"));
        about.add(helpItem);
        menuBar.add(about);
        setJMenuBar(menuBar);


        functionBar = new JToolBar();
        functionBar.setLayout(new BoxLayout(functionBar, BoxLayout.X_AXIS));
        functionBar.setPreferredSize(new Dimension(0, 40));

        ImageIcon icon = new ImageIcon(this.getClass().getResource("/main/resources/Open.png"));
        Image iconOpen = icon.getImage().getScaledInstance(30, 30, 0);
        JButton openButton = new JButton(new ImageIcon(iconOpen));
        openButton.setToolTipText("Open");
        openButton.addActionListener(e -> {
        });
        functionBar.add(openButton);


        icon = new ImageIcon(this.getClass().getResource("/main/resources/Save.png"));
        Image iconSave = icon.getImage().getScaledInstance(30, 30, 0);
        JButton saveButton = new JButton(new ImageIcon(iconSave));
        saveButton.setToolTipText("Save");
        saveButton.addActionListener(e -> {});
        functionBar.add(saveButton);


        icon = new ImageIcon(this.getClass().getResource("/main/resources/Init.png"));
        Image iconInit = icon.getImage().getScaledInstance(30, 30, 0);
        JButton initButton = new JButton(new ImageIcon(iconInit));
        initButton.setToolTipText("Init");
        initButton.addActionListener(e -> {});
        functionBar.add(initButton);



        icon = new ImageIcon(this.getClass().getResource("/main/resources/Settings.png"));
        Image iconSettings = icon.getImage().getScaledInstance(30, 30, 0);
        JButton settingsButton = new JButton(new ImageIcon(iconSettings));
        settingsButton.setToolTipText("Settings");
        settingsButton.addActionListener(e -> {});
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
    }
}