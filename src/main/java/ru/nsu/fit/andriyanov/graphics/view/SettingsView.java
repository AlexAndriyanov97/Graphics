package main.java.ru.nsu.fit.andriyanov.graphics.view;

import main.java.ru.nsu.fit.andriyanov.graphics.model.FieldOfDefinition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField gridSizeTextField;
    private JTextField kTextField;
    private JTextField mTextField;
    private JSpinner value_k;
    private JSpinner value_m;
    private JTextField xFromToTextField;
    private JSpinner to_y;
    private JSpinner from_y;
    private JSpinner from_x;
    private JSpinner to_x;
    private JTextField yFromToTextField;
    private JTextField fieldOfDefinitionTextField;

    private int k;
    private int m;
    private FieldOfDefinition fieldOfDefinition;

    public SettingsView(int k, int m, FieldOfDefinition fieldOfDefinition) {
        this.k = k;
        value_k.setValue(k);
        this.m = m;
        value_m.setValue(m);
        this.fieldOfDefinition = fieldOfDefinition;
        from_x.setValue(fieldOfDefinition.getA());
        to_x.setValue(fieldOfDefinition.getB());
        from_y.setValue(fieldOfDefinition.getC());
        to_y.setValue(fieldOfDefinition.getD());
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setSize(new Dimension(400, 500));
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    public int getK() {
        return k;
    }

    public int getM() {
        return m;
    }

    public FieldOfDefinition getFieldOfDefinition() {
        return fieldOfDefinition;
    }

    private void onOK() {
        k = (int) value_k.getValue();
        m = (int) value_m.getValue();
        fieldOfDefinition.setA((int) from_x.getValue());
        fieldOfDefinition.setB((int) to_x.getValue());
        fieldOfDefinition.setC((int) from_y.getValue());
        fieldOfDefinition.setD((int) to_y.getValue());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
