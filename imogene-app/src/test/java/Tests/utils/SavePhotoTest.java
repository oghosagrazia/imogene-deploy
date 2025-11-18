package Tests.utils;

import com.application.SavePhoto;
import com.utils.BitMapImage;
import com.utils.ImageRW;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class SavePhotoTest {

    private SavePhoto savePhoto;
    private BitMapImage testImage;
    private MockedStatic<ImageRW> mockedImageRW;

    @BeforeEach
    void setUp() {
        savePhoto = new SavePhoto();
        testImage = new BitMapImage(100, 100);
        testImage.resetToWhite();
        
        mockedImageRW = Mockito.mockStatic(ImageRW.class);
    }

    @AfterEach
    void tearDown() {
        if (mockedImageRW != null) {
            mockedImageRW.close();
        }
    }

    @Test
    void testSaveImageWithName() {
        assertDoesNotThrow(() -> {
            boolean result = savePhoto.saveImage(testImage);
        }, "Save Image should not throw exceptions with valid input");
    }

    @Test
    void testSaveImageToPathWithValidPngFile() throws IOException {
        String testFilePath = "test_image.png";
        
        mockedImageRW.when(() -> ImageRW.writeImage(eq(testImage), eq(testFilePath), eq("png")))
                     .thenAnswer(invocation -> null);
        
        boolean result = savePhoto.saveImageToPath(testImage, testFilePath);
        
        assertTrue(result, "saveImageToPath should return true for successful save");
        
        mockedImageRW.verify(() -> ImageRW.writeImage(testImage, testFilePath, "png"));
    }

    @Test
    void testSaveImageToPathWithValidJpgFile() throws IOException {
        String testFilePath = "test_image.jpg";
        
        mockedImageRW.when(() -> ImageRW.writeImage(eq(testImage), eq(testFilePath), eq("jpg")))
                     .thenAnswer(invocation -> null);
        
        boolean result = savePhoto.saveImageToPath(testImage, testFilePath);
        
        assertTrue(result, "saveImageToPath should return true for JPG files");
        
        mockedImageRW.verify(() -> ImageRW.writeImage(testImage, testFilePath, "jpg"));
    }

    @Test
    void testSaveImageToPathThrowsIOException() {
        String testFilePath = "invalid_path/test_image.png";
        
        mockedImageRW.when(() -> ImageRW.writeImage(eq(testImage), eq(testFilePath), eq("png")))
                     .thenThrow(new IOException("Cannot write to file"));
        
        assertThrows(IOException.class, () -> {
            savePhoto.saveImageToPath(testImage, testFilePath);
        }, "saveImageToPath should throw IOException when ImageRW fails");
        
        mockedImageRW.verify(() -> ImageRW.writeImage(testImage, testFilePath, "png"));
    }
}