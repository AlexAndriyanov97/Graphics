package main.java.ru.fit.andriyanov.graphics.Model;

import main.java.ru.fit.andriyanov.graphics.Model.Settings.SettingsGame;

public class Model extends Observable<GameEvent> {
    public static final int SIDES = 6;

    private SettingsGame settingsModel;

    private boolean[] fieldState;

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
