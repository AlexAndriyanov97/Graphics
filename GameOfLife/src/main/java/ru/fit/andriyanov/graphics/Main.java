package main.java.ru.fit.andriyanov.graphics;

import main.java.ru.fit.andriyanov.graphics.Model.Model;
import main.java.ru.fit.andriyanov.graphics.Model.Settings.SettingsGame;
import main.java.ru.fit.andriyanov.graphics.View.GameView;

public  class Main{

    public static void main(String[] args) {
        SettingsGame settingsModel = new SettingsGame();
        Model gameModel = new Model(settingsModel);
        GameView gameView = new GameView(gameModel);
        gameView.setVisible(true);
    }
}