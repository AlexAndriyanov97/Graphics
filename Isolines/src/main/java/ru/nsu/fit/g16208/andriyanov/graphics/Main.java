package ru.nsu.fit.g16208.andriyanov.graphics;

import ru.nsu.fit.g16208.andriyanov.graphics.controller.MainController;
import ru.nsu.fit.g16208.andriyanov.graphics.model.FieldOfDefinition;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Func;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Settings;

import java.awt.*;
import java.util.function.BiFunction;

public class Main {

    public static void main(String[] args) {
        Settings settings = new Settings(30, 30, 6, new Color[]{
                new Color(25, 49, 83),
                new Color(0, 56, 123),
                new Color(0, 79, 124),
                new Color(0, 137, 182),
                new Color(96, 147, 172),
                new Color(80,150,21),
                new Color(90, 56, 38)
        }, Color.BLACK);

        BiFunction<Double, Double, Double> function = (x, y) -> x * y + x * y;
        FieldOfDefinition fieldOfDefinition = new FieldOfDefinition(-5, 5, -5, 5);
        Func func = new Func(function, fieldOfDefinition, settings);

        MainController mainController = new MainController(func);
    }
}
