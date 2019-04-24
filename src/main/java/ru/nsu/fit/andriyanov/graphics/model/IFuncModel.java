package main.java.ru.nsu.fit.andriyanov.graphics.model;

import java.awt.*;

public interface IFuncModel {
    double GetMax();
    double GetMin();

    FieldOfDefinition GetFieldOfDefinition();

    Color GetColorByValue(double value,boolean isInterpolate);

    Color GetColorByValue(double value);

    double Calculate(double x,double y);

    Settings GetSettings();

    void SetSettings(Settings settings);

    double[] GetAllValuesOfIsolines();


}
