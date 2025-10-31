package Tests.utils;


import com.application.HistoryManager;
import com.utils.BitMapImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.application.ImageScreen;

import static org.junit.Assert.assertEquals;

class HistoryManagerTest {
    private HistoryManager dummyHistoryManager;
    private BitMapImage image1;
    private BitMapImage image2;
    private BitMapImage image3;
    private BitMapImage currentImage;
    private int orginalWidth;
    private int orginalHeight;
    


    @BeforeEach
    void setUp(){

        image1 = new BitMapImage(50,50);
        image2 = new BitMapImage(40,40);
        image3 = new BitMapImage(30,30);

         dummyHistoryManager = new HistoryManager();

         image1 = new BitMapImage(50,50);
         image1.resetToWhite();
            image2 = new BitMapImage(50,50);
            image2.resetToWhite();

            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    image2.setPixel(i, j, 255, 0, 0); // Red
                }
            }
            image3 = new BitMapImage(70,70);
            image3.resetToWhite();

            for (int i = 50; i < 70; i++) {
                for (int j = 50; j < 70; j++) {
                    image3.setPixel(i, j, 0, 0, 255); // Blue
                }
            }

            ImageScreen.currentImageWidth = 50;
            ImageScreen.currentImageHeight = 50;
            ImageScreen.currentImage = image1;

       // dummyHistoryManager.addCanvas(image1);
        //dummyHistoryManager.addCanvas(image2);
    }
    @Test
    public void testAddCanvas() {
        assertEquals(2, dummyHistoryManager.getUndoList().size());
    }

    @Test
    public void testStackFunctionality(){
        assertEquals(2, dummyHistoryManager.getUndoList().size());
        BitMapImage test = dummyHistoryManager.getLastCanvas();
        assertEquals(1, dummyHistoryManager.getUndoList().size());
        BitMapImage test2 = dummyHistoryManager.getLastCanvas();
        assertEquals(0, dummyHistoryManager.getUndoList().size());
    }

    @Test
    public void testReturnValueOfPop(){
        assertEquals(dummyHistoryManager.getLastCanvas(), image2);
        assertEquals(1, dummyHistoryManager.getUndoList().size());
        assertEquals(dummyHistoryManager.getLastCanvas(), image1);
    }
    @Test
    void testCanRedoFalse(){
        assertFalse( dummyHistoryManager.canRedo(), "Redo should not be possible when redo stack is empty");
    }
    @Test
    void testCanRedoAfterUndo() {
        dummyHistoryManager.addCanvas(image1);
        ImageScreen.currentImage = image2;
        
        dummyHistoryManager.getLastCanvas();
        assertTrue(dummyHistoryManager.canRedo(), "Should be able to redo after undo");
    }
     @Test
    void testGetRedoCanvasWhenEmpty() {
        BitMapImage result = dummyHistoryManager.getRedoCanvas();
        assertNull(result, "Should return null when redo list is empty");
    }



}
