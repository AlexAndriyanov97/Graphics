package ru.nsu.fit.g16208.andriyanov.graphics.model;

import java.awt.*;
import java.util.function.BiFunction;

public class Func implements ru.nsu.fit.g16208.andriyanov.graphics.model.IFuncModel {
    private Double min;
    private Double max;
    private double[] valueOfIsolines;

    private BiFunction<Double, Double, Double> mainFunction;
    private FieldOfDefinition fieldOfDefinition;
    private Settings settings;
    private Color colorOfIsoline;

    public Func(BiFunction<Double, Double, Double> function, FieldOfDefinition field, Settings settings) {
        mainFunction = function;
        fieldOfDefinition = field;
        this.settings = settings;
        colorOfIsoline = settings.GetColorOfIsoline();
    }

    @Override
    public Color GetColorOfIsoline() {
        return colorOfIsoline;
    }

    @Override
    public double GetMax() {
        if (max == null) {
            max = mainFunction.apply((double) fieldOfDefinition.getA(), (double) fieldOfDefinition.getC());
            for (int i = fieldOfDefinition.getA(); i <= fieldOfDefinition.getB(); i++) {
                for (int j = fieldOfDefinition.getC(); j <= fieldOfDefinition.getD(); j++) {
                    double value = Calculate(i, j);
                    max = (value > max) ? value : max;
                }
            }
        }
        return max;
    }

    @Override
    public double GetMin() {
        if (min == null) {
            min = mainFunction.apply((double) fieldOfDefinition.getA(), (double) fieldOfDefinition.getC());
            for (int x = fieldOfDefinition.getA(); x <= fieldOfDefinition.getB(); x++) {
                for (int y = fieldOfDefinition.getC(); y <= fieldOfDefinition.getD(); y++) {
                    double value = Calculate(x, y);
                    min = (value < min) ? value : min;
                }
            }
        }
        return min;
    }

    @Override
    public FieldOfDefinition GetFieldOfDefinition() {
        return fieldOfDefinition;
    }

    @Override
    public Color GetColorByValue(double value, boolean isInterpolate) {
        if (isInterpolate) {
            return GetInterpolatedColorByValue(value);
        }
        Color[] colorsOfLegend = settings.GetColorsOfLegend();
        double[] valueOfIsolines = GetAllValuesOfIsolines();
        for (int i = 0; i < settings.GetN(); i++) {
            if (value < valueOfIsolines[i]) {
                return colorsOfLegend[i];
            }
        }
        return colorsOfLegend[settings.GetN()];
    }


    @Override
    public void SetFieldOfDeFinition(FieldOfDefinition fieldOfDefinition) {
        this.fieldOfDefinition = fieldOfDefinition;
    }

    @Override
    public Color GetColorByValue(double value) {
        return GetColorByValue(value, false);
    }

    private Color GetInterpolatedColorByValue(double value) {
        Color[] colorsOfLegend = settings.GetColorsOfLegend();
        double[] valuesOfIsolines = GetAllValuesOfIsolines();

        double min = GetMin();

        double width = valuesOfIsolines[0] - min;

        int position = (int) ((value - min - width / 2) / width);

        if (value - min < width / 2) {
            return colorsOfLegend[0];
        }
        if (GetMax() - value < width / 2) {
            return colorsOfLegend[colorsOfLegend.length - 1];
        }


        double leftEdge = min + width / 2 + position * width;
        double rightEdge = min + width / 2 + (position + 1) * width;
        return Interpolate(colorsOfLegend[position], colorsOfLegend[position + 1], leftEdge, rightEdge, value);
    }


    private Color Interpolate(Color firstColor, Color secondColor, double leftEdge, double rightEdge, double value) {
        int R = (int) (firstColor.getRed() * (rightEdge - value) / (rightEdge - leftEdge) + secondColor.getRed() * (value - leftEdge) / (rightEdge - leftEdge));
        int G = (int) (firstColor.getGreen() * (rightEdge - value) / (rightEdge - leftEdge) + secondColor.getGreen() * (value - leftEdge) / (rightEdge - leftEdge));
        int B = (int) (firstColor.getBlue() * (rightEdge - value) / (rightEdge - leftEdge) + secondColor.getBlue() * (value - leftEdge) / (rightEdge - leftEdge));
        return new Color(R, G, B);
    }

    @Override
    public double Calculate(double x, double y) {
        double value = mainFunction.apply(x, y);
        return mainFunction.apply(x, y);
    }

    @Override
    public ru.nsu.fit.g16208.andriyanov.graphics.model.Settings GetSettings() {
        return settings;
    }

    @Override
    public double[] GetAllValuesOfIsolines() {
        if (valueOfIsolines == null) {
            valueOfIsolines = new double[settings.GetN()];
            double sizeOfStep = (GetMax() - GetMin()) / (settings.GetN() + 1);
            double minOfFunc = GetMin();
            for (int i = 0; i < settings.GetN(); i++) {
                valueOfIsolines[i] = minOfFunc + (i + 1) * sizeOfStep;
            }
        }
        return valueOfIsolines;

    }

    @Override
    public void SetSettings(ru.nsu.fit.g16208.andriyanov.graphics.model.Settings settings) {
        this.settings = settings;
        valueOfIsolines = null;
    }
}
