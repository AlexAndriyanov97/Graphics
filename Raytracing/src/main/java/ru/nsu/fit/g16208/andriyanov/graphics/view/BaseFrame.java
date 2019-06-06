package ru.nsu.fit.g16208.andriyanov.graphics.view;

import javax.swing.*;

abstract public class BaseFrame extends JFrame {
    protected JMenuBar menuBar = new JMenuBar();

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
}