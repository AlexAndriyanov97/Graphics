package ru.nsu.fit.g16208.andriyanov.graphics.model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderFrame extends JFrame {
    private BufferedImage renderImage;

    public RenderFrame(BufferedImage renderImage) {
        this.renderImage = renderImage;
        setSize(renderImage.getWidth(),renderImage.getHeight());
        add(new ImageHolder());
        setVisible(true);
    }

    private class ImageHolder extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(renderImage, 0, 0, this);
        }
    }
}