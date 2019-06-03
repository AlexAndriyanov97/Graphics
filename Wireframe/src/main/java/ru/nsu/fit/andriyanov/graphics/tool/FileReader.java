package main.java.ru.nsu.fit.andriyanov.graphics.tool;

import main.java.ru.nsu.fit.andriyanov.graphics.controller.FigureController;
import main.java.ru.nsu.fit.andriyanov.graphics.model.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class FileReader {

    private static SplineManager splineOwner = SplineManager.getInstance();
    private static Camera camera = Camera.getInstance();

    private static String nextLine(Scanner scanner) {
        String line = "";
        while (line.isEmpty())
            line = scanner.nextLine().replaceAll("//.*", "");

        return line;
    }

    public static void loadFile(InputStream stream, Map<Spline, SplineFigure3D> figures) {
        Scanner scanner = new Scanner(stream);

        loadBasicParamsOfBodyRotation(scanner);

        loadParamsOfPyramidVisible(scanner);

        double[][] tmp = new double[3][];
        loadParamsOfMatrixRotation(scanner, tmp);

        loadBackgroudColor(scanner);

        int K = Integer.parseInt(nextLine(scanner));
        for (int i = 0; i < K; i++) {
            Spline spline = Spline.getEmptySpline();

            loadColorOfBody(scanner, spline);

            double[] cXYZ = loadCenter(scanner);

            Matrix rotation = loadMatrixRotationForCenter(scanner, tmp);

            loadDots(scanner, spline);

            splineOwner.addSpline(spline);

            Figure3D figure = figures.get(spline);
            figure.shift(new Vector(cXYZ[0], cXYZ[1], cXYZ[2]));
            figure.rotate(rotation);
        }

        FigureController.getInstance().setFigure(null);
        splineOwner.notifyObservers();
    }

    private static void loadDots(Scanner scanner, Spline spline) {
        int N = Integer.parseInt(nextLine(scanner));
        for (int j = 0; j < N; j++) {
            double[] value = Stream.of(nextLine(scanner))
                    .flatMap(s -> Arrays.stream(s.split(" ")))
                    .flatMap(s -> Arrays.stream(s.split("\t")))
                    .filter(s -> !s.isEmpty())
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            spline.addPoint(new Point2D.Double(value[0], value[1]));
        }
    }

    private static Matrix loadMatrixRotationForCenter(Scanner scanner, double[][] tmp) {
        tmp[0] = Stream.of(nextLine(scanner))
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .flatMap(s -> Arrays.stream(s.split("\t")))
                .filter(s -> !s.isEmpty())
                .mapToDouble(Double::parseDouble)
                .toArray();
        tmp[1] = Stream.of(nextLine(scanner))
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .flatMap(s -> Arrays.stream(s.split("\t")))
                .filter(s -> !s.isEmpty())
                .mapToDouble(Double::parseDouble)
                .toArray();
        tmp[2] = Stream.of(nextLine(scanner))
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .flatMap(s -> Arrays.stream(s.split("\t")))
                .filter(s -> !s.isEmpty())
                .mapToDouble(Double::parseDouble)
                .toArray();
        return new Matrix(new double[][]{
                {tmp[0][0], tmp[0][1], tmp[0][2], 0},
                {tmp[1][0], tmp[1][1], tmp[1][2], 0},
                {tmp[2][0], tmp[2][1], tmp[2][2], 0},
                {0, 0, 0, 1}
        });
    }

    private static double[] loadCenter(Scanner scanner) {
        return Stream.of(nextLine(scanner))
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .flatMap(s -> Arrays.stream(s.split("\t")))
                .filter(s -> !s.isEmpty())
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    private static void loadColorOfBody(Scanner scanner, Spline spline) {
        int[] RGB = getRGB(scanner);
        spline.setColor(new Color(RGB[0], RGB[1], RGB[2]));
    }


    private static int[] getRGB(Scanner scanner) {
        return Stream.of(nextLine(scanner))
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .flatMap(s -> Arrays.stream(s.split("\t")))
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static void loadBackgroudColor(Scanner scanner) {
        int[] backRGB = getRGB(scanner);
        camera.setColor(new Color(backRGB[0], backRGB[1], backRGB[2]));

        splineOwner.clear();
    }

    private static void loadParamsOfMatrixRotation(Scanner scanner, double[][] tmp) {
        Matrix cameraRotation = loadMatrixRotationForCenter(scanner, tmp);
        camera.setRotation(cameraRotation);
    }

    private static void loadParamsOfPyramidVisible(Scanner scanner) {
        double[] values = Stream.of(nextLine(scanner))
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .flatMap(s -> Arrays.stream(s.split("\t")))
                .filter(s -> !s.isEmpty())
                .mapToDouble(Double::parseDouble)
                .toArray();
        camera.setFrontZ(values[0]);
        camera.setBackZ(values[1]);
        camera.setWidth(values[2]);
        camera.setHeight(values[3]);
    }

    private static void loadBasicParamsOfBodyRotation(Scanner scanner) {
        double[] values = Stream.of(nextLine(scanner))
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .flatMap(s -> Arrays.stream(s.split("\t")))
                .filter(s -> !s.isEmpty())
                .mapToDouble(Double::parseDouble)
                .toArray();
        splineOwner.setLengthCount((int) values[0]);
        splineOwner.setRotateCount((int) values[1]);
        splineOwner.setLengthK((int) values[2]);
        splineOwner.setLengthFrom(values[3]);
        splineOwner.setLengthTo(values[4]);
        splineOwner.setRotateFrom(values[5]);
        splineOwner.setRotateTo(values[6]);
    }


    public static void saveFile(OutputStream stream, Map<Spline, SplineFigure3D> figures) {
        PrintStream printer = new PrintStream(stream);

        saveBasicParamsOfBodyRotation(printer);

        saveParamsOfPyramidVisible(printer);

        saveParamsOfMatrixRotation(printer, camera.getRotation());

        saveColor(printer, camera.getColor());

        int K = figures.size();
        printer.println(K);

        for (Map.Entry<Spline, SplineFigure3D> entry : figures.entrySet()) {
            Spline spline = entry.getKey();
            Figure3D figure = entry.getValue();

            saveColor(printer, spline.getColor());

            saveCooridateOfCenter(printer, figure.getCenter().getX(), figure.getCenter().getY(), figure.getCenter().getZ());

            saveParamsOfMatrixRotation(printer, figure.getRotation());

            saveDots(printer, spline);
        }
    }

    private static void saveDots(PrintStream printer, Spline spline) {
        int N = spline.getPoints().size();
        printer.println(N);

        for (Point2D point2D : spline.getPoints()) {
            printer.print(point2D.getX()+" ");
            printer.println(point2D.getY());
        }
    }

    private static void saveCooridateOfCenter(PrintStream printer, double x, double y, double z) {
        printer.print(x + " ");
        printer.print(y + " ");
        printer.println(z);
    }

    private static void saveColor(PrintStream printer, Color color) {
        printer.print(color.getRed() + " ");
        printer.print(color.getGreen() + " ");
        printer.println(color.getBlue());
    }

    private static void saveParamsOfMatrixRotation(PrintStream printer, Matrix rotation2) {
        double[][] cameraRotation = rotation2.getValues();
        printer.print(cameraRotation[0][0] + " ");
        printer.print(cameraRotation[0][1] + " ");
        printer.println(cameraRotation[0][2]);
        printer.print(cameraRotation[1][0] + " ");
        printer.print(cameraRotation[1][1] + " ");
        printer.println(cameraRotation[1][2]);
        printer.print(cameraRotation[2][0] + " ");
        printer.print(cameraRotation[2][1] + " ");
        printer.println(cameraRotation[2][2]);
    }


    private static void saveParamsOfPyramidVisible(PrintStream printer) {
        printer.print(camera.getFrontZ()+" ");
        printer.print(camera.getBackZ()+" ");
        printer.print(camera.getWidth()+" ");
        printer.println(camera.getHeight());
    }

    private static void saveBasicParamsOfBodyRotation(PrintStream printer) {
        printer.print(splineOwner.getLengthCount() + " ");
        printer.print(splineOwner.getRotateCount() + " ");

        printer.print(splineOwner.getLengthK() + " ");

        printer.print(splineOwner.getLengthFrom() + " ");

        printer.print(splineOwner.getLengthTo() + " ");

        printer.print(splineOwner.getRotateFrom() + " ");

        printer.println(splineOwner.getRotateTo() + " ");
    }
}
