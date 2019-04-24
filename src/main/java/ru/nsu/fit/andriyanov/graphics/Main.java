package main.java.ru.nsu.fit.andriyanov.graphics;

import main.java.ru.nsu.fit.andriyanov.graphics.controller.MainController;
import main.java.ru.nsu.fit.andriyanov.graphics.model.FieldOfDefinition;
import main.java.ru.nsu.fit.andriyanov.graphics.model.Func;
import main.java.ru.nsu.fit.andriyanov.graphics.model.Settings;

import java.awt.*;
import java.util.function.BiFunction;

public class Main {

    public static void main(String[] args) {
        Settings settings = new Settings(30, 30, 10, new Color[]{
                new Color(25, 49, 83),
                new Color(0, 56, 123),
                new Color(0, 79, 124),
                new Color(0, 137, 182),
                new Color(96, 147, 172),
                new Color(70, 135, 127),
                new Color(122, 136, 142),
                new Color(146, 136, 111),
                new Color(137, 105, 62),
                new Color(141, 73, 49),
                new Color(90, 56, 38)
        }, Color.BLACK);

        BiFunction<Double, Double, Double> function = (x, y) -> x * y + x * y;
        FieldOfDefinition fieldOfDefinition = new FieldOfDefinition(-5, 5, -5, 5);
        Func func = new Func(function, fieldOfDefinition, settings);

        MainController mainController = new MainController(func);
    }
}
