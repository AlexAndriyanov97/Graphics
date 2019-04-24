package main.java.ru.nsu.fit.andriyanov.graphics.controller;

import main.java.ru.nsu.fit.andriyanov.graphics.model.Func;
import main.java.ru.nsu.fit.andriyanov.graphics.view.MainView;

import javax.swing.*;
import java.util.function.BiFunction;

public class MainController {
    private MainView mainView;
    private Func functionModel;
    private Func functionLegend;

    public MainController(Func functionModel) {
        this.functionModel = functionModel;
        BiFunction<Double, Double, Double> functionForLegend = (x, y) -> (functionModel.GetMax() - functionModel.GetMin()) / functionModel.GetFieldOfDefinition().GetWidth() * x + (functionModel.GetMin() - (functionModel.GetMax() - functionModel.GetMin()) / functionModel.GetFieldOfDefinition().GetWidth() * functionModel.GetFieldOfDefinition().getA());
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


}
