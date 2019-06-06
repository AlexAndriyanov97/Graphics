package ru.nsu.fit.g16208.andriyanov.graphics.Model.Settings;

import ru.nsu.fit.g16208.andriyanov.graphics.Model.Observable;
import ru.nsu.fit.g16208.andriyanov.graphics.Model.Settings.SettingsEvent;

public class SettingsGame extends Observable<SettingsEvent> {

    private int widthOfMap;
    private int heightOfMap;
    private int sizeOfHex;
    private int thicknessOfLine;

    private double lifeBegin = 2.0;
    private double lifeEnd = 3.3;
    private double birthBegin = 2.3;
    private double birthEnd = 2.9;
    private double firstImpact = 1.0;
    private double secondImpact = 0.3;

    private boolean[] startFieldState;

    public SettingsGame(int widthOfMap, int heightOfMap, int sizeOfHex, int thicknessOfLine) {
        this.widthOfMap = widthOfMap;
        this.heightOfMap = heightOfMap;
        this.sizeOfHex = sizeOfHex;
        this.thicknessOfLine = thicknessOfLine;

        startFieldState = new boolean[widthOfMap * heightOfMap];
    }

    public SettingsGame() {
        this(40,40,30,1);
    }

    public boolean[] getStartFieldState() {
        return startFieldState;
    }

    public void setStartFieldState(boolean[] startFieldState) {
        this.startFieldState = startFieldState;
    }

    public int getWidthOfMap() {
        return widthOfMap;
    }

    public int getHeightOfMap() {
        return heightOfMap;
    }

    public int getSizeOfHex() {
        return sizeOfHex;
    }

    public int getThicknessOfLine() {
        return thicknessOfLine;
    }

    public double getLifeBegin() {
        return lifeBegin;
    }

    public double getLifeEnd() {
        return lifeEnd;
    }

    public double getBirthBegin() {
        return birthBegin;
    }

    public double getBirthEnd() {
        return birthEnd;
    }

    public double getFirstImpact() {
        return firstImpact;
    }

    public double getSecondImpact() {
        return secondImpact;
    }

    public void setLifeBegin(double lifeBegin) {
        this.lifeBegin = lifeBegin;
        notifyObservers(SettingsEvent.LIFE_BEGIN_CHANGED, lifeBegin);
    }

    public void setLifeEnd(double lifeEnd) {
        this.lifeEnd = lifeEnd;
        notifyObservers(SettingsEvent.LIFE_END_CHANGED, lifeEnd);
    }

    public void setBirthBegin(double birthBegin) {
        this.birthBegin = birthBegin;
        notifyObservers(SettingsEvent.BIRTH_BEGIN_CHANGED, birthBegin);
    }

    public void setBirthEnd(double birthEnd) {
        this.birthEnd = birthEnd;
        notifyObservers(SettingsEvent.BIRTH_END_CHANGED, birthEnd);
    }

    public void setFirstImpact(double firstImpact) {
        this.firstImpact = firstImpact;
        notifyObservers(SettingsEvent.FIRST_IMPACT_CHANGED, firstImpact);
    }

    public void setSecondImpact(double secondImpact) {
        this.secondImpact = secondImpact;
        notifyObservers(SettingsEvent.SECOND_IMPACT_CHANGED, secondImpact);
    }

    public void setFieldWidth(int fieldWidth) {
        widthOfMap = fieldWidth;
        notifyObservers(SettingsEvent.FIELD_WIDTH_CHANGED, fieldWidth);
    }

    public void setFieldHeight(int fieldHeight) {
        heightOfMap = fieldHeight;
        notifyObservers(SettingsEvent.FIELD_HEIGHT_CHANGED, fieldHeight);
    }


    public void setSizeOfHex(int radius) {
        this.sizeOfHex = radius;
        notifyObservers(SettingsEvent.RADIUS_CHANGED, radius);
    }

    public void setThickness(int thickness) {
        this.thicknessOfLine = thickness;
        notifyObservers(SettingsEvent.THICKNESS_CHANGED, thickness);
    }
}
