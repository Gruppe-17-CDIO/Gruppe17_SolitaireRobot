import Converter.Convertion;
import Converter.Util.SortingHelperClass;
import Data.PreCard;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * * @author Andreas B.G. Jensen
 */
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


    @Test
    public void Convert_Image_Test(){
        BufferedImage  img = null;
        try {
            img = ImageIO.read(new File("C:\\Uddannelse\\DTU\\4sem\\CDIO\\Kabale_V2\\ComputerVision\\AllDeck.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Image image = SwingFXUtils.toFXImage(img, null);

        List<PreCard> preCardList = converter.ConvertImage(image);
        System.out.println();
    }



    @Test
    public void sortingOfPrecardByX_Test(){
        SortingHelperClass sorting = new SortingHelperClass();
        int numberOfPreCard = 5;
        List<PreCard> preCardList = new ArrayList<>();

        //Creating PreCard objects and adding them to a list
        for(int i = numberOfPreCard; i>0;i--) {
            PreCard obj = new PreCard();
            Point point = new Point(i, numberOfPreCard-i);
            obj.setLowerCoordinate(point);

            preCardList.add(obj);
        }

        preCardList = sorting.sortingTheListOfPrecardsAccordingToX(preCardList);

        List<PreCard> expectedPrecardList = new ArrayList<>();

        PreCard obj = new PreCard();
        Point point = new Point(1, 1);
        obj.setLowerCoordinate(point);

        PreCard obj2 = new PreCard();
        Point point2 = new Point(2, 2);
        obj2.setLowerCoordinate(point2);

        PreCard obj3 = new PreCard();
        Point point3 = new Point(3, 3);
        obj3.setLowerCoordinate(point3);

        PreCard obj4 = new PreCard();
        Point point4 = new Point(4, 4);
        obj4.setLowerCoordinate(point4);

        PreCard obj5 = new PreCard();
        Point point5 = new Point(5, 5);
        obj5.setLowerCoordinate(point5);

        expectedPrecardList.add(obj);
        expectedPrecardList.add(obj2);
        expectedPrecardList.add(obj3);
        expectedPrecardList.add(obj4);
        expectedPrecardList.add(obj5);

        for(int i = 0; i<numberOfPreCard;i++){
            assertEquals(expectedPrecardList.get(i).getLowerCoordinate().getX(),preCardList.get(i).getLowerCoordinate().getX(),0.0);
        }
    }


    @Test
    public void sortingOfPrecardByY_Test(){

        SortingHelperClass sorting = new SortingHelperClass();
        int numberOfPreCard = 5;
        List<PreCard> preCardList = new ArrayList<>();

        //Creating PreCard objects and adding them to a list
        for(int i = numberOfPreCard; i>0;i--) {
            PreCard obj = new PreCard();
            Point point = new Point(i, i);
            obj.setLowerCoordinate(point);

            preCardList.add(obj);
        }

       preCardList = sorting.sortingTheListOfPrecardsAccordingToY(preCardList);

        List<PreCard> expectedPrecardList = new ArrayList<>();

        PreCard obj = new PreCard();
        Point point = new Point(1, 1);
        obj.setLowerCoordinate(point);

        PreCard obj2 = new PreCard();
        Point point2 = new Point(2, 2);
        obj2.setLowerCoordinate(point2);

        PreCard obj3 = new PreCard();
        Point point3 = new Point(3, 3);
        obj3.setLowerCoordinate(point3);

        PreCard obj4 = new PreCard();
        Point point4 = new Point(4, 4);
        obj4.setLowerCoordinate(point4);

        PreCard obj5 = new PreCard();
        Point point5 = new Point(5, 5);
        obj5.setLowerCoordinate(point5);

        expectedPrecardList.add(obj);
        expectedPrecardList.add(obj2);
        expectedPrecardList.add(obj3);
        expectedPrecardList.add(obj4);
        expectedPrecardList.add(obj5);

        for(int i = 0; i<numberOfPreCard;i++){
            assertEquals(expectedPrecardList.get(i).getLowerCoordinate().getX(),preCardList.get(i).getLowerCoordinate().getX(),0.0);
        }
    }


    @Test
    public void removeNonDublicate_Test(){
        SortingHelperClass sorting = new SortingHelperClass();
        List<PreCard> inputPrecardList = new ArrayList<>();
        PreCard obj = new PreCard();
        Point point = new Point(1, 1);
        obj.setRank(10);
        obj.setColor("K");
        obj.setLowerCoordinate(point);

        PreCard obj2 = new PreCard();
        Point point2 = new Point(2, 2);
        obj2.setRank(9);
        obj2.setColor("K");
        obj2.setLowerCoordinate(point2);

        PreCard obj3 = new PreCard();
        Point point3 = new Point(3, 3);
        obj3.setRank(9);
        obj3.setColor("K");
        obj3.setLowerCoordinate(point3);

        PreCard obj4 = new PreCard();
        Point point4 = new Point(3, 3);
        obj4.setRank(10);
        obj4.setColor("K");
        obj4.setLowerCoordinate(point4);

        PreCard obj5 = new PreCard();
        Point point5 = new Point(5, 5);
        obj5.setRank(9);
        obj5.setColor("K");
        obj5.setLowerCoordinate(point5);

        inputPrecardList.add(obj);
        inputPrecardList.add(obj2);
        inputPrecardList.add(obj3);
        inputPrecardList.add(obj4);
        inputPrecardList.add(obj5);

        List<PreCard> expectedDublicateList = new ArrayList<>();
        expectedDublicateList.add(obj3);
        expectedDublicateList.add(obj5);

        List<PreCard> actualPreCardList = sorting.acceptOnlyDublicate(inputPrecardList);

        assertEquals(expectedDublicateList.size(),actualPreCardList.size());
        assertEquals(expectedDublicateList.get(0).toString(),actualPreCardList.get(0).toString());


    }
}
