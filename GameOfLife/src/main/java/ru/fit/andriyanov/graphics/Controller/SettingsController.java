package main.java.ru.fit.andriyanov.graphics.Controller;

import main.java.ru.fit.andriyanov.graphics.Model.Model;
import main.java.ru.fit.andriyanov.graphics.View.SettingsView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

public class SettingsController {
    private Model gameModel;
    private SettingsView settingsView;


    public SettingsController(Model gameModel, SettingsView settingsView) {
        this.gameModel = gameModel;
        this.settingsView = settingsView;

    }

    public void onWidthComponentChanged(ChangeEvent event) {
        int value = getValueFromEvent(event);
        gameModel.setFieldWidth(value);
    }

    public void onHeightComponentChanged(ChangeEvent event) {
        int value = getValueFromEvent(event);
        gameModel.setFieldHeight(value);
    }

    public void onLifeBeginChanged(ChangeEvent event) {
        double value = (double)((JSpinner)event.getSource()).getValue();
        gameModel.setLifeBegin(value);
    }

    public void onLifeEndChanged(ChangeEvent event) {
        double value = (double)((JSpinner)event.getSource()).getValue();
        gameModel.setLifeEnd(value);
    }

    public void onBirthBeginChanged(ChangeEvent event) {
        double value = (double)((JSpinner)event.getSource()).getValue();
        gameModel.setBirthBegin(value);
    }

    public void onBirthEndChanged(ChangeEvent event) {
        double value = (double)((JSpinner)event.getSource()).getValue();
        gameModel.setBirthEnd(value);
    }

    public void onFirstImpactChanged(ChangeEvent event) {
        double value = (double)((JSpinner)event.getSource()).getValue();
        gameModel.setFirstImpact(value);
    }

    public void onSecondImpactChanged(ChangeEvent event) {
        double value = (double)((JSpinner)event.getSource()).getValue();
        gameModel.setSecondImpact(value);
    }

    public void onRadiusChanged(ChangeEvent event) {
        int value = (int)((JSpinner)event.getSource()).getValue();
        gameModel.setRadius(value);
    }

    public void onThicknessChanged(ChangeEvent event) {
        int value = (int)((JSpinner)event.getSource()).getValue();
        gameModel.setThickness(value);
    }

    private int getValueFromEvent(ChangeEvent event) {
        Object source = event.getSource();
        int value;
        if (source instanceof JSlider) {
            JSlider slider = (JSlider) source;
            value = slider.getValue();
        } else if (event.getSource() instanceof JSpinner) {
            JSpinner spinner = (JSpinner) event.getSource();
            value = (int)spinner.getValue();
        } else {
            throw new ClassCastException("Wrong type of component");
        }
        return value;
    }

    public void onOkButtonPressed(ActionEvent event) {
        settingsView.setVisible(false);
        settingsView.dispose();
    }

    public void onCancelButtonPressed(ActionEvent event) {
        settingsView.setVisible(false);
        settingsView.dispose();
    }
}

