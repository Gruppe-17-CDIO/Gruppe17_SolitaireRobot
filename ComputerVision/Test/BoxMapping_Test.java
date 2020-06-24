import DarkNet_Connection.I_Connection;
import Exceptions.BufferElementException;
import computerVision.Converter.BoxMapping;
import computerVision.Converter.Util.Sorting.I_Sorting;
import computerVision.Converter.Util.Sorting.SortingHelperClass;
import DarkNet_Connection.Darknet_Stub;
import Data.BufferElement;
import Data.JsonDTO;
import dataObjects.Card;
import dataObjects.TopCards;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static computerVision.Converter.Util.Util.convertToFxImage;
import static org.junit.Assert.*;

/**
 * @author Andreas B.G. Jensen
 */
public class BoxMapping_Test {

    @Test
    public void mappingLowerRow_Test() throws BufferElementException {
        I_Sorting sorting = new SortingHelperClass();
    Darknet_Stub darknetReturnList = new Darknet_Stub();
    List<JsonDTO> expectedPrecardList = darknetReturnList.init_Stup_Cards();


    BufferElement bufferElement = new BufferElement(expectedPrecardList,sorting);
    bufferElement.calibrateImageInputDimensions();
    //bufferElement.calculateVerticalGrid();
        BoxMapping mapping = new BoxMapping(bufferElement,sorting);

    JsonDTO[] mappedJson = mapping.mappingLowerRow();
    JsonDTO[] expectedMappingLowerRow = new JsonDTO[7];

    JsonDTO obj = new JsonDTO();
    obj.setCat("5s");
        JsonDTO obj2 = new JsonDTO();
        obj2.setCat("2h");
        JsonDTO obj3 = new JsonDTO();
        obj3.setCat("8h");
        JsonDTO obj4 = new JsonDTO();
        obj4.setCat("2c");
        JsonDTO obj5 = new JsonDTO();
        obj5.setCat("5c");
        JsonDTO obj6 = new JsonDTO();
        obj6.setCat("9c");
        JsonDTO obj7 = new JsonDTO();
        obj7.setCat("8c");

        expectedMappingLowerRow[0] = obj;
        expectedMappingLowerRow[1] = obj2;
        expectedMappingLowerRow[2] = obj3;
        expectedMappingLowerRow[3] = obj4;
        expectedMappingLowerRow[4] = obj5;
        expectedMappingLowerRow[5] = obj6;
        expectedMappingLowerRow[6] = obj7;


    for(int i = 0; i<mappedJson.length;i++) {
        assertEquals(expectedMappingLowerRow[i].getCat(),mappedJson[i].getCat());
        System.out.println("Expected value: "+ expectedMappingLowerRow[i].getCat()+", Actual value: " + mappedJson[i].getCat());
    }


    }

    @Test
    public void mappingUpperRow_Test() throws BufferElementException {

        I_Sorting sorting = new SortingHelperClass();
        Darknet_Stub darknetReturnList = new Darknet_Stub();
        List<JsonDTO> expectedPrecardList = darknetReturnList.init_Stup_Cards();


        BufferElement bufferElement = new BufferElement(expectedPrecardList,sorting);
        bufferElement.calibrateImageInputDimensions();
       // bufferElement.calculateVerticalGrid();
        BoxMapping mapping = new BoxMapping(bufferElement,sorting);

        JsonDTO[] mappedJson = mapping.mappingUpperRow();

        //Setting the expectet outcome
        JsonDTO obj = new JsonDTO();
        obj.setCat("Jc");
        JsonDTO[] expectedMappingLowerRow = new JsonDTO[1];
        expectedMappingLowerRow[0] = obj;

        for(int i = 0; i<mappedJson.length;i++) {
            assertEquals(expectedMappingLowerRow[i].getCat(),mappedJson[i].getCat());
            System.out.println("Expected value: "+ expectedMappingLowerRow[i].getCat()+", Actual value: " + mappedJson[i].getCat());
        }
    }


