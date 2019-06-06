package ru.nsu.fit.g16208.andriyanov.graphics.model;

import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Camera;
import ru.nsu.fit.g16208.andriyanov.graphics.wireframe.Scene;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RayTracer {
    private Camera camera;
    private SceneModel sceneModel;

    private int width = 800;
    private int height = 800;

    private Color BG_COLOR = Color.BLACK;

    private double delta = 0.00001;

    private class Intersection {
        public Point3D point;
        public Renderable renderable;
        public double t;

        public Intersection(Point3D point, Renderable renderable, double t) {
            this.point = point;
            this.renderable = renderable;
            this.t = t;
        }
    }

    private class IntensityRGB {
        public double red;
        public double green;
        public double blue;

        public IntensityRGB(double red, double green, double blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public double getChannel(RGB channel) {
            switch (channel) {
                case RED: return red;
                case GREEN: return green;
                case BLUE: return blue;
                default: return 0;
            }
        }
    }

    public RayTracer(Camera camera, SceneModel sceneModel) {
        this.camera = camera;
        this.sceneModel = sceneModel;
    }

    public BufferedImage renderScene(Scene scene, int depth) {
        java.util.List<Drawable3D> sceneObjects = scene.getChildList();
        java.util.List<Drawable3D> cameraObjects = new ArrayList<>();

        java.util.List<Light3D> newLights = new ArrayList<>();
        for (Drawable3D d : sceneObjects) {
            Drawable3D newDrawable = camera.toCameraCoordinateSystem(d, scene.getCoordinateSystemMatrix());
            cameraObjects.add(newDrawable);
            if (newDrawable instanceof Light3D) {
                newLights.add((Light3D)newDrawable);
            }
//            if (d instanceof Quadrangle) {
//                System.out.println(((Quadrangle)d).getDiffuseCoeff(RGB.RED));
//            }
        }
        sceneObjects = cameraObjects;
        sceneModel.setLights(newLights);

        double sw = camera.getRenderModel().getSw();
        double sh = camera.getRenderModel().getSh();
        double zn = -camera.getRenderModel().getZn();


        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        IntensityRGB[] intensityArray = new IntensityRGB[width * height];

        double pW = sw / width;
        double pH = sh / height;


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point3D projPoint = new Point3D((x - (width / 2)) * sw / width + pW / 2, -(y - (height / 2)) * sh / height + pH / 2, zn);
                Ray ray = new Ray(new Point3D(0, 0, 0), projPoint);
                IntensityRGB intensity = countIntensity(ray, sceneObjects, depth);


                intensity.red = Math.max(0, Math.min(255, intensity.red));
                intensity.green = Math.max(0, Math.min(255, intensity.green));
                intensity.blue = Math.max(0, Math.min(255, intensity.blue));

                image.setRGB(x, y, new Color((int)intensity.red, (int)intensity.green, (int)intensity.blue).getRGB());
            }
        }
        return image;
    }

    private IntensityRGB countIntensity(Ray ray, java.util.List<Drawable3D> sceneObjects, int depth) {
        if (depth == 0) {
            return new IntensityRGB(0,0,0);
        }

        Intersection inter = findIntersection(ray, sceneObjects);
        if (inter == null) {
            return new IntensityRGB(0,0,0);
        }
        Ray newRay = calcReflectedRay(ray, inter.renderable.getNormal(inter.point), inter.point);

        IntensityRGB restIntensity = countIntensity(newRay, sceneObjects, depth - 1);

        double intensityR = sceneModel.getAmbientLight(RGB.RED) * inter.renderable.getDiffuseCoeff(RGB.RED) + restIntensity.red;
        double intensityG = sceneModel.getAmbientLight(RGB.GREEN) * inter.renderable.getDiffuseCoeff(RGB.GREEN) + restIntensity.green;
        double intensityB = sceneModel.getAmbientLight(RGB.BLUE) * inter.renderable.getDiffuseCoeff(RGB.BLUE) + restIntensity.blue;

        java.util.List<Light3D> lights = sceneModel.getLights();
        for (Light3D light3D : lights) {
            Double t = isLightSeen(inter, light3D, sceneObjects);
            if (t == null) {
                continue;
            }
            double f = 1. / (1. + t);
            double lIntensityR = light3D.getIntensity(RGB.RED);
            double lIntensityG = light3D.getIntensity(RGB.GREEN);
            double lIntensityB = light3D.getIntensity(RGB.BLUE);

            Point3D normal = inter.renderable.getNormal(inter.point);
            Point3D lightDirection = sum(
                    light3D.getPosition(),
                    scale(inter.point, -1)
            );
            lightDirection = scale(lightDirection, 1 / lightDirection.norm());

            Point3D eye = sum(
                    ray.getStartPoint(),
                    scale(inter.point, -1)
            );
            Point3D h = sum(lightDirection, eye);
            h = scale(h, 1 / h.norm());

            double kdR = inter.renderable.getDiffuseCoeff(RGB.RED);
            double kdG = inter.renderable.getDiffuseCoeff(RGB.GREEN);
            double kdB = inter.renderable.getDiffuseCoeff(RGB.BLUE);

            double ksR = inter.renderable.getMirroredCoeff(RGB.RED);
            double ksG = inter.renderable.getMirroredCoeff(RGB.GREEN);
            double ksB = inter.renderable.getMirroredCoeff(RGB.BLUE);

            double tmpR = kdR * dotProduct(normal, lightDirection) + Math.pow(ksR * dotProduct(normal, h), inter.renderable.getPower());
            double tmpG = kdG * dotProduct(normal, lightDirection) + Math.pow(ksG * dotProduct(normal, h), inter.renderable.getPower());
            double tmpB = kdB * dotProduct(normal, lightDirection) + Math.pow(ksB * dotProduct(normal, h), inter.renderable.getPower());

            intensityR += f * lIntensityR * tmpR;
            intensityG += f * lIntensityG * tmpG;
            intensityB += f * lIntensityB * tmpB;
        }
        return new IntensityRGB(intensityR, intensityG, intensityB);
    }

    private Ray calcReflectedRay(Ray ray, Point3D normal, Point3D point) {
//        Point3D L = scale(ray.getDirection(), -1);
        Point3D L = sum(
                ray.getStartPoint(),
                scale(point, -1)
        );
        L = scale(L, 1 / L.norm());
        double tmpScalar = 2 * dotProduct(normal, L);
        Point3D R = sum(
                scale(normal, tmpScalar),
                scale(L, -1)
        );
        R = scale(R, 1 / R.norm());
        return new Ray(sum(point, scale(normal, delta)), R);
    }

    private Double isLightSeen(Intersection inter, Light3D light3D, java.util.List<Drawable3D> sceneObjects) {
        Point3D point = inter.point;
        Point3D direction = sum(
                light3D.getPosition(),
                scale(point, -1)
        );
        direction = scale(direction, 1 / direction.norm());

        Ray ray = new Ray(sum(point, scale(inter.renderable.getNormal(point), delta)), direction);

        Intersection intersection = findIntersection(ray, sceneObjects);
        double distance = countDistance(light3D.getPosition(), point);
        if ((intersection != null) && (intersection.t < distance) && (intersection.t > 0)) {
            return null;
        }
        return distance;
    }

    private Intersection findIntersection(Ray ray, java.util.List<Drawable3D> sceneObjects) {
        Double nearest = null;
        Renderable intersectObject = null;
        for (Drawable3D child : sceneObjects) {
            if (!(child instanceof Renderable)) {
                continue;
            }

            Renderable renderable = (Renderable) child;
            List<Double> intersections = ray.findIntersection(renderable);

            if (intersections.isEmpty()) {
                continue;
            }

            if (nearest == null) {
                if (intersections.get(0) >= 0) {
                    nearest = intersections.get(0);
                    intersectObject = (Renderable) child;
                }
                continue;
            }

            if (intersections.get(0) < nearest && (intersections.get(0) >= 0)) {
                nearest = intersections.get(0);
                intersectObject = (Renderable) child;
            }
        }

        if (nearest == null) {
            return null;
        }

        return new Intersection(ray.get(nearest), intersectObject, nearest);
    }

    public static Point3D scale(Point3D p, double k) {
        return new Point3D(p.getX() * k, p.getY() * k, p.getZ() * k);
    }

    public static double dotProduct(Point3D p1, Point3D p2) {
        return p1.getX() * p2.getX() + p1.getY() * p2.getY() + p1.getZ() * p2.getZ();
    }


    public static Point3D sum(Point3D p1, Point3D p2) {
        return new Point3D(
                p1.getX() + p2.getX(),
                p1.getY() + p2.getY(),
                p1.getZ() + p2.getZ()
        );
    }

    public static double countDistance(Point3D p1, Point3D p2) {
        return Math.sqrt(
                Math.pow(Math.abs(p1.getX() - p2.getX()), 2) +
                        Math.pow(Math.abs(p1.getY() - p2.getY()), 2) +
                        Math.pow(Math.abs(p1.getZ() - p2.getZ()), 2)
        );
    }

    private double findMax(IntensityRGB[] array, RGB channel) {
        return Arrays.stream(array).mapToDouble(x -> x.getChannel(channel)).max().getAsDouble();
    }

    private double findMin(IntensityRGB[] array, RGB channel) {
        return Arrays.stream(array).mapToDouble(x -> x.getChannel(channel)).min().getAsDouble();
    }
}

