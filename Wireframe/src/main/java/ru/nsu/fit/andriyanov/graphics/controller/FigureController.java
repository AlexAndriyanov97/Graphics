package main.java.ru.nsu.fit.andriyanov.graphics.controller;

import main.java.ru.nsu.fit.andriyanov.graphics.model.Figure3D;

public class FigureController {

    private Figure3D figure;
    private boolean isEnable;


    private final static FigureController INSTANCE = new FigureController();

    private FigureController() {}

    public static FigureController getInstance() {
        return INSTANCE;
    }


    public Figure3D getFigure() {
        return figure;
    }

    public void setFigure(Figure3D figure) {
        this.figure = figure;
    }



    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        this.isEnable = enable;
    }
}
