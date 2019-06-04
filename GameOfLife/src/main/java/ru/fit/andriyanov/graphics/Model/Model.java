package main.java.ru.fit.andriyanov.graphics.Model;

import main.java.ru.fit.andriyanov.graphics.Model.Settings.SettingsEvent;
import main.java.ru.fit.andriyanov.graphics.Model.Settings.SettingsGame;

import java.util.LinkedList;
import java.util.List;

public class Model extends Observable<GameEvent> {
    public static final int SIDES = 6;

    private SettingsGame settingsModel;

    private boolean[] fieldState;


    public void clearField() {
        for (int i = 0; i < fieldState.length; i++) {
            fieldState[i] = false;
        }
        notifyObservers(GameEvent.STATE_CHANGED, fieldState);
    }

    public Model(SettingsGame settingsGame) {
        this.settingsModel = settingsGame;
    }

    private void onFieldWidthChanged(Object newWidth) {
        boolean[] newField = new boolean[(int)newWidth * getFieldHeight()];
        int oldWidth = fieldState.length / getFieldHeight();
        int border = oldWidth < (int)newWidth ? oldWidth : (int)newWidth;
        for (int j = 0; j < getFieldHeight(); j++) {
            boolean even = j % 2 == 0;
            for (int k = 0; k < (even ? border : border - 1); k++) {
                newField[j*(int)newWidth + k] = fieldState[j*oldWidth + k];
            }
        }
        fieldState = newField;
        notifyObservers(GameEvent.FIELD_WIDTH_CHANGED, newWidth);
    }


    private void onFieldHeightChanged(Object newHeight) {
        boolean[] newField = new boolean[getFieldWidth() * (int)newHeight];
        int oldHeight = fieldState.length / getFieldWidth();
        int border = oldHeight < (int)newHeight ? oldHeight : (int)newHeight;
        for (int j = 0; j < border; j++) {
            boolean even = j % 2 == 0;
            for (int k = 0; k < (even ? getFieldWidth() : getFieldWidth() - 1); k++) {
                newField[j*getFieldWidth() + k] = fieldState[j*getFieldWidth() + k];
            }
        }
        fieldState = newField;
        notifyObservers(GameEvent.FIELD_HEIGHT_CHANGED, newHeight);
    }

    private void onRadiusChanged(Object newRadius) {
        notifyObservers(GameEvent.RADIUS_CHANGED, newRadius);
    }

    private void onThicknessChanged(Object newThickness) {
        notifyObservers(GameEvent.THICKNESS_CHANGED, newThickness);
    }
    public void nextState() {
        boolean[] newFieldState = new boolean[settingsModel.getHeightOfMap() * settingsModel.getWidthOfMap()];

        for (int j = 0; j < getFieldHeight(); j++) {
            boolean even = j % 2 == 0;
            for (int k = 0; k < (even ? getFieldWidth() : getFieldWidth() - 1); k++) {
                double impact = countImpact(j, k);

                newFieldState[j*getFieldWidth() + k] = fieldState[j*getFieldWidth() + k];
                if (!fieldState[j*getFieldWidth() + k]) {
                    if (impact >= settingsModel.getBirthBegin()) {
                        if (impact <= settingsModel.getBirthEnd()) {
                            newFieldState[j*getFieldWidth() + k] = true;
                        }
                    }
                } else {
                    if (impact < settingsModel.getLifeBegin() ||
                            impact > settingsModel.getLifeEnd()) {
                        newFieldState[j*getFieldWidth() + k] = false;
                    }
                }
            }
        }
        fieldState = newFieldState;
        notifyObservers(GameEvent.STATE_CHANGED, fieldState);
    }

    public double countImpact(int j, int k) {
        List<HexCoord> fNeighbours = getFirstLevelNeighbours(j, k);
        List<HexCoord> sNeighbours = getSecondLevelNeighbours(j, k);

        return settingsModel.getFirstImpact() * getAliveNeighbours(fNeighbours) +
                settingsModel.getSecondImpact() * getAliveNeighbours(sNeighbours);

    }

    public boolean getHexState(int j, int k) {
        return fieldState[j * getFieldWidth() + k];
    }

    public void setHexState(int j, int k, boolean state) {
        fieldState[j * getFieldWidth() + k] = state;
        notifyObservers(GameEvent.STATE_CHANGED, fieldState);
    }

