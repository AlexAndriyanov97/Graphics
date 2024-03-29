package ru.nsu.fit.g16208.andriyanov.graphics.controller;

import ru.nsu.fit.g16208.andriyanov.graphics.model.FieldOfDefinition;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Func;
import ru.nsu.fit.g16208.andriyanov.graphics.model.Settings;
import ru.nsu.fit.g16208.andriyanov.graphics.view.MainView;
import ru.nsu.fit.g16208.andriyanov.graphics.view.SettingsView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.util.Scanner;
import java.util.function.BiFunction;

public class MainController {
    private MainView mainView;
    private Func functionModel;
    private Func functionLegend;
    private final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

    public MainController(Func functionModel) {
        this.functionModel = functionModel;
        BiFunction<Double, Double, Double> functionForLegend = (x, y) -> x;
        this.functionLegend = new Func(functionForLegend, functionModel.GetFieldOfDefinition(), functionModel.GetSettings());
        this.mainView = new MainView(this, functionModel, functionLegend);
        this.mainView.setSize(800, 600);
        this.mainView.setVisible(true);
        setupFileChoosersDirs();
    }

    private void setupFileChoosersDirs(){
        final File dataDir = new File(System.getProperty("user.dir"),"FIT_16208_Andriyanov_Isolines_Data");
        if(dataDir.isDirectory()){
            fileChooser.setCurrentDirectory(dataDir);
        }
    }

    public void GridPressed() {
        mainView.ChangeGridState();
    }

    public void PaintPressed() {
        mainView.ChangePaintState();
    }

    public void DotsPressed() {
        mainView.ChangeDotsState();
    }

    public void IsolinePressed() {
        mainView.ChangeIsolineState();
    }

    public void OpenPressed() throws ParseException {
        try {
        if(fileChooser.showOpenDialog(mainView)==JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            functionModel.SetSettings(ReadFile(file));
            mainView.repaint();
        }}
        catch (ParseException e){
            throw new ParseException("Неверный формат файла",0);
        }
    }

    private static Settings ReadFile(File file) throws ParseException {
        try (Scanner scanner = new Scanner(file)) {
            String line = scanner.nextLine();
            deleteComments(line);
            Scanner lineScanner = new Scanner(line);
            int k = lineScanner.nextInt();
            int m = lineScanner.nextInt();

            line = scanner.nextLine();
            deleteComments(line);
            lineScanner = new Scanner(line);
            int n = lineScanner.nextInt();

            Color[] colors = new Color[n + 1];
            for (int i = 0; i < n + 1; i++) {
                line = scanner.nextLine();
                deleteComments(line);
                colors[i] = ReadColor(line);
            }

            line = scanner.nextLine();
            deleteComments(line);
            Color isolineColor = ReadColor(line);

            return new Settings(k, m, n, colors, isolineColor);

        } catch (Throwable e) {
            throw new ParseException("неверная форма файла",0);
        }
    }

    private static Color ReadColor(String line) {
        Scanner lineScanner = new Scanner(line);
        return new Color(lineScanner.nextInt(), lineScanner.nextInt(),lineScanner.nextInt());
    }

    private static String deleteComments(String str) {
        int pos;
        if ((pos = str.indexOf("//")) == -1) {
            return str;
        } else {
            return str.substring(0, pos);
        }
    }


    public void InterpolatePressed() {
        mainView.ChangeInterpolate();
    }

    public void SettingsPressed() {
        SettingsView settingsView = new SettingsView(functionModel.GetSettings().GetK(), functionModel.GetSettings().GetM(), functionModel.GetFieldOfDefinition());
        settingsView.setLocationRelativeTo(mainView);
        settingsView.setVisible(true);
        int k = settingsView.getK();
        int m = settingsView.getM();
        FieldOfDefinition fieldOfDefinition = settingsView.getFieldOfDefinition();
        functionModel.GetSettings().SetK(k);
        functionModel.GetSettings().SetM(m);
        functionModel.SetFieldOfDeFinition(fieldOfDefinition);
        mainView.repaint();
    }


}
