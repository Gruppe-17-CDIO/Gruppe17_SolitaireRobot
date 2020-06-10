package DarkNet_Connection;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.File;
import java.io.IOException;


/*
This is a test class.

 */
public class stubImageCoordinates {

    public static void coordinate(){
        try {
            MBFImage image = ImageUtilities.readMBF(new File("C:\\Uddannelse\\DTU\\4sem\\CDIO\\Kabale_V2\\ComputerVision\\TestKabale.PNG"));
            DisplayUtilities.display(image);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        coordinate();
    }


}
