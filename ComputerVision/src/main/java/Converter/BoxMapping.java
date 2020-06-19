package Converter;

import Converter.Util.Sorting.I_Sorting;
import Converter.Util.Util;
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
    private static int getNumberOfAnalysedImage = 0;
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
        getNumberOfAnalysedImage++;

        TopCards topcard = new TopCards();
        if (getNumberOfAnalysedImage == 1) {

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
        getNumberOfAnalysedImage--;
        throw e;
    }catch (Exception e){
        throw new BoxMappingException(e.getMessage());
    }

    }


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

        if(getNumberOfAnalysedImage==1){
            if(topcards.getPiles().length!=7 || topcards.getDrawnCard()==null){
                throw new BoxMappingException("The calibrating could not be identified\n" +
                        "Please try to adjust the piles.\n " +
                        "Keep in mind that the Computer Vision will be able to detect on your dect of cards");
            }
        }

        return topcards;

    }



    public JsonDTO[] mappingLowerRow(){
        List<JsonDTO> lowerRowList = sorting.sortingTheListAccordingToX(bufferElement.getLowerRow());
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

    private double calculateHit(double elementX, double rowAverageValue){
        double value = elementX-rowAverageValue;
        if(value<0){
            return value*(-1);
        }
        return value;
    }


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
             highX = rowList.get(i).getX();
             lowY = rowList.get(i).getX();
            color = rowList.get(i).getCat();
            for (j = i; j < rowList.size(); j++) {

                //Finding the highest X value of the same type of card
                if (rowList.get(i).getCat().equals(rowList.get(j).getCat())) {
                    if (rowList.get(j).getX() >= highX) {
                        highX = rowList.get(j).getX();
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


    public JsonDTO[] mappingUpperRow(){
        List<JsonDTO> singleElementRow = averageXCoordinates(bufferElement.getUpperRow());
        JsonDTO[] upperRow = new JsonDTO[singleElementRow.size()];

        for(int i = 0; i<upperRow.length;i++){
            upperRow[i] = singleElementRow.get(i);
        }
        return upperRow;
    }




    private Card[] convertListToArray(ArrayList<Card> cardList){
       Card[] cardArray = new Card[cardList.size()];
        for(int i = 0; i<cardList.size();i++){
            cardArray[i]=cardList.get(i);
        }
        return cardArray;


    }

}
