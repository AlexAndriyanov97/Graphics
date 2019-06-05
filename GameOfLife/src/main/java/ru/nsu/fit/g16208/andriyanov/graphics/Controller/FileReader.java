package ru.nsu.fit.g16208.andriyanov.graphics.Controller;

import ru.nsu.fit.g16208.andriyanov.graphics.Model.Model;
import ru.nsu.fit.g16208.andriyanov.graphics.Model.Settings.SettingsGame;

import java.io.*;
import java.util.Scanner;

public class FileReader {
    public static void saveFile(SettingsGame settingsModel, Model gameModel, File file) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append(settingsModel.getWidthOfMap())
                    .append(" ")
                    .append(settingsModel.getHeightOfMap())
                    .append("\n");
            bufferedWriter.write(strBuilder.toString());

            bufferedWriter.write(settingsModel.getThicknessOfLine() + "\n");
            bufferedWriter.write(settingsModel.getSizeOfHex() + "\n");

            boolean[] fieldState = gameModel.getFieldState();
            int fieldHeight = gameModel.getFieldHeight();
            int fieldWidth = gameModel.getFieldWidth();
            StringBuilder fieldStateStr = new StringBuilder();
            int counter = 0;
            for (int j = 0; j < fieldHeight; j++) {
                boolean even = j % 2 == 0;
                for (int k = 0; k < (even ? fieldWidth : fieldWidth - 1); k++) {
                    if (fieldState[j * fieldWidth + k]) {
                        ++counter;
                        fieldStateStr
                                .append(k)
                                .append(" ")
                                .append(j)
                                .append("\n");
                    }
                }
            }

            bufferedWriter.write(counter + "\n");
            bufferedWriter.write(fieldStateStr.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SettingsGame loadFile(File file) throws IOException{
        try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(file))) {
            String firstLine = bufferedReader.readLine();
            if (firstLine == null) {
                throw new EOFException();
            }
            firstLine = deleteComments(firstLine);
            Scanner scanner = new Scanner(firstLine);
            int width = scanner.nextInt();
            int height = scanner.nextInt();

            String secondLine = bufferedReader.readLine();
            if (secondLine == null) {
                throw new EOFException();
            }
            secondLine = deleteComments(secondLine);
            scanner = new Scanner(secondLine);
            int thickness = scanner.nextInt();

            String thirdLine = bufferedReader.readLine();
            if (thirdLine == null) {
                throw new EOFException();
            }
            thirdLine = deleteComments(thirdLine);
            scanner = new Scanner(thirdLine);
            int radius = scanner.nextInt();

            String forthLine = bufferedReader.readLine();
            if (forthLine == null) {
                throw new EOFException();
            }
            forthLine = deleteComments(forthLine);
            scanner = new Scanner(forthLine);
            int all = scanner.nextInt();

            boolean[] fieldState = new boolean[width * height];

            for (int i = 0; i < all; i++) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    throw new EOFException();
                }
                line = deleteComments(line);
                scanner = new Scanner(line);
                int k = scanner.nextInt();
                int j = scanner.nextInt();
                fieldState[j * width + k] = true;
            }
            SettingsGame model = new SettingsGame(width, height, radius, thickness);
            model.setStartFieldState(fieldState);
            return model;

        } catch (Throwable t) {
            throw new IOException();
        }
    }


    private static String deleteComments(String str) {
        int pos;
        if ((pos = str.indexOf("//")) == -1) {
            return str;
        } else {
            return str.substring(0, pos);
        }
    }
}
