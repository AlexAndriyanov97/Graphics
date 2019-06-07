package ru.nsu.fit.g16208.andriyanov.graphics.view;

import javax.swing.*;
import java.awt.*;

abstract public class BaseFrame extends JFrame {
    protected JMenuBar menuBar = new JMenuBar();
    protected JToolBar functionBar;


    public BaseFrame() {
        functionBar = new JToolBar();
        functionBar.setLayout(new BoxLayout(functionBar, BoxLayout.X_AXIS));
        functionBar.setPreferredSize(new Dimension(0, 40));
    }

    protected JMenu createMenu(String name) {
        JMenu menu = new JMenu(name);
        menuBar.add(menu);
        return menu;
    }

    protected JMenuItem createMenuItem(JMenu menu, String name, Runnable action) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(e -> action.run());
        menu.add(item);
        return item;
    }

    protected void createToolItem(String path, String name, Runnable action) {
        ImageIcon icon = new ImageIcon(this.getClass().getResource(path));
        Image image = icon.getImage().getScaledInstance(30, 30, 0);
        JButton button = new JButton(new ImageIcon(image));
        button.setToolTipText(name);
        button.addActionListener(e -> {
            action.run();
        });
        functionBar.add(button);
    }

}