package Tests.utils;
import com.utils.BitMapImage;
import com.utils.ImageUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ImageUtilsTest {

    // Test given in seminar
    @org.junit.Test
    public void testInvert() {
        int[][][] rgb = new int[][][] {{{255, 255, 255}}};
        BitMapImage image = new BitMapImage(rgb);
        image = ImageUtils.invert(image);
        rgb = image.getRgb();
        assert(rgb[0][0][0] == 0);
        assert(rgb[0][0][1] == 0);
        assert(rgb[0][0][2] == 0);
    }
}
