package main.java.ru.fit.andriyanov.graphics.View;

import main.java.ru.fit.andriyanov.graphics.Controller.GameController;
import main.java.ru.fit.andriyanov.graphics.Model.GameEvent;
import main.java.ru.fit.andriyanov.graphics.Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class HexField extends JPanel {
    private int fieldWidth;
    private int fieldHeight;
    private int thickness;
    private int rad;

    private int halfHexWidth;
    private int halfRad;
    private Point[] leftTopHex;

    private int deltaX;
    private int deltaY;

    private boolean[] fieldState;

    private Model gameModel;

    private BufferedImage bufferedImage;

    private Color bgColor = Color.WHITE;
    private Color borderColor = Color.BLACK;
    private Color fillColor = Color.GREEN;
    private Color textColor = Color.BLACK;

    private int imageWidth;
    private int imageHeight;

    private boolean showImpacts = false;


    public HexField(Model gameModel, GameController gameController) {
        this.gameModel = gameModel;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                Point click = mouseEvent.getPoint();
                gameController.mouseClicked(click);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                super.mouseDragged(mouseEvent);
                gameController.mouseDragged(mouseEvent.getPoint());
            }
        });



        fieldState = gameModel.getFieldState();
        gameModel.registerObserver(this::onStateChanged, GameEvent.STATE_CHANGED);
        gameModel.registerObserver(this::onFieldWidthChanged, GameEvent.FIELD_WIDTH_CHANGED);
        gameModel.registerObserver(this::onFieldHeightChanged, GameEvent.FIELD_HEIGHT_CHANGED);
        gameModel.registerObserver(this::onRadiusChanged, GameEvent.RADIUS_CHANGED);
        gameModel.registerObserver(this::onThicknessChanged, GameEvent.THICKNESS_CHANGED);
        gameModel.registerObserver(this::onSettingsModelUpdated, GameEvent.SETTINGS_MODEL_UPDATED);

        updateField();
    }

    private void updateField() {
        this.fieldWidth = gameModel.getFieldWidth();
        this.fieldHeight = gameModel.getFieldHeight();
        this.thickness = gameModel.getThickness();
        this.rad = gameModel.getRadius();

        halfHexWidth = (int) (Math.sqrt(3) * rad / 2);
        halfRad = rad / 2;
        leftTopHex = countLeftTopHex();

        deltaX = halfHexWidth * 2;
        deltaY = halfRad * 3;

        imageWidth = fieldWidth * halfHexWidth * 2 + 100;
        imageHeight = fieldHeight * halfRad * 3 + 100;

        bufferedImage = paintHexField();
    }


    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ((Graphics2D)bufferedImage.getGraphics()).setBackground(Color.WHITE);

        graphics.drawImage(bufferedImage, 0, 0, this);

    }

    private BufferedImage paintHexField() {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(bgColor);
        g.fillRect(0,0, imageWidth, imageHeight);

        for (int j = 0; j < fieldHeight; j++) {
            boolean even = j % 2 == 0;
            for (int k = 0; k < (even ? fieldWidth : fieldWidth - 1); k++) {
                paintHex(image, j, k, fieldState[j * fieldWidth + k]);
            }
        }
        repaint();
        return image;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(imageWidth, imageHeight);
    }


    private Point[] countLeftTopHex() {
        Point[] corners = new Point[Model.SIDES];
        corners[0] = new Point(halfHexWidth, 0);
        corners[1] = new Point(0, halfRad);
        corners[2] = new Point(0, halfRad * 3);
        corners[3] = new Point(halfHexWidth, halfRad * 4);
        corners[4] = new Point(halfHexWidth * 2, halfRad * 3);
        corners[5] = new Point(halfHexWidth * 2, halfRad);

        return corners;
    }

    private void paintHex(BufferedImage image, int j, int k, boolean filled) {

        Point[] corners = new Point[Model.SIDES];
        for (int i = 0; i < Model.SIDES; i++) {
            corners[i] = new Point(leftTopHex[i].x + k * deltaX + (j % 2) * (deltaX / 2), leftTopHex[i].y + j * deltaY);
        }
        for (int i = 0; i < Model.SIDES; i++) {
            drawLine(image, corners[i].x, corners[i].y, corners[(i + 1) % Model.SIDES].x, corners[(i + 1) % Model.SIDES].y);
        }
        if (filled) {
            Point center = getHexCenter(j, k);
            fillHex(image, center);
        }
        if (showImpacts) {
            Point center = getHexCenter(j, k);
            Graphics g = image.getGraphics();
            FontMetrics fontMetrics = g.getFontMetrics();
            double impact = gameModel.countImpact(j,k);
            String impactStr;
            if (impact - (int)impact == 0) {
                impactStr = String.format("%d", (int)impact);
            } else {
                impactStr = String.format("%.1f", impact);
            }
            int strWidth = fontMetrics.stringWidth(impactStr);
            g.setColor(textColor);
            g.drawString(impactStr, center.x - (strWidth / 2), center.y + g.getFontMetrics().getHeight() / 2);
        }
    }

    public Point getHexCenter(int x, int y) {
        int coordX = halfHexWidth* ((1 + (x % 2)) + 2 * y);
        int coordY = halfRad * (2 + 3 * x);
        return new Point(coordX, coordY);
    }

    private void drawLine(BufferedImage image, int xstart, int ystart, int xend, int yend) {
        if (thickness == 1) {
            drawBresenhamLine(image, xstart, ystart, xend, yend);
        } else {
            Graphics2D g = (Graphics2D)image.getGraphics();
            BasicStroke pen1 = new BasicStroke(thickness);

            g.setStroke(pen1);
            g.setColor(borderColor);
            g.drawLine(xstart, ystart, xend, yend);
        }
    }


    private int sign (int x) {
        return Integer.compare(x, 0);
    }

    private void drawBresenhamLine(BufferedImage image, int xstart, int ystart, int xend, int yend) {
        int dx = xend - xstart;
        int dy = yend - ystart;

        int directX = sign(dx);
        int directY = sign(dy);

        dx = Math.abs(dx);
        dy = Math.abs(dy);

        int stepX;
        int stepY;
        int deltaErr;
        int errLim;

        if (dx > dy) {
            stepX = directX;
            stepY = 0;
            deltaErr = dy;
            errLim = dx;
        } else {
            stepX = 0;
            stepY = directY;
            deltaErr = dx;
            errLim = dy;
        }

        int x = xstart;
        int y = ystart;

        int err = 0;
        image.setRGB(x, y, borderColor.getRGB());
        for (int t = 0; t < errLim; t++) {
            err += deltaErr;
            if (2 * err >= errLim) {
                err -= errLim;
                x += directX;
                y += directY;
            } else {
                x += stepX;
                y += stepY;
            }

            image.setRGB(x, y, borderColor.getRGB());
        }
    }


    private void fillHex(BufferedImage image, Point p) {
        Stack<Point> pointStack = new Stack<>();
        pointStack.push(p);
        while (!pointStack.isEmpty()) {
            Point currentPoint = pointStack.pop();
            Point lPoint = getLeftBorder(image, currentPoint);
            Point rPoint = getRightBorder(image, currentPoint);
            fillSpan(image, lPoint, rPoint);

            boolean needTop = true;
            boolean needBottom = true;

            int y = lPoint.y;
            for (int x = lPoint.x; x < rPoint.x; x++) {
                if (needTop) {
                    if (image.getRGB(x, y - 1) != borderColor.getRGB()
                            && image.getRGB(x, y - 1) != fillColor.getRGB()) {
                        pointStack.push(new Point(x, y - 1));
                        needTop = false;
                    }
                }
                if (needBottom) {
                    if (image.getRGB(x, y + 1) != borderColor.getRGB()
                            && image.getRGB(x, y + 1) != fillColor.getRGB()) {
                        pointStack.push(new Point(x, y + 1));
                        needBottom = false;
                    }
                }
                if (!needTop && !needBottom) {
                    break;
                }
            }
        }
    }

    private void fillSpan(BufferedImage image, Point leftBorder, Point rightBorder) {
        int y = leftBorder.y;
        for (int x = leftBorder.x; x < rightBorder.x; x++) {
            image.setRGB(x,y, fillColor.getRGB());
        }
    }

    private Point getLeftBorder(BufferedImage image, Point p) {
        int currentX = p.x;
        while (true) {
            if (image.getRGB(currentX, p.y) == borderColor.getRGB()) {
                ++currentX;
                break;
            }
            --currentX;
        }
        return new Point(currentX, p.y);
    }

    private Point getRightBorder(BufferedImage image, Point p) {
        int currentX = p.x;
        while (true) {
            if (image.getRGB(currentX, p.y) == borderColor.getRGB()) {
//                ++currentX;
                break;
            }
            ++currentX;
        }
        return new Point(currentX, p.y);
    }

    private void onStateChanged(Object fieldState) {
        this.fieldState = (boolean[])fieldState;
        bufferedImage = paintHexField();
    }

    private void onFieldWidthChanged(Object newWidth) {
        fieldState = gameModel.getFieldState();
        fieldWidth = (int)newWidth;
        updateField();
        bufferedImage = paintHexField();
    }

    private void onFieldHeightChanged(Object newHeight) {
        fieldState = gameModel.getFieldState();
        fieldHeight = (int)newHeight;
        updateField();
        bufferedImage = paintHexField();
    }

    private void onRadiusChanged(Object newRadius) {
        rad = (int)newRadius;
        updateField();
        bufferedImage = paintHexField();
    }

    private void onThicknessChanged(Object newThickness) {
        thickness = (int)newThickness;
        bufferedImage = paintHexField();
    }

    private void onSettingsModelUpdated(Object object) {
        updateField();
    }

    public int getHalfHexWidth() {
        return halfHexWidth;
    }

    public int getHalfRad() {
        return halfRad;
    }

    public boolean isImpactsEnabled() {
        return showImpacts;
    }

    public void setImpactsEnabled(boolean enabled) {
        showImpacts = enabled;
        bufferedImage = paintHexField();
    }
}
