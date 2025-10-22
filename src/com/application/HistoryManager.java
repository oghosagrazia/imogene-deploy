package com.application;
import com.application.panels.ImageScreen;
import com.utils.BitMapImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HistoryManager {
    private final ArrayList<BitMapImage> undoList = new ArrayList<>();
    public void addCanvas(BitMapImage bitMapImage){
        undoList.add(bitMapImage);
    }

    public BitMapImage getLastCanvas(){
        // break if canvas is still blank
        if (undoList.isEmpty() || undoList.getLast() == null){
            BitMapImage whiteIMG = new BitMapImage(ImageScreen.currentImageWidth, ImageScreen.currentImageHeight);
            whiteIMG.resetToWhite();

            return whiteIMG;
        }
        // pop last element
        BitMapImage previousCanvas = undoList.getLast();
        undoList.removeLast();

        // Return last image
        return previousCanvas;
    }
    public ArrayList<BitMapImage> getUndoList(){
        return undoList;
    }
}
