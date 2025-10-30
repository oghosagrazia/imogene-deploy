package Tests.utils;


import com.application.HistoryManager;
import com.utils.BitMapImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class HistoryManagerTest {
    HistoryManager dummyHistoryManager;
    BitMapImage image1;
    BitMapImage image2;

    @BeforeEach
    void setUp(){
        dummyHistoryManager = new HistoryManager();
        image1 = new BitMapImage(50,50);
        image2 = new BitMapImage(40,40);

        dummyHistoryManager.addCanvas(image1);
        dummyHistoryManager.addCanvas(image2);
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

}
