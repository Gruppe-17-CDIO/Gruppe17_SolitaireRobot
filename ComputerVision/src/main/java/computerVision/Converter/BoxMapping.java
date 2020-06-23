package computerVision.Converter;

import computerVision.Converter.Util.Sorting.I_Sorting;
import computerVision.Converter.Util.Util;
import Data.BufferElement;
import Data.JsonDTO;
import Exceptions.BoxMappingException;
import Exceptions.ComputerVisionException;
import dataObjects.Card;
import dataObjects.TopCards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoxMapping {
    private I_Sorting sorting;
    private static int numberOfAnalysedImage = 0;
    private BufferElement bufferElement;

    /**
     * @Author Andreas B.G. Jensen
     * Thsi constructor is used for testing
     * @param bufferElement
     * @param sortObject
     */
    public BoxMapping(BufferElement bufferElement, I_Sorting sortObject) {
        this.bufferElement = bufferElement;
        sorting = sortObject;
    }

    /**
     * @author Andreas B.G. Jensen
     */
    public BoxMapping(I_Sorting sortObject) {
        sorting = sortObject;
        bufferElement = new BufferElement(sorting);
    }


    /**
     * @author Andreas B.G. Jensen
     * Converts at  list of JsonDTO objects to Card elements and returns them in a TopCards object.
     * If the preCardList is the first inputlist it will be used for callibrating the image to the indput.
     * @param preCardList
     * @return
     * @throws ComputerVisionException
     */
    public TopCards makeBoxMapping(List<JsonDTO> preCardList) throws BoxMappingException {
    try {
        numberOfAnalysedImage++;

        TopCards topcard = new TopCards();
        if (numberOfAnalysedImage == 1) {

            bufferElement.setCallibrationInputList(preCardList);
            bufferElement.calibrateImageInputDimensions();
            return mappingToTopCard(topcard);

        } else {
            bufferElement.setNewUpperAndLowerRow(preCardList);
            return mappingToTopCard(topcard);
        }

    }catch (BoxMappingException e){
        e.getStackTrace();
        //decrement the
        numberOfAnalysedImage--;
        throw e;
    }catch (Exception e){
        throw new BoxMappingException(e.getMessage());
    }

    }

    /**
     * @author Andreas B.G. Jensen
     * Mapping the JsonDTO elements to TopCards elements which will be returned to the controller.
     * @param topcards
     * @return topcards
     * @throws ComputerVisionException
     */
    public TopCards mappingToTopCard(TopCards topcards) throws Exception {
        JsonDTO[] upperRow = mappingUpperRow();
        JsonDTO[] lowerRow = mappingLowerRow();
        ArrayList<Card> foundation = new ArrayList<>();

        //Finding drawCard
        for(int i = 0; i<upperRow.length;i++){
            if(upperRow[i].getX()<=bufferElement.getDrawCardSeparationLine()){
                Card drawCard = Util.convertToCard(upperRow[i]);
                topcards.setDrawnCard(drawCard);
            }else{
                foundation.add(Util.convertToCard(upperRow[i]));
            }
        }
        //Finding doundation
        Card[] foundationArray = new Card[4];
        for(int i = 0; i<foundationArray.length;i++){
            try {
                foundationArray[i] = foundation.get(i);
            }catch (IndexOutOfBoundsException e){
                //The purpus is to get empty index in the foundations, is there is no card.
                foundationArray[i] = null;
            }finally{
                topcards.setFoundations(foundationArray);
            }
        }

        Card[] piles = new Card[lowerRow.length];
        //Mapping pile row
        for (int i = 0; i< lowerRow.length;i++){
            if(lowerRow[i]==null){
                piles[i]=null;
            }else{
                piles[i]=Util.convertToCard(lowerRow[i]);
            }
        }

        topcards.setPiles(piles);

        if(numberOfAnalysedImage ==1){
            if(!validateCalibration(topcards)){
                throw new BoxMappingException("The calibrating could not be identified\n" +
                        "Please try to adjust the piles.\n " +
                        "Keep in mind that the Computer Vision will be able to detect on your dect of cards");
            }
        }

        System.out.println(topcards.toString());
        return topcards;

    }

    /**
     * @authos Andreas B.G. Jensen
     * Validating the output from the calibration. The calibration is required to have 7 card elements in the lowerRow
     * and on draw card. If the calibration cannot be validated it will return false else it will return true.
     * @param topcard
     * @return boolean
     */
    private boolean validateCalibration(TopCards topcard){
        //Checking piles
        for(int i =0;i<topcard.getPiles().length;i++){
            if(topcard.getPiles()[i]==null){
                return false;
            }
        }
        //Checking draw card
        if(topcard.getDrawnCard()==null){
            return false;
        }
        return true;
    }


    /**
     * @authos Andreas B.G. Jensen
     * Mapping the incomming output from darknet to the calibration gridlines.
     * If no element exist the element index will be set to null.
     * The mapping works by comparing the X-coordinates of the cardelements by the x-coordinate of the callibration.
     * The cardelement will be assigned to the pile in wich the difference in the X-coordinate will be smallest.
     * The cardelement will be compared with its avereage X-coordinates.
     * @return cardList
     */
    public JsonDTO[] mappingLowerRow(){
        List<JsonDTO> lowerRowList = sorting.sortingListAccordingToX(bufferElement.getLowerRow());
        HashMap<Integer, Double> rowGrow = bufferElement.getRowFixedGridLines();
        lowerRowList = averageXCoordinates(lowerRowList);
        JsonDTO[] cardList = new JsonDTO[7];
        int closestMatchRow =0;

        double closer;

        for(int i = 0; i<lowerRowList.size();i++){
            //Value is just a largenumper
            closer = 200000000.0;
            for (Map.Entry<Integer,Double> entry : rowGrow.entrySet())
                if(calculateHit(lowerRowList.get(i).getX(),entry.getValue())<=closer){
                        closestMatchRow = entry.getKey().intValue();
                        closer = calculateHit(lowerRowList.get(i).getX(),entry.getValue());
                    }
            cardList[closestMatchRow] = lowerRowList.get(i);
        }

        return cardList;

    }

    /**
     * @author Andreas B.G. Jensen
     * Ensures that the distanse is always positive
     * @param elementX
     * @param rowAverageValue
     * @return
     */
    private double calculateHit(double elementX, double rowAverageValue){
        double value = elementX-rowAverageValue;
        if(value<0){
            return value*(-1);
        }
        return value;
    }


    /**
     * @author Andreas B.G. Jensen
     * Calculates the average X-koordinate for each card.
     * The averageing X-coordinate will be calculatet from the lowest X-value to the highest X-value of the same cardtype.
     * NB: Remember that the darknet output can give more coordinates for the same type of card element.
     * @param rowList
     * @return actualNumberOfElementsList
     */
    private List<JsonDTO> averageXCoordinates(List<JsonDTO> rowList) {
        List<JsonDTO> actualNumberOfElementsList = new ArrayList<>();
        double lowX = 0;
        double highX = 0;
        double lowY= 0;
        String color = "";

        int i = 0;
        int j = i;
        for (i = 0; i < rowList.size(); i++) {
             lowX = rowList.get(i).getX();
             highX = rowList.get(i).getX()+rowList.get(i).getW();
             lowY = rowList.get(i).getX();
            color = rowList.get(i).getCat();
            for (j = i; j < rowList.size(); j++) {

                //Finding the highest X value of the same type of card
                if (rowList.get(i).getCat().equals(rowList.get(j).getCat())) {
                    if (rowList.get(j).getX() >= highX) {
                        highX = rowList.get(j).getX()+rowList.get(i).getW();
                        if (rowList.get(j).getY() > lowY) {
                            lowY = rowList.get(j).getY();
                        }
                    }
                }else{
                    break;
                }
            }JsonDTO obj = new JsonDTO();
            obj.setX(bufferElement.calculateAverageX(lowX,highX));
            obj.setY(lowY);
            obj.setCat(color);
            actualNumberOfElementsList.add(obj);
            i = j-1;
        }
        return actualNumberOfElementsList;
    }


    /**
     * @author Andreas B.G. Jensen
     * @return upperRow
     */
    public JsonDTO[] mappingUpperRow(){
        List<JsonDTO> singleElementRow = averageXCoordinates(bufferElement.getUpperRow());
        JsonDTO[] upperRow = new JsonDTO[singleElementRow.size()];

        for(int i = 0; i<upperRow.length;i++){
            upperRow[i] = singleElementRow.get(i);
        }
        return upperRow;
    }


    /**
     * @author Andreas B.G. Jensen
     * @param cardList
     * @return cardArray
     */
    private Card[] convertListToArray(ArrayList<Card> cardList){
       Card[] cardArray = new Card[cardList.size()];
        for(int i = 0; i<cardList.size();i++){
            cardArray[i]=cardList.get(i);
        }
        return cardArray;


    }

    /**
     * @author Andreas B.G. Jensen
     * This method is used only for testing.
     * Change the number of analysed pictures
     * @param analyseNumber
     */
    public void setNumberOfAnalysedPic(int analyseNumber){
        this.numberOfAnalysedImage = analyseNumber;
    }

}
