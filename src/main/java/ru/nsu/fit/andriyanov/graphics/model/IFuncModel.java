package main.java.ru.nsu.fit.andriyanov.graphics.model;

public interface IFuncModel {
    double[] GetIsolines();

    double GetMax();
    double GetMin();

    FieldOfDefinition GetFieldOfDefinition();
}
