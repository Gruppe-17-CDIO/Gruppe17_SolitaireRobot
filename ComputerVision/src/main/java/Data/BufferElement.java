package Data;

import Converter.Util.SortingHelperClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BufferElement {
    SortingHelperClass sortingObject;
    List<JsonDTO> calubrationList;
    List<JsonDTO> upperRow = new ArrayList<>();
    List<JsonDTO> lowerRow = new ArrayList<>();
    static double separationLine = 0;
    static double drawCardSeparationLineX = 0;
    static HashMap<Integer, Double> rowFixedGridLines;

    public BufferElement(List<JsonDTO> calubrationList, SortingHelperClass sortingObject) {
        this.calubrationList = calubrationList;
        this.sortingObject = sortingObject;
    }


    public void devideElementsBetweenUpperAndLowerRow(){
        calubrationList = sortingObject.sortingTheListOfPrecardsAccordingToY(calubrationList);
       List<JsonDTO> upperElements = calubrationList;
       for(int i = 0; i<upperElements.size();i++){
           if(upperElements.get(i).getCat().equals(upperElements.get(0).getCat())){
               upperRow.add(upperElements.get(i));
           }
       }
        lowerRow = removeSameElementsInList(calubrationList,upperRow);
        setDrawCardSeparationLineX();
        calculateBufferY();
    }






    public void calculateBufferY(){

        upperRow = sortingObject.sortingTheListOfPrecardsAccordingToY(upperRow);
        lowerRow = sortingObject.sortingTheListOfPrecardsAccordingToY(lowerRow);

        double highesYFromUppeRow =upperRow.get(upperRow.size()-1).getY();
        double lowestYFromLowerRow = lowerRow.get(0).getY();

        double buffer = (lowestYFromLowerRow-highesYFromUppeRow)/2;

        separationLine = highesYFromUppeRow+buffer;
    }


    private void setDrawCardSeparationLineX(){
        List<JsonDTO> rowUpperRow = sortingObject.sortingTheListOfPrecardsAccordingToX(upperRow);
        drawCardSeparationLineX = rowUpperRow.get(rowUpperRow.size()-1).getX();
    }


    private List<JsonDTO> removeSameElementsInList(List<JsonDTO> listToRemoveFrom, List<JsonDTO> listToCompare){
        for(int i = 0; i<listToCompare.size();i++){
            if(listToRemoveFrom.contains(listToCompare.get(i))){
                listToRemoveFrom.remove(listToCompare.get(i));
            }
        }
        return listToRemoveFrom;
    }


    public void calculateVerticalGrid(){
         rowFixedGridLines = new HashMap<>();
        lowerRow = sortingObject.sortingTheListOfPrecardsAccordingToX(lowerRow);

        int rowCounter = 0;
        //while(rowCounter<7) {
            for (int i = 0; i < lowerRow.size(); i++) {
                double lowX = lowerRow.get(i).getX();
                double highX = lowerRow.get(i).getX();
                for (int j = i; j < lowerRow.size(); j++) {

                    //Finding the highest X value of the same type of card
                    if(lowerRow.get(i).getCat().equals(lowerRow.get(j).getCat())){
                        if(lowerRow.get(j).getX()>=highX){
                            highX = lowerRow.get(j).getX();
                        }
                    }else{
                        i=j-1;
                        break;
                    }

                }
                rowFixedGridLines.put(rowCounter, calculateAverageX(lowX,highX).doubleValue());
                rowCounter++;
            }

        //}
        System.out.println();
    }


    public Double calculateAverageX(double lowX, double highX){
        Double average = lowX+((highX-lowX)/2);
        return average;
    }

    public List<JsonDTO> getUpperRow(){
        return upperRow;
    }

    public List<JsonDTO> getLowerRow(){
        return lowerRow;
    }

    public double getSeparationLine(){
        return separationLine;
    }

    public HashMap<Integer, Double> getRowFixedGridLines(){
        return rowFixedGridLines;
    }

    public double getDrawCardSeparationLine(){
        return drawCardSeparationLineX;
    }

    public void setNewUpperAndLowerRow(List<JsonDTO> preCardList){
        preCardList = sortingObject.sortingTheListOfPrecardsAccordingToY(preCardList);
        List<JsonDTO> upperElements = preCardList;
        for(int i = 0; i<upperElements.size();i++){
            if(upperElements.get(i).getY()<separationLine){
                upperRow.add(upperElements.get(i));
            }
        }
        lowerRow = removeSameElementsInList(preCardList,upperRow);
    }

}
