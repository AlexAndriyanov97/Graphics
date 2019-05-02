package main.java.ru.nsu.fit.andriyanov.graphics.view;

import main.java.ru.nsu.fit.andriyanov.graphics.model.FieldOfDefinition;
import main.java.ru.nsu.fit.andriyanov.graphics.model.Func;
import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class LegendView extends JPanel {
    private Func func;
    private BufferedImage image;
    private BufferedImage legendImage;
    private int widthOfLegend;
    private int heightOfLegend;
    private Point pointOfBegan;

    public LegendView(Func func) {
        this.func=func;
    }

    @Override
    protected void paintComponent(Graphics g) {
        UpdateImage();
        g.drawImage(image,0,0,image.getWidth(),image.getHeight(),this);
    }

    private void UpdateImage(){
        Dimension size = getSize();
        image = new BufferedImage(size.width,size.height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,image.getWidth(),image.getHeight());

        double percentOfFreeFillByX = 0.9;
        double percentOfFreeFillByY = 0.8;

        int x = (int) (image.getWidth()*(1-percentOfFreeFillByX)*0.5);
        int y = (int) (image.getHeight()*(1-percentOfFreeFillByY));
        pointOfBegan = new Point(x,y);

        widthOfLegend = (int) (image.getWidth()*percentOfFreeFillByX);
        heightOfLegend = (int) (image.getHeight()*percentOfFreeFillByY);

        legendImage = new BufferedImage(widthOfLegend,heightOfLegend,BufferedImage.TYPE_INT_RGB);
        Draw();
        image.getGraphics().drawImage(legendImage,x,y,widthOfLegend,heightOfLegend,this);
        DrawValueOfLegends();
    }


    private void DrawValueOfLegends(){
        double[] valueOfIsolines = func.GetAllValuesOfIsolines();
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLACK);
        int sizeOfStep = widthOfLegend/(valueOfIsolines.length+1);
        for (int i=0;i<valueOfIsolines.length;i++){
            String text = String.format("%.1f",valueOfIsolines[i]);
            int widthOfString = graphics.getFontMetrics().stringWidth(text);
            Point position = new Point(pointOfBegan.x+(i+1)*sizeOfStep, pointOfBegan.y-5);
            graphics.drawString(text,position.x-widthOfString/2,position.y);
        }

        String textOnLeftEdge = String.format("%.1f",func.GetMin());
        Point position  = new Point(pointOfBegan.x,pointOfBegan.y-5);
        int widthOfString = graphics.getFontMetrics().stringWidth(textOnLeftEdge);
        graphics.drawString(textOnLeftEdge,position.x-widthOfString/2,position.y);


        String textOnRightEdge = String.format("%.1f",func.GetMax());
        position  = new Point(pointOfBegan.x+(valueOfIsolines.length+1)*sizeOfStep,pointOfBegan.y-5);
        widthOfString = graphics.getFontMetrics().stringWidth(textOnRightEdge);
        graphics.drawString(textOnRightEdge,position.x-widthOfString/2,position.y);
    }

    private void Draw() {
        for (int i = 0; i < legendImage.getWidth(); i++) {
            for (int j = 0; j < legendImage.getHeight(); j++) {
                Point2D point = GetPointIntoField(new Point(i, j), func.GetFieldOfDefinition());
                //legendImage.setRGB(i,j, Color.WHITE.getRGB());
                legendImage.setRGB(i, j, func.GetColorByValue(CalculateValueForPoint(point)).getRGB());
            }
        }
    }
    private Point2D GetPointIntoField(Point point, FieldOfDefinition fieldOfDefinition) {
        double scaleX = (double) fieldOfDefinition.GetWidth() / (double) image.getWidth();
        double scaleY = (double) fieldOfDefinition.GetHeight() / (double) image.getHeight();

        return new Point2D.Double(point.x * scaleX + fieldOfDefinition.getA(), point.y * scaleY + fieldOfDefinition.getC());
    }

    private double CalculateValueForPoint(Point2D point) {
        return func.Calculate(point.getX(), point.getY());
    }


}
