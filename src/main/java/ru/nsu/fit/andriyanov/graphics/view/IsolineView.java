package main.java.ru.nsu.fit.andriyanov.graphics.view;

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
        isPaintActivated=false;
        isDotsActivated=false;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = e.getPoint();
                valueOfDinamicIsoline = CalculateValueForPoint(GetPointIntoField(point,model.GetFieldOfDefinition()));
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
                Point point =e.getPoint();
                valueOfDinamicIsoline = CalculateValueForPoint(GetPointIntoField(point,model.GetFieldOfDefinition()));
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(isPaintActivated){
                   customIsolines.add(valueOfDinamicIsoline);
                }
                else {
                    valueOfDinamicIsoline = null;
                    repaint();
                }
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        updateImage();
        g.drawImage(image,0,0,image.getWidth(),image.getHeight(),this);
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

        if(isIsolineActivated){
            double[] valueOfIsolines = model.GetAllValuesOfIsolines();
            for (double valueOfIsoline : valueOfIsolines) {
                DrawIsoline(k, m, valueOfIsoline);
            }
            for(Double isoline : customIsolines){
                DrawIsoline(k,m,isoline);
            }
            if(valueOfDinamicIsoline!=null){
                DrawIsoline(k,m,valueOfDinamicIsoline);
            }

        }
    }



    private void DrawIsoline(int k,int m,double valueOfIsoline){
        for (int i=0;i<k;i++){
            for (int j = 0;j<m;j++){

            }
        }
    }

    private void DrawIsolineIntoTheGrid(int i, int j, double valueOfIsoline){
        Point[] angles = GetAngleOfRect(i,j);
    }

    private void DrawGrid(int k,int m){
        double widthGrid = (double) image.getWidth()/k;
        double heightGrid = (double) image.getHeight()/m;
        Graphics graphics = image.getGraphics();
        graphics.setXORMode(Color.RED);
        for (int i=0;i<k-1;i++){
            int x = (int) widthGrid*(i+1);
            graphics.drawLine(x,0,x,image.getHeight());
        }
        for (int i=0;i<m-1;i++){
            int y = (int) heightGrid*(i+1);
            graphics.drawLine(0,y,image.getWidth(),y);
        }
    }


    private Point[] GetAngleOfRect(int i,int j){
        Settings settings = model.GetSettings();
        int k = settings.GetK();
        int m = settings.GetM();
        double widthGrid = (double) image.getWidth()/k;
        double heightGrid = (double) image.getHeight()/m;
        Point[] angles = new Point[4];
        angles[0] = new Point((int)(i*widthGrid),(int)(j*heightGrid));
        angles[1] = new Point((int)((i+1)*widthGrid),(int)(j*heightGrid));
        angles[2] = new Point((int)((i+1)*widthGrid),(int)((j+1)*heightGrid));
        angles[3] = new Point((int)(i*widthGrid),(int)((j+1)*heightGrid));
        return angles;
    }

    private void DrawDot(Graphics graphics, Point centr){
        // Захардкодим радиус точки
        int radius = 3;
        graphics.fillOval(centr.x-radius,centr.y-radius,2*radius,2*radius);
    }

    private void Draw() {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Point2D point = GetPointIntoField(new Point(i, j), model.GetFieldOfDefinition());
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
}
