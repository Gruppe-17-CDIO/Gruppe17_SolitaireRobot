import Converter.Convertion;
import Test.Test_Convertion;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

public class Convertion_Test {
    Convertion converter = new Convertion();


    /*
    Testing that an image will be put into sections.
     */

    @Test
    private void Test_calibrateImgBoxes(){

        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Image im = convertToFxImage(image);

        System.out.println(image.getWidth());
        System.out.println(image.getHeight());

        converter.calibrateImgBoxes(im);




    }

    /*
    Convertin a BufferedImage to jfx image
    This is used only for testing.
     */
    @Test
    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }
}
