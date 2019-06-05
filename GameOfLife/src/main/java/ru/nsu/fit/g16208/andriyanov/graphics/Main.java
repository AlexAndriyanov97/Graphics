package ru.nsu.fit.g16208.andriyanov.graphics;

import ru.nsu.fit.g16208.andriyanov.graphics.Model.Model;
import ru.nsu.fit.g16208.andriyanov.graphics.Model.Settings.SettingsGame;
import ru.nsu.fit.g16208.andriyanov.graphics.View.GameView;

public  class Main{

    public static void main(String[] args) {
        SettingsGame settingsModel = new SettingsGame();
        Model gameModel = new Model(settingsModel);
        GameView gameView = new GameView(gameModel);
        gameView.setVisible(true);
    }
}