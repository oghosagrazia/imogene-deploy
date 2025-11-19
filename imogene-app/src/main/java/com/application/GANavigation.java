package com.application;

import com.utils.BitMapImage;
import java.util.ArrayList;

public class GANavigation {
    private final ArrayList<BitMapImage> generations = new ArrayList<>();
    int currentGenerationIndex = -1;

    public int getCurrentGenerationIndex() {
        return currentGenerationIndex;
    }

    public void addGeneration(BitMapImage bitMapImage) {
        generations.add(bitMapImage);
        currentGenerationIndex = generations.size() - 1;
    }
    public BitMapImage getGenerationIndex(int index) {
        if (index >= 0 && index < generations.size()) {
            return generations.get(index);
        } else  {
            return null;
        }
    }
    public BitMapImage nextGeneration() {
        if(generations.isEmpty()) return null;

        if (currentGenerationIndex < generations.size() - 1 && currentGenerationIndex >= 0) {
            currentGenerationIndex++;
        }
        return generations.get(currentGenerationIndex);
    }
    public BitMapImage previousGeneration() {
        if(generations.isEmpty()) return null;

        if (currentGenerationIndex > 0) {
            currentGenerationIndex--;
        }
        return generations.get(currentGenerationIndex);
    }
    public void clearGenerations() {
        generations.clear();
        currentGenerationIndex = -1;
    }
    public void setCurrentGenerationIndex(int currentGenerationIndex) {
        this.currentGenerationIndex = currentGenerationIndex;
    }
}
