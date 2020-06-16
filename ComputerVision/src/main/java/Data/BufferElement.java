package Data;

import Converter.Util.SortingHelperClass;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BufferElement {
    SortingHelperClass sortingObject;
    List<JsonDTO> calubrationList;
    List<JsonDTO> upperRow = new ArrayList<>();
    List<JsonDTO> lowerRow = new ArrayList<>();
    double separationLine = 0;
    HashMap<Integer, Double> rowFixedGridLines;

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
    }






    public void calculateBufferY(){

        upperRow = sortingObject.sortingTheListOfPrecardsAccordingToY(upperRow);
        lowerRow = sortingObject.sortingTheListOfPrecardsAccordingToY(lowerRow);

        double highesYFromUppeRow =upperRow.get(upperRow.size()-1).getY();
        double lowestYFromLowerRow = lowerRow.get(0).getY();

        double buffer = (lowestYFromLowerRow-highesYFromUppeRow)/2;

        separationLine = highesYFromUppeRow+buffer;
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
        while(rowCounter<7) {
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
                        i=j;
                        break;
                    }

                }rowFixedGridLines.put(rowCounter,calculateAverageGrid(lowX,highX).doubleValue());
                rowCounter++;
            }
        }
        System.out.println();
    }


    public Double calculateAverageGrid(double lowX,double highX){
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




}
