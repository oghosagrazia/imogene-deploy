package com.application;
import com.utils.BitMapImage;
import com.utils.ImageRW;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.IOException;

public class SavePhoto {

    public boolean saveImage(BitMapImage image) {
        return saveImageWithName(image, null);
       
    }
    public boolean saveImageWithName(BitMapImage image, String name) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Image (*.png)", "png"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG Image (*.jpg;*.jpeg)", "jpg", "jpeg"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("BMP Image (*.bmp)", "bmp"));
            
        if (name != null && !name.isEmpty()) {
            fileChooser.setSelectedFile(new File(name + ".png"));
        }else{
            fileChooser.setSelectedFile(new File("imogene-image" + System.currentTimeMillis() + ".png"));
        }
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try{
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                FileNameExtensionFilter selectedFilter = (FileNameExtensionFilter) fileChooser.getFileFilter();
                String extensions = selectedFilter.getExtensions()[0]; 

                if (!filePath.toLowerCase().endsWith("." + extensions)) {
                    filePath += "." + extensions;
                    fileToSave = new File(filePath);

                }
                ImageRW.writeImage(image, fileToSave.getAbsolutePath(), extensions);

                JOptionPane.showMessageDialog(null, "Image has been saved to:\n" + fileToSave.getAbsolutePath(), "Your Image is Saved", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving image: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
                return false;
        }
    }
    return false;
    }
    public boolean saveImageToPath(BitMapImage image, String filePath) throws IOException{
        String extensions = filePath.substring(filePath.lastIndexOf(".") + 1);
        ImageRW.writeImage(image, filePath, extensions);
        return true;
        }
    }