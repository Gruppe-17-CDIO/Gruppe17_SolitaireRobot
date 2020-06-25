package Test;

import computerVision.Converter.Convertion;
import computerVision.Converter.Util.Util;
import javafx.scene.image.Image;


import java.awt.image.BufferedImage;

/**
 * * @author Andreas B.G. Jensen
 */
public class Test_Create_Image {

    public Image Test_calibrateImgBoxes(){
        Convertion converter = new Convertion();
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Image im = Util.convertToFxImage(image);

        System.out.println(image.getWidth());
        System.out.println(image.getHeight());


        return im;

    }
}
