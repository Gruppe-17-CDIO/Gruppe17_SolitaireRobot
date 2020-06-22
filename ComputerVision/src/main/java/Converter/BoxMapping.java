package Converter;

import Converter.Util.SortingHelperClass;
import Converter.Util.Util;
import Data.BufferElement;
import Data.JsonDTO;
import dataObjects.Card;
import dataObjects.TopCards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoxMapping {
    private final SortingHelperClass sorting = new SortingHelperClass();
    private static List<JsonDTO> currentPreCardList;
    private static int getNumberOfAnalysedImage = 0;
    private BufferElement bufferElement;

    public BoxMapping(BufferElement bufferElement) {
        this.bufferElement = bufferElement;
    }

    public TopCards makeBoxMapping(List<JsonDTO> preCardList) throws Exception {

        getNumberOfAnalysedImage++;
        currentPreCardList = preCardList;

        TopCards topcard = new TopCards();
        if(getNumberOfAnalysedImage==1) {
            bufferElement = new BufferElement(currentPreCardList, sorting);
            bufferElement.devideElementsBetweenUpperAndLowerRow();
            bufferElement.calculateBufferY();
            bufferElement.getDrawCardSeparationLine();
            bufferElement.calculateVerticalGrid();

            return mappingToTopCard(topcard);
        }else{
            bufferElement.setNewUpperAndLowerRow(preCardList);
            return mappingToTopCard(topcard);
        }

    }


    public JsonDTO[] mappingLowerRow(){
        List<JsonDTO> lowerRowList = sorting.sortingTheListOfPrecardsAccordingToX(bufferElement.getLowerRow());
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

        return topcards;

    }


    private Card[] convertListToArray(ArrayList<Card> cardList){
       Card[] cardArray = new Card[cardList.size()];
        for(int i = 0; i<cardList.size();i++){
            cardArray[i]=cardList.get(i);
        }
        return cardArray;


    }

}
