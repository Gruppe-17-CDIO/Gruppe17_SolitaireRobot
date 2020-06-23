import computerVision.Converter.BoxMapping;
import computerVision.Converter.Convertion;
import computerVision.Converter.Util.Sorting.SortingHelperClass;
import DarkNet_Connection.Darknet_Stub;
import Data.JsonDTO;
import Exceptions.BoxMappingException;
import Exceptions.ComputerVisionException;
import computerVision.I_ComputerVisionController;
import dataObjects.TopCards;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author Andreas B.G. Jensen
 * Testing exceptions
 */

public class ThrowExceptions_Test {


    @Test
    public void BoxMappingException_EmptyDarkNetCalibrationImage_Test(){
        String expected_exception_string = "No output from darknet have been detected\nThe calibration could not be done";
        SortingHelperClass sorting = new SortingHelperClass();
        Darknet_Stub darknetReturnList = new Darknet_Stub();
        List<JsonDTO> callibrationImage = darknetReturnList.empty_darknet_calibration_output();

        try {
            BoxMapping mapping = new BoxMapping(sorting);
            mapping.makeBoxMapping(callibrationImage);

        }catch (BoxMappingException e){
            assertEquals(expected_exception_string,e.getMessage());
        }
    }

    @Test
    public void CallibrationImage_Is_less_than_7_elements_in_lowerRow_Test(){
        String expected_exception_string = "The calibrating could not be identified\n" +
                "Please try to adjust the piles.\n " +
                "Keep in mind that the Computer Vision will be able to detect on your dect of cards";

        SortingHelperClass sorting = new SortingHelperClass();
        Darknet_Stub darknetReturnList = new Darknet_Stub();
        List<JsonDTO> callibrationImage = darknetReturnList.missing_Darknet_input_when_Callibrating();

        try {
            BoxMapping mapping = new BoxMapping(sorting);
            mapping.makeBoxMapping(callibrationImage);

        }catch (BoxMappingException e){
            assertEquals(expected_exception_string,e.getMessage());
        }
    }

/**
 * @author Andreas B.G. Jensen
 * Testing if a BoxMappingException is thrown to the Controller Logic
 * To run this test you will have to change the return method from Get_Image_Information(Image img) {return init_Stup_Cards();}
 * to return missing_Darknet_input_when_Callibrating();
 *     }
 */
    @Test
    public void ComputerVisionException_will_be_Thrown_out(){
        I_ComputerVisionController converter = new Convertion();
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Image im = convertToFxImage(image);

        String expected_exception_string = "The calibrating could not be identified\n" +
                "Please try to adjust the piles.\n " +
                "Keep in mind that the Computer Vision will be able to detect on your dect of cards";

        try {

            TopCards actualTopCard = converter.getSolitaireCards(im);

        }catch (ComputerVisionException e){
            System.out.println("An exception is thrown");
            assertEquals(expected_exception_string,e.getMessage());
        }
    }


    /**
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