    private void setInitState() {
        fieldState = settingsModel.getStartFieldState();
        notifyObservers(GameEvent.STATE_CHANGED, fieldState);

        settingsModel.registerObserver(this::onFieldWidthChanged, SettingsEvent.FIELD_WIDTH_CHANGED);
        settingsModel.registerObserver(this::onFieldHeightChanged, SettingsEvent.FIELD_HEIGHT_CHANGED);
        settingsModel.registerObserver(this::onRadiusChanged, SettingsEvent.RADIUS_CHANGED);
        settingsModel.registerObserver(this::onThicknessChanged, SettingsEvent.THICKNESS_CHANGED);
    }

    private List<HexCoord> getFirstLevelNeighbours(int j, int k) {
        HexCoord[] neighbours = new HexCoord[SIDES];
        neighbours[0] = new HexCoord(j, k - 1);
        neighbours[1] = new HexCoord(j, k + 1);
        neighbours[2] = new HexCoord(j - 1, k);
        neighbours[3] = new HexCoord(j + 1, k);
        neighbours[4] = new HexCoord(j - 1, k - 1 + 2 * (j % 2));
        neighbours[5] = new HexCoord(j + 1, k - 1 + 2 * (j % 2));

        return getValidCoord(neighbours);
    }

    private List<HexCoord> getSecondLevelNeighbours(int j, int k) {
        HexCoord[] neighbours = new HexCoord[SIDES];
        neighbours[0] = new HexCoord(j - 2, k);
        neighbours[1] = new HexCoord(j + 2, k);
        neighbours[2] = new HexCoord(j - 1, k - 2 + (j % 2));
        neighbours[3] = new HexCoord(j - 1, k + 1 + (j % 2));
        neighbours[4] = new HexCoord(j + 1, k - 2 + (j % 2));
        neighbours[5] = new HexCoord(j + 1, k + 1 + (j % 2));

        return getValidCoord(neighbours);
    }

    private List<HexCoord> getValidCoord(HexCoord[] coords) {
        List<HexCoord> result = new LinkedList<>();
        for (HexCoord hc : coords) {
            if (hc.j >= 0 && hc.k >= 0) {
                boolean even = hc.j % 2 == 0;
                if (hc.j < getFieldHeight() && hc.k < (even ? getFieldWidth() : getFieldWidth() - 1)) {
                    result.add(hc);
                }
            }
        }
        return result;
    }

    private int getAliveNeighbours(List<HexCoord> neighbours) {
        int result = 0;
        for (HexCoord hc : neighbours) {
            if (fieldState[hc.j * getFieldWidth() + hc.k]) {
                ++result;
            }
        }
        return result;
    }



    public int getFieldWidth() {
        return settingsModel.getWidthOfMap();
    }

    public int getFieldHeight() {
        return settingsModel.getHeightOfMap();
    }

    public boolean[] getFieldState() {
        return fieldState;
    }

    public double getLifeBegin() {
        return settingsModel.getLifeBegin();
    }

    public double getLifeEnd() {
        return settingsModel.getLifeEnd();
    }

    public double getBirthBegin() {
        return settingsModel.getBirthBegin();
    }

    public double getBirthEnd() {
        return settingsModel.getBirthEnd();
    }

    public double getFirstImpact() {
        return settingsModel.getFirstImpact();
    }

    public double getSecondImpact() {
        return settingsModel.getSecondImpact();
    }

    public int getRadius() {
        return settingsModel.getSizeOfHex();
    }

    public int getThickness() {
        return settingsModel.getThicknessOfLine();
    }

    public void setLifeBegin(double lifeBegin) {
        settingsModel.setLifeBegin(lifeBegin);
    }

    public void setLifeEnd(double lifeEnd) {
        settingsModel.setLifeEnd(lifeEnd);
    }


    public void setBirthBegin(double birthBegin) {
        settingsModel.setBirthBegin(birthBegin);
    }

    public void setBirthEnd(double birthEnd) {
        settingsModel.setBirthEnd(birthEnd);
    }

    public void setFirstImpact(double firstImpact) {
        settingsModel.setFirstImpact(firstImpact);
    }

    public void setSecondImpact(double secondImpact) {
        settingsModel.setSecondImpact(secondImpact);
    }

    public void setRadius(int radius) {
        settingsModel.setSizeOfHex(radius);
    }

    public void setThickness(int thickness) {
        settingsModel.setThickness(thickness);
    }


    public void setFieldWidth(int width) {
        settingsModel.setFieldWidth(width);
    }

    public void setFieldHeight(int height) {
        settingsModel.setFieldHeight(height);
    }


    public SettingsGame getSettingsModel() {
        return settingsModel;
    }

}
