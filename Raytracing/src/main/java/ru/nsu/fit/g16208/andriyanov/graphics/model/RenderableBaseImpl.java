package ru.nsu.fit.g16208.andriyanov.graphics.model;

abstract public class RenderableBaseImpl extends Shape implements Renderable {
    private double kdR = 0.8;
    private double kdG = 0.8;
    private double kdB = 0.8;

    private double ksR = 0.8;
    private double ksG = 0.8;
    private double ksB = 0.8;

    private double power = 2;

    @Override
    public double getDiffuseCoeff(RGB color) {
        switch (color) {
            case RED:
                return kdR;
            case GREEN:
                return kdG;
            case BLUE:
                return kdB;
            default:
                return 0;
        }
    }

    @Override
    public double getMirroredCoeff(RGB color) {
        switch (color) {
            case RED:
                return ksR;
            case GREEN:
                return ksG;
            case BLUE:
                return ksB;
            default:
                return 0;
        }
    }

    public void setDiffuseCoeff(RGB color, double value) {
        switch (color) {
            case RED:
                kdR = value;
                break;
            case GREEN:
                kdG = value;
                break;
            case BLUE:
                kdB = value;
                break;
        }
    }

    public void setMirroredCoef(RGB color, double value) {
        switch (color) {
            case RED:
                ksR = value;
                break;
            case GREEN:
                ksG = value;
                break;
            case BLUE:
                ksB = value;
                break;
        }
    }


    @Override
    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

}

