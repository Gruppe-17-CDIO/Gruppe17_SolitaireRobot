import Converter.Convertion;
import Converter.ImageBoxes;
import DarkNet_Connection.Darknet_Stub;
import DarkNet_Connection.I_Connection;
import Data.PreCard;
import dataObjects.Card;
import dataObjects.ConvertState;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;

/**
 * * @author Andreas B.G. Jensen
 */
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

        List<double[]> boxs = boxes.returnImgBoxes(image,null);

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



    @Test
    public void Test_BoxMapping(){
        ImageBoxes boxes = new ImageBoxes();
        Convertion convert = new Convertion();
        I_Connection connection = new Darknet_Stub();



        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("C:\\Uddannelse\\DTU\\4sem\\CDIO\\Kabale_V2\\ComputerVision\\TestKabale.PNG"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Converting BufferedImage to javaFX Image
        Image image = SwingFXUtils.toFXImage(img, null);
        //Creating boxes
        List<double[]> boxs = boxes.returnImgBoxes(image,null);

        //Creating a return from
        List<PreCard> returnFromDarknet = convert.ConvertImage(image);
        //converting all precard to Card object for late use to assertEqual
        List<Card> preCardToCards = preCardToCard(returnFromDarknet);

        ConvertState currentState = boxes.boxMapping(returnFromDarknet,boxs,image);





        //Compearing draw card
        assertEquals(preCardToCards.get(0).getColor(),currentState.getDraw().getColor());
        assertEquals(preCardToCards.get(0).getRank(),currentState.getDraw().getRank());

        //Compearing  Foundation
        assertEquals(preCardToCards.get(1).getColor(),currentState.getFoundation1().getColor());
        assertEquals(preCardToCards.get(1).getRank(),currentState.getFoundation1().getRank());

        assertEquals(preCardToCards.get(2).getColor(),currentState.getFoundation2().getColor());
        assertEquals(preCardToCards.get(2).getRank(),currentState.getFoundation2().getRank());

        assertEquals(preCardToCards.get(3).getColor(),currentState.getFoundation3().getColor());
        assertEquals(preCardToCards.get(3).getRank(),currentState.getFoundation3().getRank());

        assertEquals(preCardToCards.get(4).getColor(),currentState.getFoundation4().getColor());
        assertEquals(preCardToCards.get(4).getRank(),currentState.getFoundation4().getRank());

        //Comparing rowPiles
        assertEquals(preCardToCards.get(5).getColor(),currentState.getRow1().getColor());
        assertEquals(preCardToCards.get(5).getRank(),currentState.getRow1().getRank());

        assertEquals(preCardToCards.get(6).getColor(),currentState.getRow2().getColor());
        assertEquals(preCardToCards.get(6).getRank(),currentState.getRow2().getRank());

        assertEquals(preCardToCards.get(7).getColor(),currentState.getRow3().getColor());
        assertEquals(preCardToCards.get(7).getRank(),currentState.getRow3().getRank());

        assertEquals(preCardToCards.get(8).getColor(),currentState.getRow4().getColor());
        assertEquals(preCardToCards.get(8).getRank(),currentState.getRow4().getRank());

        assertEquals(preCardToCards.get(9).getColor(),currentState.getRow5().getColor());
        assertEquals(preCardToCards.get(9).getRank(),currentState.getRow5().getRank());

        assertEquals(preCardToCards.get(10).getColor(),currentState.getRow6().getColor());
        assertEquals(preCardToCards.get(10).getRank(),currentState.getRow6().getRank());

        assertEquals(preCardToCards.get(11).getColor(),currentState.getRow7().getColor());
        assertEquals(preCardToCards.get(11).getRank(),currentState.getRow7().getRank());

    }

    @Test
    public void Test_BoxMapping_Shuffle_PreCard(){


        ImageBoxes boxes = new ImageBoxes();
        Convertion convert = new Convertion();
        I_Connection connection = new Darknet_Stub();



        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("C:\\Uddannelse\\DTU\\4sem\\CDIO\\Kabale_V2\\ComputerVision\\TestKabale.PNG"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Converting BufferedImage to javaFX Image
        Image image = SwingFXUtils.toFXImage(img, null);
        //Creating boxes
        List<double[]> boxs = boxes.returnImgBoxes(image,null);

        //Creating a return from
        List<PreCard> returnFromDarknet = convert.ConvertImage(image);
        Collections.shuffle(returnFromDarknet);

        returnFromDarknet = sortingListX(returnFromDarknet,image);

        //converting all precard to Card object for late use to assertEqual
        List<Card> preCardToCards = preCardToCard(returnFromDarknet);

        ConvertState currentState = boxes.boxMapping(returnFromDarknet,boxs,image);





        //Compearing draw card
        assertEquals(preCardToCards.get(0).getColor(),currentState.getDraw().getColor());
        assertEquals(preCardToCards.get(0).getRank(),currentState.getDraw().getRank());

        //Compearing  Foundation
        assertEquals(preCardToCards.get(1).getColor(),currentState.getFoundation1().getColor());
        assertEquals(preCardToCards.get(1).getRank(),currentState.getFoundation1().getRank());

        assertEquals(preCardToCards.get(2).getColor(),currentState.getFoundation2().getColor());
        assertEquals(preCardToCards.get(2).getRank(),currentState.getFoundation2().getRank());

        assertEquals(preCardToCards.get(3).getColor(),currentState.getFoundation3().getColor());
        assertEquals(preCardToCards.get(3).getRank(),currentState.getFoundation3().getRank());

        assertEquals(preCardToCards.get(4).getColor(),currentState.getFoundation4().getColor());
        assertEquals(preCardToCards.get(4).getRank(),currentState.getFoundation4().getRank());

        //Comparing rowPiles
        assertEquals(preCardToCards.get(5).getColor(),currentState.getRow1().getColor());
        assertEquals(preCardToCards.get(5).getRank(),currentState.getRow1().getRank());

        assertEquals(preCardToCards.get(6).getColor(),currentState.getRow2().getColor());
        assertEquals(preCardToCards.get(6).getRank(),currentState.getRow2().getRank());

        assertEquals(preCardToCards.get(7).getColor(),currentState.getRow3().getColor());
        assertEquals(preCardToCards.get(7).getRank(),currentState.getRow3().getRank());

        assertEquals(preCardToCards.get(8).getColor(),currentState.getRow4().getColor());
        assertEquals(preCardToCards.get(8).getRank(),currentState.getRow4().getRank());

        assertEquals(preCardToCards.get(9).getColor(),currentState.getRow5().getColor());
        assertEquals(preCardToCards.get(9).getRank(),currentState.getRow5().getRank());

        assertEquals(preCardToCards.get(10).getColor(),currentState.getRow6().getColor());
        assertEquals(preCardToCards.get(10).getRank(),currentState.getRow6().getRank());

        assertEquals(preCardToCards.get(11).getColor(),currentState.getRow7().getColor());
        assertEquals(preCardToCards.get(11).getRank(),currentState.getRow7().getRank());
    }



    private Card.Suit createSuit(PreCard preCard){

        switch (preCard.getColor()){
            case "H": return Card.Suit.HEART;
            case "D": return Card.Suit.DIAMOND;
            case "S": return Card.Suit.SPADE;
            case "K": return Card.Suit.CLUB;

        }
        return null;
    }

    private List<Card> preCardToCard(List<PreCard> returnFromDarknet){
        //Creating cardobject for all PreCards
        List<Card> preCardToCards = new ArrayList<>();
        for(int i = 0; i<returnFromDarknet.size();i++){
            Card.Suit suit = createSuit(returnFromDarknet.get(i));
            Card card = null;
            try {
                card = new Card(suit,returnFromDarknet.get(i).getRank());
            } catch (Exception e) {
                e.printStackTrace();
            }
            preCardToCards.add(card);
        }
    return preCardToCards;
    }



    private List<PreCard> sortingListX(List<PreCard> list, Image img){

        List<PreCard> tempList = new ArrayList<>();

        PreCard tempPreCard;
        int listNr = list.size();


        while(tempList.size()!=listNr){
            tempPreCard = list.get(0);
            for(int i = 0; i<list.size();i++){
                if(list.get(i).getUpperCoordinate().getX()<=tempPreCard.getUpperCoordinate().getX()){
                    tempPreCard = list.get(i);
                }
            }
            tempList.add(tempPreCard);
            list.remove(tempPreCard);
        }
        return sortingListY(tempList,img);
    }


    private List<PreCard> sortingListY(List<PreCard> list, Image img){
        double lowerHight = img.getHeight()/2;

        PreCard tempPreCard;

        int listLength = list.size();


        for(int i = 0; i<listLength;i++){
            if(list.get(i).getUpperCoordinate().getY()>lowerHight){

                tempPreCard = list.get(i);

                list.remove(tempPreCard);
                list.add(tempPreCard);
                i--;
                listLength--;
            }

        }
    return list;
    }



}
