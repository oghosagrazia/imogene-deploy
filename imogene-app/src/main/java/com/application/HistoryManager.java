package com.application;
import com.application.panels.ImageScreen;
import com.utils.BitMapImage;

import java.util.ArrayList;

public class HistoryManager {
    // ArrayList to store screenshots of previous canvases
    private final ArrayList<BitMapImage> undoList = new ArrayList<>();
    private final ArrayList<BitMapImage> redoList = new ArrayList<>();

    public void addCanvas(BitMapImage bitMapImage){
        undoList.add(bitMapImage);
        redoList.clear(); //similar to word doc redo functionality
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
        redoList.add(ImageScreen.currentImage);

        // Return last image
        return previousCanvas;
    }
    public BitMapImage getRedoCanvas(){
        if (redoList.isEmpty() || redoList.getLast() ==null){
            return null;
        }
        BitMapImage redoCanvas = redoList.getLast();
        redoList.removeLast();
        undoList.add(ImageScreen.currentImage);
        return redoCanvas;
    }
    public boolean canRedo(){
        return !redoList.isEmpty();
    }
    public ArrayList<BitMapImage> getUndoList(){
        return undoList;
    }
}
