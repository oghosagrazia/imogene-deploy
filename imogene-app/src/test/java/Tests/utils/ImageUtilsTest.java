package Tests.utils;
import com.utils.BitMapImage;
import com.utils.ImageUtils;

import static org.junit.Assert.*;
public class ImageUtilsTest {

    // Test given in seminar
    @org.junit.Test
    public void testInvert() {
        int[][][] rgb = new int[][][]{{{255, 255, 255}}};
        BitMapImage image = new BitMapImage(rgb);
        image = ImageUtils.invert(image);
        rgb = image.getRgb();
        assert (rgb[0][0][0] == 0);
        assert (rgb[0][0][1] == 0);
        assert (rgb[0][0][2] == 0);
    }

    // Test for null image
    @org.junit.Test
    public void nullImageThrow() {
        try {
        ImageUtils.scale(null, 2, 2);
        fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException expected){}
        }

    // Test negative size input
    @org.junit.Test
    public void NegativeInputsThrow() {
        try {
            BitMapImage image = new BitMapImage(new int[][][]{{{1,2,3}}});
            ImageUtils.scale(image, 1, -5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected){}
    }

    // Test invalid size input
    @org.junit.Test
    public void zeroInputsThrow() {
        try {
            BitMapImage image = new BitMapImage(new int[][][]{{{1,2,3}}});
            ImageUtils.scale(image, 0, 1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected){}
    }

    // Test for scaling up
    @org.junit.Test
    public void testScaleUp() {
        int[][][] rgb = new int[][][]{ {{255, 255, 255}} };
        BitMapImage image = new BitMapImage(rgb);

        BitMapImage scaleUp = ImageUtils.scale(image, 2, 2);
        int[][][] scaledResult = scaleUp.getRgb();

        // Check match of pixels
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                assertArrayEquals(new int []{255, 255, 255}, scaledResult[y][x]);
            }
        }
    }

    // Test for scaling down
    @org.junit.Test
    public void testScaleDown() {
        int[][][] rgb = new int[][][]{
                {{255, 255, 255}, {255, 255, 255}},{{255, 255, 255}, {255, 255, 255}} };

        BitMapImage image = new BitMapImage(rgb);

        BitMapImage scaleDown = ImageUtils.scale(image, 1, 1);
        int[][][] scaledResult = scaleDown.getRgb();

        assertArrayEquals(new int []{255, 255, 255}, scaledResult[0][0]);
    }

    // Test for changing image width
    @org.junit.Test
    public void testCustomWidth() {
        int[][][] rgb = new int[][][]{ {{255, 255, 255}, {255, 255, 255}} };
        BitMapImage image = new BitMapImage(rgb);

        BitMapImage scaled = ImageUtils.scale(image, 4, 1);
        int[][][] scaledResult = scaled.getRgb();

        for (int x = 0; x < 4; x++) {
            assertArrayEquals(new int []{255, 255, 255}, scaledResult[0][x]);
        }
    }

    // Test for changing image height
    @org.junit.Test
    public void testCustomHeight() {
        int[][][] rgb = new int[][][]{ {{255, 255, 255}, {255, 255, 255}} };
        BitMapImage image = new BitMapImage(rgb);

        BitMapImage scaled = ImageUtils.scale(image, 1, 4);
        int[][][] scaledResult = scaled.getRgb();

        for (int y = 0; y < 4; y++) {
            assertArrayEquals(new int []{255, 255, 255}, scaledResult[y][0]);
        }
    }

    // Test no change in scale
    @org.junit.Test
    public void testScaleNoCustomisation() {
        int[][][] rgb = new int[][][]{{{10, 20, 30}, {40, 50, 60}}, {{70, 80, 90}, {100, 110, 120}}};
        BitMapImage image = new BitMapImage(rgb);

        BitMapImage scaled = ImageUtils.scale(image, 2, 2);
        int[][][] scaledResult = scaled.getRgb();

        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                assertArrayEquals( new int[] {rgb[y][x][0], rgb[y][x][1], rgb[y][x][2]} ,scaledResult[y][x]);
            }
        }
    }

    // Test colour stays the same during scaling
    @org.junit.Test
    public void testColourScale() {
        int[][][] rgb = new int[][][]{{{12, 34, 56}}};
        BitMapImage image = new BitMapImage(rgb);

        BitMapImage scaled = ImageUtils.scale(image, 4, 3);
        int[][][] scaledResult = scaled.getRgb();
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 4; x++) {
                assertArrayEquals(new int[]{12, 34, 56}, scaledResult[y][x]);
            }
        }
    }
}

