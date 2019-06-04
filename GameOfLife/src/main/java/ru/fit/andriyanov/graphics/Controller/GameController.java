package main.java.ru.fit.andriyanov.graphics.Controller;

import main.java.ru.fit.andriyanov.graphics.Model.HexCoord;
import main.java.ru.fit.andriyanov.graphics.Model.Model;
import main.java.ru.fit.andriyanov.graphics.Model.Settings.SettingsGame;
import main.java.ru.fit.andriyanov.graphics.View.GameView;
import main.java.ru.fit.andriyanov.graphics.View.SettingsView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class GameController {

    private Model gameModel;
    private GameView view;

    private Timer timer;
    private static final int DELAY = 1000;

    private SettingsView settingsView;
    private boolean xorMode = false;

    public GameController(Model gameModel, GameView view) {
        this.gameModel = gameModel;
        this.view = view;
        timer = new Timer(DELAY, (e) -> gameModel.nextState());

    }

    public void mouseClicked(Point click) {
        HexCoord hc = countHexCoordByClick(click);
        if (xorMode) {
            gameModel.setHexState(hc.j, hc.k, !gameModel.getHexState(hc.j, hc.k));
        } else {
            gameModel.setHexState(hc.j, hc.k, true);
        }
    }

    private HexCoord prevHex = null;

    public void mouseDragged(Point p) {
        HexCoord hc = countHexCoordByClick(p);
        if (hc.j >= 0 && hc.k >= 0
                && hc.k < gameModel.getFieldWidth() && hc.j < gameModel.getFieldHeight()) {
            if (prevHex != null) {
                if (prevHex.j != hc.j ||
                        prevHex.k != hc.k) {
                    if (xorMode) {
                        gameModel.setHexState(hc.j, hc.k, !gameModel.getHexState(hc.j, hc.k));
                    } else {
                        gameModel.setHexState(hc.j, hc.k, true);
                    }
                }
            }
            prevHex = hc;
        }
    }

    public void onNew() {
        gameModel.setSettingsModel(new SettingsGame());
    }

    public void onOpen() {

        JFileChooser fileOpen = new JFileChooser("user.dir");
        try {

            int ret = fileOpen.showDialog(null, "Open file");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                SettingsGame settingsModel = FileReader.loadFile(file);
                gameModel.setSettingsModel(settingsModel);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can not open file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void onSave() {
        JFileChooser fileSave = new JFileChooser("user.dir");
        try {
            int ret = fileSave.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileSave.getSelectedFile();
                FileReader.saveFile(gameModel.getSettingsModel(), gameModel, file);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can not open file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void onNext() {
        gameModel.nextState();
    }

    public void onRun() {
        timer.start();
    }

    public void onStop() {
        timer.stop();
    }

    public void onImpactPressed() {
        view.getField().setImpactsEnabled(!view.getField().isImpactsEnabled());
    }

    public void onClear() {
        gameModel.clearField();
    }

    public void onXor(boolean value) {
        xorMode = value;
    }

    public void onXor() {
        xorMode = !xorMode;
    }


    public void onSettingsPressed() {
        settingsView = new SettingsView(gameModel);

        settingsView.setSize(420, 260);
        settingsView.setVisible(true);
    }


    private HexCoord countHexCoordByClick(Point click) {
        int j = click.y / (3 * view.getField().getHalfRad());
        int k = (click.x - view.getField().getHalfHexWidth() * (j % 2)) / (2 * view.getField().getHalfHexWidth());

        HexCoord firstCandidate = new HexCoord(j - 1, k);
        HexCoord secondCandidate = new HexCoord(j - 1, k - 1 + (j % 2) * 2);
        HexCoord thirdCandidate = new HexCoord(j, k);

        return findClosest(
                click,
                firstCandidate,
                secondCandidate,
                thirdCandidate);
    }

    private HexCoord findClosest(Point click, HexCoord... candidates) {
        int dist[] = new int[candidates.length];
        for (int i = 0; i < candidates.length; i++) {
            dist[i] = countDist(click, view.getField().getHexCenter(candidates[i].j, candidates[i].k));
        }
        int minIndex = 0;
        for (int i = 1; i < dist.length; i++) {
            if (dist[i] < dist[minIndex]) {
                minIndex = i;
            }
        }
        return candidates[minIndex];
    }

    private int countDist(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }


}
