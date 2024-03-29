package ru.nsu.fit.g16208.andriyanov.graphics.model;

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

    void SetFieldOfDeFinition(FieldOfDefinition fieldOfDefinition);

    Color GetColorOfIsoline();


    double[] GetAllValuesOfIsolines();


}
