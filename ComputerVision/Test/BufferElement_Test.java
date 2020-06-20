import Converter.Util.Sorting.SortingHelperClass;
import DarkNet_Connection.Darknet_Stub;
import Data.BufferElement;
import Data.JsonDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Andreas B.G. Jensen
 */
public class BufferElement_Test {


    /**
     * @auther Andreas B.G. Jensen
     * Testing that an image will be divided into two rows
     * An upper row where the draw cards will be placed and a lower row where all the piles will be placed
     */

    @Test
    public void CreateSeparationLine_Test(){
        SortingHelperClass sorting = new SortingHelperClass();
        Darknet_Stub darknetReturnList = new Darknet_Stub();
        List<JsonDTO> expectedPrecardList = darknetReturnList.init_Stup_Cards();


        BufferElement bufferElement = new BufferElement(expectedPrecardList,sorting);
        bufferElement.calibrateImageInputDimensions();

        List<JsonDTO> expectdUpperRowElements = new ArrayList<>();
        JsonDTO upperElement = new JsonDTO();
        upperElement.setCat("Jc");
        JsonDTO upperElement2 = new JsonDTO();
        upperElement2.setCat("Jc");
        expectdUpperRowElements.add(upperElement);
        expectdUpperRowElements.add(upperElement2);

        //removing upper elements from lower row
        List<JsonDTO> expectdLowerRowElements = expectedPrecardList;
        for(int i = 0; i<expectdUpperRowElements.size();i++){
            for (int j = 0; j<expectdLowerRowElements.size();j++){
                if(expectdLowerRowElements.get(j).getCat().equals(expectdUpperRowElements.get(i).getCat())){
                    expectdLowerRowElements.remove(j);
                }
            }
        }

        //Getting upperRow and compare it to expectedUpperRow
        List<JsonDTO> upperRow = bufferElement.getUpperRow();

        assertEquals(upperRow.size(),expectdUpperRowElements.size());

        for (int i = 0; i<upperRow.size();i++){
            assertEquals(upperRow.get(i).getCat(),expectdUpperRowElements.get(i).getCat());
        }


        List<JsonDTO> lowerRow = bufferElement.getLowerRow();

        //Sorting actual and expected lower row
        lowerRow = sorting.sortingTheListAccordingToY(lowerRow);
        expectdLowerRowElements = sorting.sortingTheListAccordingToY(expectdLowerRowElements);
        for (int i = 0; i<upperRow.size();i++){
            assertEquals(lowerRow.get(i),expectdLowerRowElements.get(i));
        }
    }


    @Test
    public void calculateBufferY_Test(){
        SortingHelperClass sorting = new SortingHelperClass();
        Darknet_Stub darknetReturnList = new Darknet_Stub();
        List<JsonDTO> expectedPrecardList = darknetReturnList.init_Stup_Cards();


        BufferElement bufferElement = new BufferElement(expectedPrecardList,sorting);
        bufferElement.calibrateImageInputDimensions();
        bufferElement.calculateBufferY();

        List<JsonDTO> upperRow = bufferElement.getUpperRow();
        List<JsonDTO> lowerRow = bufferElement.getLowerRow();

        double devidingLine = bufferElement.getSeparationLine();
        for(int i = 0; i<upperRow.size();i++){
            if(upperRow.get(i).getY()<devidingLine){
                assert(true);
            }else {
                assert (false);
            }
        }

        for(int i = 0; i<lowerRow.size(); i++){
            if(lowerRow.get(i).getY()>devidingLine){
                assert(true);
            }else {
                assert (false);
            }
        }
    }

    @Test
    public void calculateVerticalGrid_Test(){
        SortingHelperClass sorting = new SortingHelperClass();
        Darknet_Stub darknetReturnList = new Darknet_Stub();
        List<JsonDTO> expectedPrecardList = darknetReturnList.init_Stup_Cards();


        BufferElement bufferElement = new BufferElement(expectedPrecardList,sorting);
        bufferElement.calibrateImageInputDimensions();
        //bufferElement.calculateVerticalGrid();

        HashMap<Integer, Double> verticalGrid = bufferElement.getRowFixedGridLines();



        for (Map.Entry<Integer,Double> entry : verticalGrid.entrySet())
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());

        assert (true);
    }

}
