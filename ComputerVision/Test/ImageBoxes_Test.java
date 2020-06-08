import Converter.ImageBoxes;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import static org.junit.Assert.*;

public class ImageBoxes_Test {

    @Test
    public void calibratingImageBoxes(){
        ImageBoxes boxes = new ImageBoxes();

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("C:\\Uddannelse\\DTU\\4sem\\CDIO\\Kabale_V2\\ComputerVision\\TestKabale.PNG"));
        } catch (IOException e) {
        }
        System.out.println("Test image width: " + img.getWidth());
        System.out.println("Test image height: " + img.getHeight());

        //Converting BufferedImage to javaFX Image
        Image image = SwingFXUtils.toFXImage(img, null);

        System.out.println("Test image JavaFX width: " + image.getWidth());
        System.out.println("Test image JavaFX height: " + image.getHeight());

        System.out.println("Testimage converted to javaFX image");

        int totalHeight = img.getHeight();
        int totalWidth = img.getWidth();

        //Creating draw pile
        int numberOfPiles = 7;
        double pileWidth = totalWidth/numberOfPiles;

        double[] drawPiles = new double[numberOfPiles];
        drawPiles[0] = pileWidth*1;
        drawPiles[1] = pileWidth*2;
        drawPiles[2] = pileWidth*3;
        drawPiles[3] = pileWidth*4;
        drawPiles[4] = pileWidth*5;
        drawPiles[5] = pileWidth*6;
        drawPiles[6] = pileWidth*7;

        // creating foundationPile
        double[] foundationPiles = new double[4];
        foundationPiles[0] = totalWidth/5*2;
        foundationPiles[1] = totalWidth/5*3;
        foundationPiles[2] = totalWidth/5*4;
        foundationPiles[3] = totalWidth/5*5;

        //Creating draw pile
        double[] drawPile = new double[1];
        drawPile[0] = totalWidth/5*1;

        List<double[]> boxs = boxes.calibrateImgBoxes(image);

        for(int i = 0;i<1;i++){
            assertEquals(drawPile[i],boxs.get(0)[i],1);

        }

        for(int i = 0;i<4;i++){
            assertEquals(foundationPiles[i],boxs.get(1)[i],1);
        }

        for(int i = 0;i<6;i++){
            assertEquals(drawPiles[i],boxs.get(2)[i],1);
        }
    }
}
