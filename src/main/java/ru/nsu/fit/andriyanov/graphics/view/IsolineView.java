package main.java.ru.nsu.fit.andriyanov.graphics.view;

import main.java.ru.nsu.fit.andriyanov.graphics.model.FieldOfDefinition;
import main.java.ru.nsu.fit.andriyanov.graphics.model.IFuncModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class IsolineView extends JPanel {

    private IFuncModel model;
    private double scaleX;
    private double scaleY;

    private BufferedImage image;

    public IsolineView(IFuncModel funcModel) {
        this.model = funcModel;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point2D point = GetPointIntoField(e.getPoint(), funcModel.GetFieldOfDefinition());
                super.mouseMoved(e);
            }
        });
    }



    private Point2D GetPointIntoField(Point point, FieldOfDefinition fieldOfDefinition){
        double scaleX = (double) fieldOfDefinition.GetWidth()/(double) image.getWidth();
        double scaleY = (double) fieldOfDefinition.GetHeight()/(double) image.getHeight();

        return new Point2D.Double(point.x*scaleX+fieldOfDefinition.getA(),point.y*scaleY+fieldOfDefinition.getC());
    }
}
