package Data;

import Converter.Util.SortingHelperClass;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class BufferElement {
    SortingHelperClass sortingObject;
    List<JsonDTO> calubrationList;
    List<JsonDTO> upperRow = new ArrayList<>();
    List<JsonDTO> lowerRow = new ArrayList<>();
    double separationLine = 0;

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

    public List<JsonDTO> getUpperRow(){
        return upperRow;
    }

    public List<JsonDTO> getLowerRow(){
        return lowerRow;
    }

    public double getSeparationLine(){
        return separationLine;
    }

}
