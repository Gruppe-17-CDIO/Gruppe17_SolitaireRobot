package Converter;

import Converter.Util.SortingHelperClass;
import Data.BufferElement;
import Data.JsonDTO;
import Data.PreCard;
import dataObjects.Card;
import dataObjects.TopCards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoxMapping {
    private SortingHelperClass sorting = new SortingHelperClass();
    private static List<JsonDTO> currentPreCardList;
    private static int getNumberOfAnalysedImage = 0;
    private BufferElement bufferElement;

    public BoxMapping(BufferElement bufferElement) {
        this.bufferElement = bufferElement;
    }

    public TopCards makeBoxMapping(List<JsonDTO> preCardList, TopCards topCards) throws Exception {

        getNumberOfAnalysedImage++;
        currentPreCardList = preCardList;



        if(getNumberOfAnalysedImage==1){
            bufferElement = new BufferElement(currentPreCardList,sorting);
            bufferElement.devideElementsBetweenUpperAndLowerRow();
            bufferElement.calculateBufferY();
            bufferElement.getRowFixedGridLines();


            //Card draw = new Card(createSuit(smallestY.get(0)),smallestY.get(0).getRank());





        }
        //TODO: Make a lower boundary of the upper row




return null;
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


    public JsonDTO[] mappingLowerRow(){
        List<JsonDTO> lowerRowList = bufferElement.getLowerRow();
        HashMap<Integer, Double> rowGrow = bufferElement.getRowFixedGridLines();
        lowerRowList = sorting.sortingTheListOfPrecardsAccordingToX(averageXCoordinates(lowerRowList));
        JsonDTO[] cardList = new JsonDTO[7];
        int closestMatchRow =0;
        double newCloser = calculateHit(lowerRowList.get(0).getX(),rowGrow.get(closestMatchRow));;

        double closer = newCloser;

        for(int i = 0; i<lowerRowList.size();i++){
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

                }
            }JsonDTO obj = new JsonDTO();
            obj.setX(bufferElement.calculateAverageX(lowX,highX));
            obj.setY(lowY);
            obj.setCat(color);
            actualNumberOfElementsList.add(obj);
            i = j;



        }



        return actualNumberOfElementsList;
    }


    public JsonDTO[] mappingUpperRow(){
        List<JsonDTO> singleElementRow = averageXCoordinates(bufferElement.getUpperRow());
        singleElementRow = sorting.sortingTheListOfPrecardsAccordingToX(singleElementRow);

        JsonDTO[] upperRow = new JsonDTO[singleElementRow.size()];

        for(int i = 0; i<upperRow.length;i++){
            upperRow[i] = singleElementRow.get(i);
        }

        return upperRow;
    }


}
