package com.application;

import com.imogene.utils.BitMapImage;
import com.imogene.utils.ImageRW;

import java.io.File;
import java.io.IOException;

public class LoadPhoto {
    public BitMapImage loadImage(File path) throws IOException {
        if (path.exists() && path.isFile()) {
            return ImageRW.readImage(path.getAbsolutePath());
        }
        throw new IOException("File does not exist or is not a file");
    }
}
