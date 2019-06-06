package ru.nsu.fit.g16208.andriyanov.graphics.Controller;

import ru.nsu.fit.g16208.andriyanov.graphics.model.Point3D;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Matrix;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Loader {
    private File sceneFile;
    private File cameraFile;

    public Loader(File sceneFile, File cameraFile) {
        this.sceneFile = sceneFile;
        this.cameraFile = cameraFile;

    }

    public Loader(File sceneFile) {
        this.sceneFile = sceneFile;
    }

    public SceneModel loadScene() throws IOException {
        SceneModel sceneModel = new SceneModel();
        Scene scene = new Scene(new CoordinateSystem(
                Matrix.E(), new Point3D(0, 0, 0)
        ));

        try (Scanner scanner = new Scanner(sceneFile, "UTF-8")) {
            String[] strData = nextData(scanner);
            int Ar = Integer.parseInt(strData[0]);
            int Ag = Integer.parseInt(strData[1]);
            int Ab = Integer.parseInt(strData[2]);

            sceneModel.setAmbientLight(RGB.RED, Ar);
            sceneModel.setAmbientLight(RGB.GREEN, Ag);
            sceneModel.setAmbientLight(RGB.BLUE, Ab);

            strData = nextData(scanner);
            int NL = Integer.parseInt(strData[0]);

            List<Light3D> lights = new ArrayList<>();
            for (int i = 0; i < NL; ++i) {
                strData = nextData(scanner);
                double x = Double.parseDouble(strData[0]);
                double y = Double.parseDouble(strData[1]);
                double z = Double.parseDouble(strData[2]);

                int r = Integer.parseInt(strData[3]);
                int g = Integer.parseInt(strData[4]);
                int b = Integer.parseInt(strData[5]);

                Light3D light = new Light3D(new Point3D(x, y, z), r, g, b);
                lights.add(light);
                scene.addChild(light);
            }
            sceneModel.setLights(lights);

            while ((strData = nextData(scanner)) != null) {
                String type = strData[0].toUpperCase();

                Drawable3D drawable3D = null;
                switch (type) {
                    case "SPHERE":
                        drawable3D = parseSphere(scanner);
                        break;

                    case "BOX":
                        drawable3D = parseBox(scanner);
                        break;

                    case "TRIANGLE":
                        drawable3D = parseTriangle(scanner);
                        break;

                    case "QUADRANGLE":
                        drawable3D = parseQuadrangle(scanner);
                        break;

                    default:
                        throw new IOException("Bad primitive");
                }

                scene.addChild(drawable3D);
            }


        } catch (Exception e) {
            throw new IOException("Bad file format", e);
        }
        sceneModel.setScene(scene);
        return sceneModel;
    }


    private Sphere parseSphere(Scanner scanner) {
        String[] strData = nextData(scanner);
        double x = Double.parseDouble(strData[0]);
        double y = Double.parseDouble(strData[1]);
        double z = Double.parseDouble(strData[2]);
        Point3D center = new Point3D(x, y, z);

        strData = nextData(scanner);
        double r = Double.parseDouble(strData[0]);

        Sphere sphere = new Sphere(center, r);

        strData = nextData(scanner);
        double rRatio = Double.parseDouble(strData[0]);
        double gRatio = Double.parseDouble(strData[1]);
        double bRatio = Double.parseDouble(strData[2]);

        sphere.setDiffuseCoeff(RGB.RED, rRatio);
        sphere.setDiffuseCoeff(RGB.GREEN, gRatio);
        sphere.setDiffuseCoeff(RGB.BLUE, bRatio);


        rRatio = Double.parseDouble(strData[3]);
        gRatio = Double.parseDouble(strData[4]);
        bRatio = Double.parseDouble(strData[5]);

        sphere.setMirroredCoef(RGB.RED, rRatio);
        sphere.setMirroredCoef(RGB.GREEN, gRatio);
        sphere.setMirroredCoef(RGB.BLUE, bRatio);

        double power = Double.parseDouble(strData[6]);
        sphere.setPower(power);

        return sphere;
    }

    private Box parseBox(Scanner scanner) {

        String[] strData = nextData(scanner);
        double x = Double.parseDouble(strData[0]);
        double y = Double.parseDouble(strData[1]);
        double z = Double.parseDouble(strData[2]);
        Point3D a = new Point3D(x, y, z);

        strData = nextData(scanner);
        x = Double.parseDouble(strData[0]);
        y = Double.parseDouble(strData[1]);
        z = Double.parseDouble(strData[2]);
        Point3D b = new Point3D(x, y, z);

        Box box = new Box(a, b);

        strData = nextData(scanner);
        double rRatio = Double.parseDouble(strData[0]);
        double gRatio = Double.parseDouble(strData[1]);
        double bRatio = Double.parseDouble(strData[2]);

        box.setDiffuseCoeff(RGB.RED, rRatio);
        box.setDiffuseCoeff(RGB.GREEN, gRatio);
        box.setDiffuseCoeff(RGB.BLUE, bRatio);

        rRatio = Double.parseDouble(strData[3]);
        gRatio = Double.parseDouble(strData[4]);
        bRatio = Double.parseDouble(strData[5]);

        box.setMirroredCoef(RGB.RED, rRatio);
        box.setMirroredCoef(RGB.GREEN, gRatio);
        box.setMirroredCoef(RGB.BLUE, bRatio);

        double power = Double.parseDouble(strData[6]);
        box.setPower(power);

        return box;
    }

    private Triangle parseTriangle(Scanner scanner) {

        String[] strData = nextData(scanner);
        double x = Double.parseDouble(strData[0]);
        double y = Double.parseDouble(strData[1]);
        double z = Double.parseDouble(strData[2]);
        Point3D a = new Point3D(x, y, z);

        strData = nextData(scanner);
        x = Double.parseDouble(strData[0]);
        y = Double.parseDouble(strData[1]);
        z = Double.parseDouble(strData[2]);
        Point3D b = new Point3D(x, y, z);

        strData = nextData(scanner);
        x = Double.parseDouble(strData[0]);
        y = Double.parseDouble(strData[1]);
        z = Double.parseDouble(strData[2]);
        Point3D c = new Point3D(x, y, z);

        Triangle triangle = new Triangle(a, b, c);

        strData = nextData(scanner);
        double rRatio = Double.parseDouble(strData[0]);
        double gRatio = Double.parseDouble(strData[1]);
        double bRatio = Double.parseDouble(strData[2]);

        triangle.setDiffuseCoeff(RGB.RED, rRatio);
        triangle.setDiffuseCoeff(RGB.GREEN, gRatio);
        triangle.setDiffuseCoeff(RGB.BLUE, bRatio);

        rRatio = Double.parseDouble(strData[3]);
        gRatio = Double.parseDouble(strData[4]);
        bRatio = Double.parseDouble(strData[5]);

        triangle.setMirroredCoef(RGB.RED, rRatio);
        triangle.setMirroredCoef(RGB.GREEN, gRatio);
        triangle.setMirroredCoef(RGB.BLUE, bRatio);

        double power = Double.parseDouble(strData[6]);
        triangle.setPower(power);

        return triangle;
    }

    private Quadrangle parseQuadrangle(Scanner scanner) {
        String[] strData = nextData(scanner);
        double x = Double.parseDouble(strData[0]);
        double y = Double.parseDouble(strData[1]);
        double z = Double.parseDouble(strData[2]);
        Point3D a = new Point3D(x, y, z);

        strData = nextData(scanner);
        x = Double.parseDouble(strData[0]);
        y = Double.parseDouble(strData[1]);
        z = Double.parseDouble(strData[2]);
        Point3D b = new Point3D(x, y, z);

        strData = nextData(scanner);
        x = Double.parseDouble(strData[0]);
        y = Double.parseDouble(strData[1]);
        z = Double.parseDouble(strData[2]);
        Point3D c = new Point3D(x, y, z);

        strData = nextData(scanner);
        x = Double.parseDouble(strData[0]);
        y = Double.parseDouble(strData[1]);
        z = Double.parseDouble(strData[2]);
        Point3D d = new Point3D(x, y, z);

        Quadrangle quadrangle = new Quadrangle(a, b, c, d);

        strData = nextData(scanner);
        double rRatio = Double.parseDouble(strData[0]);
        double gRatio = Double.parseDouble(strData[1]);
        double bRatio = Double.parseDouble(strData[2]);

        quadrangle.setDiffuseCoeff(RGB.RED, rRatio);
        quadrangle.setDiffuseCoeff(RGB.GREEN, gRatio);
        quadrangle.setDiffuseCoeff(RGB.BLUE, bRatio);

        rRatio = Double.parseDouble(strData[3]);
        gRatio = Double.parseDouble(strData[4]);
        bRatio = Double.parseDouble(strData[5]);


        quadrangle.setMirroredCoef(RGB.RED, rRatio);
        quadrangle.setMirroredCoef(RGB.GREEN, gRatio);
        quadrangle.setMirroredCoef(RGB.BLUE, bRatio);

        double power = Double.parseDouble(strData[6]);
        quadrangle.setPower(power);

        return quadrangle;
    }

    public RenderModel loadRenderSettings() throws FileNotFoundException {
        return loadRenderSettings(cameraFile);
    }

    public RenderModel loadRenderSettings(File settings) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(settings, "UTF-8")) {
            RenderModel renderModel = new RenderModel();
            String[] strData = nextData(scanner);
            int Br = Integer.parseInt(strData[0]);
            int Bg = Integer.parseInt(strData[1]);
            int Bb = Integer.parseInt(strData[2]);

            renderModel.setBackgroundColor(new Color(Br, Bg, Bb));

            strData = nextData(scanner);
            double gamma = Double.parseDouble(strData[0]);
            strData = nextData(scanner);
            int depth = Integer.parseInt(strData[0]);
            renderModel.setDepth(depth);

            strData = nextData(scanner);
            String quality = strData[0];
            strData = nextData(scanner);

            double eyeX = Double.parseDouble(strData[0]);
            double eyeY = Double.parseDouble(strData[1]);
            double eyeZ = Double.parseDouble(strData[2]);
            renderModel.setDirection(new Point3D(eyeX, eyeY, eyeZ));


            strData = nextData(scanner);
            double viewX = Double.parseDouble(strData[0]);
            double viewY = Double.parseDouble(strData[1]);
            double viewZ = Double.parseDouble(strData[2]);
            renderModel.setCenter(new Point3D(viewX, viewY, viewZ));


            strData = nextData(scanner);
            double upX = Double.parseDouble(strData[0]);
            double upY = Double.parseDouble(strData[1]);
            double upZ = Double.parseDouble(strData[2]);
            renderModel.setUp(new Point3D(upX, upY, upZ));

            strData = nextData(scanner);
            double zN = Double.parseDouble(strData[0]);
            double zF = Double.parseDouble(strData[1]);
            renderModel.setZn(zN);
            renderModel.setZf(zF);

            strData = nextData(scanner);
            double sW = Double.parseDouble(strData[0]);
            double sH = Double.parseDouble(strData[1]);
            renderModel.setSw(sW);
            renderModel.setSh(sH);
            return renderModel;

        }
    }

    private static String removeComment(String line) {
        int index = line.indexOf("//");
        if (index == -1) {
            return line;
        }

        return line.substring(0, index);
    }

    public void saveRenderSettings(RenderModel renderModel, File file) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append(renderModel.getBackgroundColor().getRed())
                    .append(" ")
                    .append(renderModel.getBackgroundColor().getGreen())
                    .append(" ")
                    .append(renderModel.getBackgroundColor().getBlue())
                    .append("\n");
            bufferedWriter.write(strBuilder.toString());

            //gamma
            bufferedWriter.write( "1.0"+"\n");
            //depth
            bufferedWriter.write("3" + "\n");
            //quality
            bufferedWriter.write("FINE"+"\n");


            bufferedWriter.write(renderModel.getDirection().getX()+" "+renderModel.getDirection().getY()+" "+renderModel.getDirection().getZ()+"\n");

            bufferedWriter.write(renderModel.getCenter().getX()+" "+renderModel.getCenter().getY()+" "+renderModel.getCenter().getZ()+"\n");

            bufferedWriter.write(renderModel.getUp().getX()+" "+renderModel.getUp().getY()+" "+renderModel.getUp().getZ()+"\n");

            bufferedWriter.write(renderModel.getZn()+" "+renderModel.getZf()+"\n");

            bufferedWriter.write(renderModel.getSw()+" "+renderModel.getSh());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] nextData(Scanner scanner) {
        String line = "";
        while (line.isEmpty()) {
            if (!scanner.hasNextLine()) {
                return null;
            }

            line = removeComment(scanner.nextLine()).trim();
        }

        return line.replaceAll("[ ]{2,}", " ").split(" ");
    }
}
