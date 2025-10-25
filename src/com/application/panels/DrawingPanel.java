package com.application.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {
    private BufferedImage canvas;

    public DrawingPanel(int pixelWidth, int pixelHeight) {
        setLayout(new GridBagLayout());

        canvas = new BufferedImage(pixelWidth, pixelHeight, BufferedImage.TYPE_INT_RGB);

        // Swing layout matches the image dimensions set.
        setPreferredSize(new Dimension(pixelWidth, pixelHeight));

        clearCanvas();
    }

    public void setPixel(int x, int y, Color color) {
        if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight()) {
            canvas.setRGB(x, y, color.getRGB());
            repaint();
        }
    }

    public void clearCanvas() {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g2d.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, this);
    }
}
