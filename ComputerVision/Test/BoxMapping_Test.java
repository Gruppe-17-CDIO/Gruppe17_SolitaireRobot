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
import static org.junit.Assert.*;


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
    }


    }

    @Test
    public void mappingUpperRow_Test(){

        SortingHelperClass sorting = new SortingHelperClass();
        Darknet_Stub darknetReturnList = new Darknet_Stub();
        List<JsonDTO> expectedPrecardList = darknetReturnList.init_Stup_Cards();


        BufferElement bufferElement = new BufferElement(expectedPrecardList,sorting);
        bufferElement.devideElementsBetweenUpperAndLowerRow();
        bufferElement.calculateVerticalGrid();
        BoxMapping mapping = new BoxMapping(bufferElement);

        JsonDTO[] mappedJson = mapping.mappingUpperRow();

        //Setting the expectet outcome
        JsonDTO obj = new JsonDTO();
        obj.setCat("Jc");
        JsonDTO[] expectedMappingLowerRow = new JsonDTO[1];
        expectedMappingLowerRow[0] = obj;

        for(int i = 0; i<mappedJson.length;i++) {
            assertEquals(expectedMappingLowerRow[i].getCat(),mappedJson[i].getCat());
        }
    }
}
