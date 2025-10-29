package com.application.panels;

import com.GA.GeneticAlgorithm;
import com.utils.BitMapImage;

import javax.swing.*;
import java.awt.*;

public class ImageScreen extends JPanel {

    public static DrawingPanel drawingPanel;

    public static final boolean UPSCALE = true;
    public  static final int DEFAULT_UPSCALE_FACTOR = 6; // Default scale at startup.

    public static int currentImageHeight = 100;
    public static int currentImageWidth = 133;

    public static BitMapImage currentImage;
    public static GeneticAlgorithm currentGA;
    public static boolean halt;

    // Non-static variables for customisable image size.
    private int customWidth;
    private int customHeight;
    private int upScale;

    // Singleton pattern
    private static final ImageScreen instance = new ImageScreen(100,133,DEFAULT_UPSCALE_FACTOR
    );

    public static ImageScreen getInstance() {
        return instance;
    }

    public ImageScreen(int customHeight, int customWidth, int upScale) {
        // Border layout to accommodate the central image screen and the two sidebars
        super(new BorderLayout());

        // New non-static fields for customisable image sizing.
        this.customWidth = customWidth;
        this.customHeight = customHeight;
        this.upScale = Math.max(1, upScale);

        // Static fields
        currentImageWidth = this.customWidth;
        currentImageHeight = this.customHeight;

        // Left sidebar
        JPanel leftPanel = LeftSidebar.getInstance();

        // Middle image panel - computes scaled pixel size
        int pixelWidth = currentImageWidth * this.upScale;
        int pixelHeight = currentImageHeight * this.upScale;
        drawingPanel = new DrawingPanel(pixelWidth, pixelHeight);
        //drawingPanel.setLayout(new GridBagLayout());

        // Right sidebar
        JPanel rightPanel = RightSidebar.getInstance();

        // Create splitPanes to separate the screen into three resizable panels
        JSplitPane splitLeftCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, drawingPanel);
        splitLeftCenter .setResizeWeight(0.10);
        JSplitPane splitAll = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitLeftCenter, rightPanel);
        splitAll.setResizeWeight(0.85);

        // Create the image screen JPanel and return it
        setLayout(new BorderLayout()); // TODO: why is this here?
        add(splitAll, BorderLayout.CENTER);

    }


    // Sets users customised scale number.
    public void setUpScale(int customScale) {
        customScale = Math.max(1, customScale); // Value must be at least 1.

        // Checks if scale set to exit repainting.
        if (this.upScale == customScale) {
            return;
        }
        this.upScale = customScale;


        redraw();
    }


    // Getters
    public int getCustomWidth() {
        return customWidth;
    }

    public int getCustomHeight() {
        return customHeight;
    }

    public int getUpScale(){
        return upScale;
    }



    public static void drawPixel(int x, int y, Color color) {
        // Using the instance field 'upScale' through the singleton instance.
        int scaleFactor = ImageScreen.getInstance().getUpScale();

        if (ImageScreen.UPSCALE && scaleFactor > 1) {
            for (int xc = x * scaleFactor; xc < x* scaleFactor + scaleFactor; xc++)
                for(int yc = y* scaleFactor; yc < y* scaleFactor + scaleFactor; yc++) {
                    ImageScreen.drawingPanel.setPixel(xc, yc, color);
                }
        }
        else
            ImageScreen.drawingPanel.setPixel(x, y, color);
    }

    public static void redraw() {
        paintImage(ImageScreen.currentImage);
    }

    private static void paintImage(BitMapImage image) {
        int[][][] bitmap = image.getRgb();

        int imageHeight = bitmap.length; // Finds how many pixels are in the height.
        int imageWidth = bitmap[0].length; // Find how many pixels are in the width.

        // Checks if the drawing pixels are within bounds of the real image size.
        for(int y = 0; y < Math.min(ImageScreen.currentImageHeight, imageHeight); y++) {
            for(int x = 0; x < Math.min(ImageScreen.currentImageWidth, imageWidth); x++) {
                drawPixel(x, y, new Color(bitmap[y][x][0], bitmap[y][x][1], bitmap[y][x][2]));
            }
        }
    }


    public void setCustomDimensions(int width, int height) {
        int w = Math.max(1, width);
        int h = Math.max(1, height);

        // Skips if the dimensions are unchanged
        if (this.customWidth == w && this.customHeight == h) { return;}
        this.customWidth = w;
        this.customHeight = h;
        currentImageWidth = this.customWidth;
        currentImageHeight = this.customHeight;

        // Rebuild the drawing panel with the new set dimensions
        rebuildDrawingPanel();
        redraw();
    }


    // Rebuild the drawing panel when size or scale changes
    private void rebuildDrawingPanel() {
        int pixelWidth = customWidth * upScale;
        int pixelHeight = customHeight * upScale;

        DrawingPanel newPanel = new DrawingPanel(pixelWidth, pixelHeight);

        if (drawingPanel != null) {
            Container parent = drawingPanel.getParent();
            if (parent instanceof JSplitPane) {
                ((JSplitPane) parent).setRightComponent(newPanel);
            } else if (parent != null) {
                parent.remove(drawingPanel);
                parent.add(newPanel);
            }
        }
        drawingPanel = newPanel;
        revalidate();
        repaint();
    }
}
