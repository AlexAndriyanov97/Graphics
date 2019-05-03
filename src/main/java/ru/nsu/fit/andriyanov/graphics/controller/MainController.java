package main.java.ru.nsu.fit.andriyanov.graphics.controller;

import main.java.ru.nsu.fit.andriyanov.graphics.model.FieldOfDefinition;
import main.java.ru.nsu.fit.andriyanov.graphics.model.Func;
import main.java.ru.nsu.fit.andriyanov.graphics.view.MainView;
import main.java.ru.nsu.fit.andriyanov.graphics.view.SettingsView;

import javax.swing.*;
import java.util.function.BiFunction;

public class MainController {
    private MainView mainView;
    private Func functionModel;
    private Func functionLegend;

    public MainController(Func functionModel) {
        this.functionModel = functionModel;
        BiFunction<Double, Double, Double> functionForLegend = (x, y) -> x;
        this.functionLegend = new Func(functionForLegend, functionModel.GetFieldOfDefinition(), functionModel.GetSettings());
        this.mainView = new MainView(this, functionModel, functionLegend);
        this.mainView.setSize(800, 600);
        this.mainView.setVisible(true);
    }

    public void GridPressed() {
        mainView.ChangeGridState();
    }

    public void PaintPressed() {
        mainView.ChangePaintState();
    }

    public void DotsPressed() {
        mainView.ChangeDotsState();
    }

    public void IsolinePressed() {
        mainView.ChangeIsolineState();
    }

    public void InterpolatePressed() {
        mainView.ChangeInterpolate();
    }

    public void SettingsPressed() {
        SettingsView settingsView = new SettingsView(functionModel.GetSettings().GetK(), functionModel.GetSettings().GetM(), functionModel.GetFieldOfDefinition());
        settingsView.setLocationRelativeTo(mainView);
        settingsView.setVisible(true);
        int k = settingsView.getK();
        int m = settingsView.getM();
        FieldOfDefinition fieldOfDefinition = settingsView.getFieldOfDefinition();
        functionModel.GetSettings().SetK(k);
        functionModel.GetSettings().SetM(m);
        functionModel.SetFieldOfDeFinition(fieldOfDefinition);
        mainView.repaint();
    }


}
