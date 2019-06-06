package ru.nsu.fit.g16208.andriyanov.graphics.view;

import ru.nsu.fit.g16208.andriyanov.graphics.model.Point3D;
import ru.nsu.fit.g16208.andriyanov.graphics.model.RenderModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField brTextField;
    private JTextField bbTextField;
    private JTextField bgTextField;
    private JTextField bb;
    private JTextField bg;
    private JTextField br;
    private JTextField qualityTextField;
    private JTextField depthTextField;
    private JTextField gammaTextField;
    private JTextField quality;
    private JTextField depth;
    private JTextField gamma;
    private JTextField eyeZTextField;
    private JTextField eyeYTextField;
    private JTextField eyeXTextField;
    private JTextField eyeZ;
    private JTextField eyeY;
    private JTextField eyeX;
    private JTextField viewZTextField;
    private JTextField viewYTextField;
    private JTextField viewXTextField;
    private JTextField viewZ;
    private JTextField viewY;
    private JTextField viewX;
    private JTextField upZTextField;
    private JTextField upYTextField;
    private JTextField upXTextField;
    private JTextField upX;
    private JTextField ZNTextField;
    private JTextField ZFTextField;
    private JTextField SWTextField;
    private JTextField zn;
    private JTextField upY;
    private JTextField zf;
    private JTextField sw;
    private JTextField upZ;
    private JTextField SHTextField;
    private JTextField sh;

    private RenderModel renderModel;

    public SettingsView(RenderModel renderModel) {
        this.renderModel = renderModel;
        br.setText(String.valueOf(renderModel.getBackgroundColor().getRed()));
        bg.setText(String.valueOf(renderModel.getBackgroundColor().getGreen()));
        bb.setText(String.valueOf(renderModel.getBackgroundColor().getBlue()));

        depth.setText(String.valueOf(renderModel.getDepth()));

        eyeX.setText(String.valueOf(renderModel.getDirection().getX()));
        eyeY.setText(String.valueOf(renderModel.getDirection().getY()));
        eyeZ.setText(String.valueOf(renderModel.getDirection().getZ()));

        viewX.setText(String.valueOf(renderModel.getCenter().getX()));
        viewY.setText(String.valueOf(renderModel.getCenter().getY()));
        viewZ.setText(String.valueOf(renderModel.getCenter().getZ()));

        upX.setText(String.valueOf(renderModel.getUp().getX()));
        upY.setText(String.valueOf(renderModel.getUp().getY()));
        upZ.setText(String.valueOf(renderModel.getUp().getZ()));

        zn.setText(String.valueOf(renderModel.getZn()));
        zf.setText(String.valueOf(renderModel.getZf()));
        sh.setText(String.valueOf(renderModel.getSh()));
        sw.setText(String.valueOf(renderModel.getSw()));


        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

    private void onOK() {
        renderModel.setBackgroundColor(new Color(Integer.parseInt(br.getText()), Integer.parseInt(bg.getText()), Integer.parseInt(bb.getText())));
        renderModel.setDepth(Integer.parseInt(depth.getText()));

        renderModel.setZf(Double.parseDouble(zf.getText()));
        renderModel.setZn(Double.parseDouble(zn.getText()));
        renderModel.setSw(Double.parseDouble(sw.getText()));
        renderModel.setSh(Double.parseDouble(sh.getText()));

        renderModel.setUp(new Point3D(Double.parseDouble(upX.getText()), Double.parseDouble(upY.getText()), Double.parseDouble(upZ.getText())));
        renderModel.setCenter(new Point3D(Double.parseDouble(viewX.getText()), Double.parseDouble(viewY.getText()), Double.parseDouble(viewZ.getText())));
        renderModel.setUp(new Point3D(Double.parseDouble(eyeX.getText()), Double.parseDouble(eyeY.getText()), Double.parseDouble(eyeZ.getText())));
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
