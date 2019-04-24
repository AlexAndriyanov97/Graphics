package main.java.ru.nsu.fit.andriyanov.graphics.view;

import main.java.ru.nsu.fit.andriyanov.graphics.model.ChangedPoint;
import main.java.ru.nsu.fit.andriyanov.graphics.model.FieldOfDefinition;
import main.java.ru.nsu.fit.andriyanov.graphics.model.IFuncModel;
import main.java.ru.nsu.fit.andriyanov.graphics.model.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IsolineView extends JPanel {

    private IFuncModel model;
    private double scaleX;
    private double scaleY;

    private List<Consumer<ChangedPoint>> listChangedPoint = new ArrayList<>();

    private BufferedImage image;

    private Double valueOfDinamicIsoline;

    private boolean isInterpolate;
    private boolean isGridActivated;
    private boolean isPaintActivated;
    private boolean isDotsActivated;
    private boolean isIsolineActivated;

    private List<Double> customIsolines = new ArrayList<>();


    public IsolineView(IFuncModel funcModel) {
        this.model = funcModel;
        isInterpolate = false;
        isGridActivated = false;
        isPaintActivated = false;
        isDotsActivated = false;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = e.getPoint();
                valueOfDinamicIsoline = CalculateValueForPoint(GetPointIntoField(point, model.GetFieldOfDefinition()));
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point2D point = GetPointIntoField(e.getPoint(), funcModel.GetFieldOfDefinition());
                ChangedPoint changedPoint = new ChangedPoint(point.getX(), point.getY(), CalculateValueForPoint(point));
                for (Consumer<ChangedPoint> listener : listChangedPoint) {
                    listener.accept(changedPoint);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                valueOfDinamicIsoline = CalculateValueForPoint(GetPointIntoField(point, model.GetFieldOfDefinition()));
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isPaintActivated) {
                    customIsolines.add(valueOfDinamicIsoline);
                } else {
                    valueOfDinamicIsoline = null;
                    repaint();
                }
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        updateImage();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), this);
    }

    private void updateImage() {
        Dimension sizeImage = getSize();
        image = new BufferedImage(sizeImage.width, sizeImage.height, BufferedImage.TYPE_INT_RGB);
        Draw();

        FieldOfDefinition fieldOfDefinition = model.GetFieldOfDefinition();
        scaleX = (double) fieldOfDefinition.GetWidth() / (double) image.getWidth();
        scaleY = (double) fieldOfDefinition.GetHeight() / (double) image.getHeight();

        Settings settings = model.GetSettings();

        int k = settings.GetK();
        int m = settings.GetM();

        if (isIsolineActivated) {
            double[] valueOfIsolines = model.GetAllValuesOfIsolines();
            for (double valueOfIsoline : valueOfIsolines) {
                DrawIsoline(k, m, valueOfIsoline);
            }
            for (Double isoline : customIsolines) {
                DrawIsoline(k, m, isoline);
            }
            if (valueOfDinamicIsoline != null) {
                DrawIsoline(k, m, valueOfDinamicIsoline);
            }

        }
    }


    private void DrawIsoline(int k, int m, double valueOfIsoline) {
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < m; j++) {
                DrawIsolineIntoTheGrid(i, j, valueOfIsoline);
            }
        }
    }

    private void DrawIsolineIntoTheGrid(int i, int j, double valueOfIsoline) {
        int COUNT_ANGLE = 4;

        List<Point> crossingPoint = new ArrayList<>();
        Point[] angles = GetAngleOfRect(i, j);
        boolean[] valueOfAngle = new boolean[COUNT_ANGLE];

        for (int l = 0; l < COUNT_ANGLE; l++) {
            valueOfAngle[l] = Double.compare(CalculateValueForPoint(angles[l]), valueOfIsoline) > 0;
        }


        for (int l = 0; l < COUNT_ANGLE; l++) {
            if (valueOfAngle[l] != valueOfAngle[l + 1 % COUNT_ANGLE]) {
                crossingPoint.add(CalculatePointOfCrossing(angles[l], angles[l + 1 % COUNT_ANGLE], valueOfIsoline));
            }
        }
        Graphics graphics = image.getGraphics();

        if (crossingPoint.size() == 2) {
            graphics.drawLine(crossingPoint.get(0).x, crossingPoint.get(0).y, crossingPoint.get(1).x, crossingPoint.get(1).y);
        }

        CalculateThreeCrossingPoint(COUNT_ANGLE, crossingPoint, angles);
        CalculateFourCrossingPoint(valueOfIsoline, crossingPoint, angles, valueOfAngle, graphics);

        if (isDotsActivated) {
            for (Point point : crossingPoint) {
                DrawDot(graphics, point);
            }
        }

    }

    private void CalculateThreeCrossingPoint(int COUNT_ANGLE, List<Point> crossingPoint, Point[] angles) {
        if (crossingPoint.size() == 3) {
            for (int l = 0; l < 3; l++) {
                for (int k = 0; k < COUNT_ANGLE; k++) {
                    // Если точка угловая, заменяем её на две точки смещённые от этого угла..
                    if (crossingPoint.get(l).equals(angles[k])) {
                        int dx = 0;
                        int dy = 0;
                        switch (k){
                            case 0:
                                dx=1;
                                dy = 1;
                                break;
                            case 1:
                                dx=-1;
                                dy=1;
                                break;
                            case 2:
                                dx=-1;
                                dy=-1;
                                break;
                            case 3:
                                dx=1;
                                dy=-1;
                                break;
                        }
                        crossingPoint.remove(l);
                        Point firstPoint = new Point(angles[k].x+dx,angles[k].y);
                        crossingPoint.add(l,firstPoint);
                        Point secondPoint = new Point(angles[k].x, angles[k].y+dy);
                        crossingPoint.add(l,secondPoint);
                    }
                }
            }
        }
    }

    private void CalculateFourCrossingPoint(double valueOfIsoline, List<Point> crossingPoint, Point[] angles, boolean[] valueOfAngle, Graphics graphics) {
        if (crossingPoint.size() == 4) {
            Point centerOfRect = new Point((angles[0].x + angles[2].x) / 2, (angles[0].y + angles[2].y) / 2);
            boolean isMarkedCenter = Double.compare(CalculateValueForPoint(centerOfRect), valueOfIsoline) > 0;
            if (isMarkedCenter) {
                if (valueOfAngle[0]) {
                    graphics.drawLine(crossingPoint.get(0).x, crossingPoint.get(0).y, crossingPoint.get(1).x, crossingPoint.get(1).y);
                    graphics.drawLine(crossingPoint.get(2).x, crossingPoint.get(2).y, crossingPoint.get(3).x, crossingPoint.get(3).y);
                } else {
                    graphics.drawLine(crossingPoint.get(0).x, crossingPoint.get(0).y, crossingPoint.get(3).x, crossingPoint.get(3).y);
                    graphics.drawLine(crossingPoint.get(1).x, crossingPoint.get(1).y, crossingPoint.get(2).x, crossingPoint.get(2).y);
                }
            } else {
                if (valueOfAngle[0]) {
                    graphics.drawLine(crossingPoint.get(0).x, crossingPoint.get(0).y, crossingPoint.get(3).x, crossingPoint.get(3).y);
                    graphics.drawLine(crossingPoint.get(1).x, crossingPoint.get(1).y, crossingPoint.get(2).x, crossingPoint.get(2).y);
                } else {
                    graphics.drawLine(crossingPoint.get(0).x, crossingPoint.get(0).y, crossingPoint.get(1).x, crossingPoint.get(1).y);
                    graphics.drawLine(crossingPoint.get(2).x, crossingPoint.get(2).y, crossingPoint.get(3).x, crossingPoint.get(3).y);
                }

            }
        }
    }


    private Point CalculatePointOfCrossing(Point f1, Point f2, double valueIsoline) {
        double valueOfFirstPoint = CalculateValueForPoint(f1);
        double valueOfSecondPoint = CalculateValueForPoint(f2);

        if (f1.y != f2.y) {
            double dy = f2.y - f1.y;
            double x = 0;
            double y = dy * scaleY * (valueIsoline - valueOfFirstPoint) / (valueOfSecondPoint - valueOfFirstPoint);
            return new Point((int) (f1.x + x / scaleX), (int) (f1.y + y / scaleY));
        } else {
            double dx = f2.x - f1.x;
            double x = dx * scaleX * (valueIsoline - valueOfFirstPoint) / (valueOfSecondPoint - valueOfFirstPoint);
            double y = 0;
            return new Point((int) (f1.x + x / scaleX), (int) (f1.y + y / scaleY));
        }
    }

    private void DrawGrid(int k, int m) {
        double widthGrid = (double) image.getWidth() / k;
        double heightGrid = (double) image.getHeight() / m;
        Graphics graphics = image.getGraphics();
        graphics.setXORMode(Color.RED);
        for (int i = 0; i < k - 1; i++) {
            int x = (int) widthGrid * (i + 1);
            graphics.drawLine(x, 0, x, image.getHeight());
        }
        for (int i = 0; i < m - 1; i++) {
            int y = (int) heightGrid * (i + 1);
            graphics.drawLine(0, y, image.getWidth(), y);
        }
    }


    private Point[] GetAngleOfRect(int i, int j) {
        Settings settings = model.GetSettings();
        int k = settings.GetK();
        int m = settings.GetM();
        double widthGrid = (double) image.getWidth() / k;
        double heightGrid = (double) image.getHeight() / m;
        Point[] angles = new Point[4];
        angles[0] = new Point((int) (i * widthGrid), (int) (j * heightGrid));
        angles[1] = new Point((int) ((i + 1) * widthGrid), (int) (j * heightGrid));
        angles[2] = new Point((int) ((i + 1) * widthGrid), (int) ((j + 1) * heightGrid));
        angles[3] = new Point((int) (i * widthGrid), (int) ((j + 1) * heightGrid));
        return angles;
    }

    private void DrawDot(Graphics graphics, Point centr) {
        // Захардкодим радиус точки
        int radius = 3;
        graphics.fillOval(centr.x - radius, centr.y - radius, 2 * radius, 2 * radius);
    }

    private void Draw() {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Point2D point = GetPointIntoField(new Point(i, j), model.GetFieldOfDefinition());
                double value = CalculateValueForPoint(point);
                int rgb = model.GetColorByValue(CalculateValueForPoint(point), isInterpolate).getRGB();
                image.setRGB(i, j, model.GetColorByValue(CalculateValueForPoint(point), isInterpolate).getRGB());
            }
        }
    }


    private double CalculateValueForPoint(Point2D point) {
        return model.Calculate(point.getX(), point.getY());
    }


    public void SetFunctionOfListenerChangedPoint(Consumer<ChangedPoint> listener) {
        listChangedPoint.add(listener);
    }

    private Point2D GetPointIntoField(Point point, FieldOfDefinition fieldOfDefinition) {
        double scaleX = (double) fieldOfDefinition.GetWidth() / (double) image.getWidth();
        double scaleY = (double) fieldOfDefinition.GetHeight() / (double) image.getHeight();

        return new Point2D.Double(point.x * scaleX + fieldOfDefinition.getA(), point.y * scaleY + fieldOfDefinition.getC());
    }

    public void ChangeGridActivated() {
        isGridActivated = !isGridActivated;
        repaint();
    }

    public void ChangeInterpolate(){
        isInterpolate = !isInterpolate;
        repaint();
    }

    public void ChangePaintActivated() {
        isPaintActivated = !isGridActivated;
        repaint();
    }

    public void ChangeDotsActivated() {
        isDotsActivated = !isGridActivated;
        repaint();
    }

    public void ChangeIsolineActivated() {
        isIsolineActivated = !isIsolineActivated;
        repaint();
    }
}
