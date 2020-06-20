import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;

/**
 * @author Andreas B.G. Jensen
 * Not implementet
 */
public class SeriesOfImages_Test {

    @Test
    public void SeriesOf_Images_Test(){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("C:\\Uddannelse\\DTU\\4sem\\CDIO\\Kabale_V2\\ComputerVision\\TestKabale.PNG"));
        } catch (IOException e) {


        }




    }

}
