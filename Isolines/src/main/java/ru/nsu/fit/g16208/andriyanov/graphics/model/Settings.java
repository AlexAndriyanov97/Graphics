package ru.nsu.fit.g16208.andriyanov.graphics.model;

import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class Settings {

    private int k;

    private int m;

    private int n;

    private Color[] colorsOfLegend;

    private Color colorOfIsoline;

    public Settings(int k, int m, int n, Color[] colorsOfLegend, Color colorOfIsoline) {
        this.k = k;
        this.m = m;
        this.n = n;
        this.colorsOfLegend = colorsOfLegend;
        this.colorOfIsoline = colorOfIsoline;
    }

    public Settings(File settingsFile) {
        try (Scanner scanner = new Scanner(settingsFile)) {
            String line = scanner.nextLine();
            line = CheckLine(line);
            Scanner firstLineScanner = new Scanner(line);
            this.k = firstLineScanner.nextInt();
            this.m = firstLineScanner.nextInt();

            line = scanner.nextLine();
            line = CheckLine(line);
            Scanner secondLineScanner = new Scanner(line);
            this.n = secondLineScanner.nextInt();
            colorsOfLegend = new Color[this.n+1];

            for (int i= 0;i<=n;i++){
                line = scanner.nextLine();
                line = CheckLine(line);
                colorsOfLegend[i]= ParseColor(line);
            }
            line = scanner.nextLine();
            line = CheckLine(line);
            this.colorOfIsoline = ParseColor(line);


        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка в парсинге данных");
        }
    }


    private Color ParseColor(String lineColor){
        Scanner scanner = new Scanner(lineColor);
        int red = scanner.nextInt();
        int green = scanner.nextInt();
        int blue = scanner.nextInt();

        return new Color(red,green,blue);
    }

    private String CheckLine(String sourceLine) {
        if (sourceLine == null) {
            throw new NullPointerException();
        }
        int positionOfBeginComment = sourceLine.indexOf('#');
        if (positionOfBeginComment == -1) {
            return sourceLine;
        } else {
            return sourceLine.substring(0, positionOfBeginComment);
        }
    }

    public int GetK() {
        return k;
    }

    public int GetM() {
        return m;
    }

    public int GetN() {
        return n;
    }

    public Color[] GetColorsOfLegend() {
        return colorsOfLegend;
    }

    public Color GetColorOfIsoline() {
        return colorOfIsoline;
    }

    public void SetK(int k) {
        this.k = k;
    }

    public void SetM(int m) {
        this.m = m;
    }
}
