package Test;

import Converter.Convertion;
import Converter.Util.Util;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;


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
