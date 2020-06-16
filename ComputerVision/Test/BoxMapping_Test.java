import Converter.BoxMapping;
import Converter.Util.SortingHelperClass;
import DarkNet_Connection.Darknet_Stub;
import Data.BufferElement;
import Data.JsonDTO;
import com.sun.rowset.internal.Row;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BoxMapping_Test {

    @Test
    public void mappingLowerRow_Test(){
    SortingHelperClass sorting = new SortingHelperClass();
    Darknet_Stub darknetReturnList = new Darknet_Stub();
    List<JsonDTO> expectedPrecardList = darknetReturnList.init_Stup_Cards();


    BufferElement bufferElement = new BufferElement(expectedPrecardList,sorting);
        bufferElement.devideElementsBetweenUpperAndLowerRow();
        bufferElement.calculateVerticalGrid();
        BoxMapping mapping = new BoxMapping(bufferElement);
    HashMap<Integer, Double> verticalGrid = bufferElement.getRowFixedGridLines();

    JsonDTO[] mappedJson = mapping.mappingLowerRow(verticalGrid,bufferElement.getLowerRow());

    for(int i = 0; i<mappedJson.length;i++) {
        if(mappedJson[i]!=null) {
            System.out.println(mappedJson[i].getCat());
        }
    }


    }
}
