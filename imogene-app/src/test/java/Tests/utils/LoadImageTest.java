package Tests.utils;

import com.application.LoadPhoto;
import com.imogene.utils.BitMapImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LoadImageTest {
    LoadPhoto loadPhoto;

    @BeforeEach
    void setUp() {
        loadPhoto = new LoadPhoto();
    }


    // Test an invalid file, exception expected
    @Test
    void invalidFileTest() throws IOException {
        File tempInvalidFile = new File("invalidFile");

        IOException exception = assertThrows(IOException.class, () -> loadPhoto.loadImage(tempInvalidFile));
        assertEquals("File does not exist or is not a file", exception.getMessage());
    }


    // Test with a valid file
    @Test
    void validFileTest() throws IOException {
        // create a temp file
        File tempValidFile = File.createTempFile("test", ".bmp");
        tempValidFile.deleteOnExit();

        // Convert file to type BitMapImage
        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        bufferedImage.setRGB(0, 0, 255);
        ImageIO.write(bufferedImage, "BMP", tempValidFile);

        BitMapImage image = loadPhoto.loadImage(tempValidFile);

        assertNotNull(image, "Expected image to be loaded");
    }
}
