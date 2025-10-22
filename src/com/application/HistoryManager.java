package com.application;
import com.API.GenerationConnector;
import com.application.panels.DrawingPanel;
import com.application.panels.ImageScreen;
import com.utils.BitMapImage;
import com.utils.ImageRW;

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
        if (undoList.isEmpty()){
            return null;
        }

        // pop last element
        BitMapImage previousCanvas = undoList.getLast();
        undoList.removeLast();

        return previousCanvas;
    }
}