    @Test
    public void mappingToTopCard_Test_instanciation() throws Exception {
        SortingHelperClass sorting = new SortingHelperClass();
        Darknet_Stub darknetReturnList = new Darknet_Stub();
        List<JsonDTO> expectedPrecardList = darknetReturnList.init_Stup_Cards();


        BufferElement bufferElement = new BufferElement(expectedPrecardList,sorting);
        bufferElement.calibrateImageInputDimensions();
        //bufferElement.calculateVerticalGrid();
        BoxMapping mapping = new BoxMapping(bufferElement,sorting);

        TopCards expectedTopCard = new TopCards();
        //Piles
        try {
            Card card = new Card(createSuit("s"),5);
            Card card1 = new Card(createSuit("h"),2);
            Card card2 = new Card(createSuit("h"),8);
            Card card3 = new Card(createSuit("c"),2);
            Card card4 = new Card(createSuit("c"),5);
            Card card5 = new Card(createSuit("c"),9);
            Card card6 = new Card(createSuit("c"),8);
            Card[] piles = new Card[7];
            piles[0]= card;
            piles[1]= card1;
            piles[2]= card2;
            piles[3]= card3;
            piles[4]= card4;
            piles[5]= card5;
            piles[6]= card6;

            //Draw
            Card draw= new Card(createSuit("c"),11);

            //Foundation
            Card[] foundation = new Card[4];
            for (int i = 0; i<foundation.length;i++){
                foundation[i]=null;
            }

            expectedTopCard.setDrawnCard(draw);
            expectedTopCard.setPiles(piles);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TopCards actualTopCard = new TopCards();
        actualTopCard = mapping.mappingToTopCard(actualTopCard);


        //Comparing the two topCards
        assertEquals(expectedTopCard.getDrawnCard().getRank(),actualTopCard.getDrawnCard().getRank());
        assertEquals(expectedTopCard.getDrawnCard().getColor(),actualTopCard.getDrawnCard().getColor());


        for(int i = 0; i<expectedTopCard.getFoundations().length;i++){
            try {
                assertEquals(expectedTopCard.getFoundations()[i].getRank(), actualTopCard.getFoundations()[i].getRank());
                assertEquals(expectedTopCard.getFoundations()[i].getColor(), actualTopCard.getFoundations()[i].getColor());
            }catch (NullPointerException e){
                System.out.println("Is foundation a nulpointer? at index : "+i);
            }
        }


        for(int i = 0; i<expectedTopCard.getPiles().length;i++){
            assertEquals(expectedTopCard.getPiles()[i].getRank(),actualTopCard.getPiles()[i].getRank());
            assertEquals(expectedTopCard.getPiles()[i].getColor(),actualTopCard.getPiles()[i].getColor());
        }

    }


    @Test
    public void mappingToTopCard_Test_missing_first_Second_and_lastPile_pile_in_mitterRow() throws Exception {
        SortingHelperClass sorting = new SortingHelperClass();
        Darknet_Stub darknetReturnList = new Darknet_Stub();
        List<JsonDTO> callibrationImage = darknetReturnList.init_Stup_Cards();
        List<JsonDTO> expectedPrecardList = darknetReturnList.missing_first_Second_and_lastPile_pile_in_mitterRow();


        BufferElement bufferElement = new BufferElement(callibrationImage,sorting);
        bufferElement.calibrateImageInputDimensions();
       // bufferElement.calculateVerticalGrid();
        BoxMapping mapping = new BoxMapping(bufferElement,sorting);

        //Avoding the callibrationsstate
        mapping.setNumberOfAnalysedPic(0);
        bufferElement.setNewUpperAndLowerRow(expectedPrecardList);

        TopCards expectedTopCard = new TopCards();
        //Piles
        try {
            //Card card = new Card(createSuit("s"),5);
            Card card1 = new Card(createSuit("h"),2);
            Card card2 = new Card(createSuit("h"),8);
            //Card card3 = new Card(createSuit("c"),2);
            Card card4 = new Card(createSuit("c"),5);
            Card card5 = new Card(createSuit("c"),9);
            //Card card6 = new Card(createSuit("c"),8);
            Card[] piles = new Card[7];
            piles[0]= null;
            piles[1]= card1;
            piles[2]= card2;
            piles[3]= null;
            piles[4]= card4;
            piles[5]= card5;
            piles[6]= null;

            //Draw
            Card draw= new Card(createSuit("c"),11);

            //Foundation
            Card[] foundation = new Card[4];
            for (int i = 0; i<foundation.length;i++){
                foundation[i]=null;
            }

            expectedTopCard.setDrawnCard(draw);
            expectedTopCard.setPiles(piles);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TopCards actualTopCard = new TopCards();
        actualTopCard = mapping.mappingToTopCard(actualTopCard);


        //Comparing the two topCards
        assertEquals(expectedTopCard.getDrawnCard().getRank(),actualTopCard.getDrawnCard().getRank());
        assertEquals(expectedTopCard.getDrawnCard().getColor(),actualTopCard.getDrawnCard().getColor());


        for(int i = 0; i<expectedTopCard.getFoundations().length;i++){
            try {
                assertEquals(expectedTopCard.getFoundations()[i].getRank(), actualTopCard.getFoundations()[i].getRank());
                assertEquals(expectedTopCard.getFoundations()[i].getColor(), actualTopCard.getFoundations()[i].getColor());
            }catch (NullPointerException e){
                System.out.println("Is foundation a nulpointer? at index : "+i);
            }
        }

        assertEquals(actualTopCard.getPiles()[3],null);
        System.out.println("Zero first element" );
        assertEquals(actualTopCard.getPiles()[0],null);
        System.out.println("Zero therd element" );
        assertEquals(actualTopCard.getPiles()[6],null);
        System.out.println("Zero sixth element" );
        for(int i = 0; i<expectedTopCard.getPiles().length;i++){
            try {
            assertEquals(expectedTopCard.getPiles()[i].getRank(),actualTopCard.getPiles()[i].getRank());
            assertEquals(expectedTopCard.getPiles()[i].getColor(),actualTopCard.getPiles()[i].getColor());
            }catch (NullPointerException e){
                System.out.println("Is one of the piles a nulpointer? at index : "+i);
            }
        }

    }


    @Test
    public void mappingToTopCard_Test_missing_one_pile_in_mitterRow() throws Exception {
        SortingHelperClass sorting = new SortingHelperClass();
        Darknet_Stub darknetReturnList = new Darknet_Stub();
        List<JsonDTO> callibrationImage = darknetReturnList.init_Stup_Cards();
        List<JsonDTO> expectedPrecardList = darknetReturnList.init_Stup_Cards_missing_one_in_mitter_pile_row();


        BufferElement bufferElement = new BufferElement(callibrationImage,sorting);
        bufferElement.calibrateImageInputDimensions();
        //bufferElement.calculateVerticalGrid();
        BoxMapping mapping = new BoxMapping(bufferElement,sorting);

        //Avoding the callibrationsstate
        mapping.setNumberOfAnalysedPic(0);
        bufferElement.setNewUpperAndLowerRow(expectedPrecardList);

        TopCards expectedTopCard = new TopCards();
        //Piles
        try {
            Card card = new Card(createSuit("s"),5);
            Card card1 = new Card(createSuit("h"),2);
            Card card2 = new Card(createSuit("h"),8);
            //Card card3 = new Card(createSuit("c"),2);
            Card card4 = new Card(createSuit("c"),5);
            Card card5 = new Card(createSuit("c"),9);
            Card card6 = new Card(createSuit("c"),8);
            Card[] piles = new Card[7];
            piles[0]= card;
            piles[1]= card1;
            piles[2]= card2;
            piles[3]= null;
            piles[4]= card4;
            piles[5]= card5;
            piles[6]= card6;

            //Draw
            Card draw= new Card(createSuit("c"),11);

            //Foundation
            Card[] foundation = new Card[4];
            for (int i = 0; i<foundation.length;i++){
                foundation[i]=null;
            }

            expectedTopCard.setDrawnCard(draw);
            expectedTopCard.setPiles(piles);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TopCards actualTopCard = new TopCards();
        actualTopCard = mapping.mappingToTopCard(actualTopCard);


        //Comparing the two topCards
        assertEquals(expectedTopCard.getDrawnCard().getRank(),actualTopCard.getDrawnCard().getRank());
        assertEquals(expectedTopCard.getDrawnCard().getColor(),actualTopCard.getDrawnCard().getColor());


        for(int i = 0; i<expectedTopCard.getFoundations().length;i++){
            try {
                assertEquals(expectedTopCard.getFoundations()[i].getRank(), actualTopCard.getFoundations()[i].getRank());
                assertEquals(expectedTopCard.getFoundations()[i].getColor(), actualTopCard.getFoundations()[i].getColor());
            }catch (NullPointerException e){
                System.out.println("Is foundation a nulpointer? at index : "+i);
            }
        }

        assertEquals(actualTopCard.getPiles()[3],null);
        System.out.println("Zero therd element" );

        for(int i = 0; i<expectedTopCard.getPiles().length;i++){
            try {
                assertEquals(expectedTopCard.getPiles()[i].getRank(),actualTopCard.getPiles()[i].getRank());
                assertEquals(expectedTopCard.getPiles()[i].getColor(),actualTopCard.getPiles()[i].getColor());
            }catch (NullPointerException e){
                System.out.println("Is one of the piles a nulpointer? at index : "+i);
            }
        }

    }


    @Test
    public void overlappingCalibration_Test() throws Exception {
        SortingHelperClass sorting = new SortingHelperClass();
        I_Connection darknetReturnList = new Darknet_Stub();
        BufferElement buffer = new BufferElement(sorting);
        BoxMapping mapper = new BoxMapping(sorting);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("C:\\Uddannelse\\DTU\\4sem\\CDIO\\Kabale_V4\\ComputerVision\\src\\main\\resources\\export.png"));

            Image inputToDarknet = convertToFxImage(img);

            List<JsonDTO> outPutList = darknetReturnList.Get_Image_Information(inputToDarknet);

            //buffer.setCallibrationInputList(outPutList);
            //buffer.calibrateImageInputDimensions();

            TopCards actualTopCard = mapper.makeBoxMapping(outPutList);


            TopCards expectedTopCards = new TopCards();
            Card drawCard = new Card(createSuit("s"),9);
            Card pile1 = new Card(createSuit("s"),13);
            Card pile2 = new Card(createSuit("d"),5);
            Card pile3 = new Card(createSuit("h"),8);
            Card pile4 = new Card(createSuit("s"),1);
            Card pile5 = new Card(createSuit("c"),11);
            Card pile6 = new Card(createSuit("h"),1);
            Card pile7 = new Card(createSuit("d"),1);

            Card[] pile = new Card[7];
            pile[0] = pile1;
            pile[1] = pile2;
            pile[2] = pile3;
            pile[3] = pile4;
            pile[4] = pile5;
            pile[5] = pile6;
            pile[6] = pile7;

            Card[] foundationPiles = new Card[4];
            foundationPiles[0] = null;
            foundationPiles[1] = null;
            foundationPiles[2] = null;
            foundationPiles[3] = null;

            expectedTopCards.setFoundations(foundationPiles);
            expectedTopCards.setDrawnCard(drawCard);
            expectedTopCards.setPiles(pile);

            System.out.println("Actual TopCard");
            System.out.println(actualTopCard.toString()+"\n");
            System.out.println("Expected TopCard");
            System.out.println(expectedTopCards.toString()+"\n");

            for(int i = 0; i<expectedTopCards.getPiles().length;i++){
                try {
                    assertEquals(expectedTopCards.getPiles()[i].getRank(),actualTopCard.getPiles()[i].getRank());
                    assertEquals(expectedTopCards.getPiles()[i].getColor(),actualTopCard.getPiles()[i].getColor());
                }catch (NullPointerException e){
                    System.out.println("Is one of the piles a nulpointer? at index : "+i);
                }
            }


        } catch (IOException e) {
        }

    }


    private static Card.Suit createSuit(String suite){

        switch (suite){
            case "h": return Card.Suit.HEART;
            case "d": return Card.Suit.DIAMOND;
            case "s": return Card.Suit.SPADE;
            case "c": return Card.Suit.CLUB;

        }
        return null;
    }
}
