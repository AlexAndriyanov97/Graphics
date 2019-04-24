package main.java.ru.nsu.fit.andriyanov.graphics.controller;

import main.java.ru.nsu.fit.andriyanov.graphics.model.Func;
import main.java.ru.nsu.fit.andriyanov.graphics.view.MainView;

public class MainController {
    private MainView mainView;
    private Func functionModel;


    public MainController(Func functionModel) {
        this.functionModel = functionModel;
        this.mainView = new MainView(this,functionModel);
        this.mainView.setSize(800,600);
        this.mainView.setVisible(true);
    }

    public void GridPressed(){
        mainView.ChangeGridState();
    }

    public void PaintPressed(){
        mainView.ChangePaintState();
    }

    public void DotsPressed(){
        mainView.ChangeDotsState();
    }

    public void IsolinePressed(){
        mainView.ChangeIsolineState();
    }
}
