import Data.JsonDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import kong.unirest.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MappingJSON_Test {

    @Test
    public void mappingJsonArrayToObject(){
        //Test json array (opening image)
        String jsonString = "[{'x': 568.4954223632812, 'y': 421.9874267578125, 'w': 31.311742782592773, 'h': 58.897151947021484, 'cat': '4h', 'score': 0.9980122447013855}, {'x': 1725.1982421875, 'y': 365.4192810058594, 'w': 27.48609161376953, 'h': 55.04191207885742, 'cat': '2d', 'score': 0.9954978227615356}, {'x': 632.512451171875, 'y': 549.294677734375, 'w': 30.37803077697754, 'h': 61.26737594604492, 'cat': '7h', 'score': 0.9953638315200806}, {'x': 870.717041015625, 'y': 548.5819702148438, 'w': 28.73849868774414, 'h': 57.012367248535156, 'cat': '3h', 'score': 0.9944019913673401}, {'x': 781.98486328125, 'y': 770.3113403320312, 'w': 30.67120361328125, 'h': 65.33380889892578, 'cat': '7h', 'score': 0.993342399597168}, {'x': 419.8759765625, 'y': 204.46665954589844, 'w': 27.380325317382812, 'h': 61.8360595703125, 'cat': '4h', 'score': 0.9931385517120361}, {'x': 1575.063232421875, 'y': 148.5637664794922, 'w': 29.37227439880371, 'h': 55.59343338012695, 'cat': '2d', 'score': 0.9917445182800293}, {'x': 1257.0567626953125, 'y': 755.1366577148438, 'w': 26.83867835998535, 'h': 57.186439514160156, 'cat': '9s', 'score': 0.9903607964515686}, {'x': 1020.4981689453125, 'y': 766.133544921875, 'w': 29.990882873535156, 'h': 61.30168533325195, 'cat': '3h', 'score': 0.9888191819190979}, {'x': 1588.3197021484375, 'y': 534.6492919921875, 'w': 29.638446807861328, 'h': 56.859710693359375, 'cat': '3s', 'score': 0.9788123965263367}, {'x': 549.95166015625, 'y': 765.8501586914062, 'w': 26.069135665893555, 'h': 65.36333465576172, 'cat': 'Qs', 'score': 0.9774652123451233}, {'x': 1347.9598388671875, 'y': 542.3919067382812, 'w': 25.583314895629883, 'h': 62.20513153076172, 'cat': '5s', 'score': 0.9748940467834473}, {'x': 1508.39111328125, 'y': 757.8221435546875, 'w': 27.653419494628906, 'h': 69.83187103271484, 'cat': '5s', 'score': 0.9724005460739136}, {'x': 1102.026611328125, 'y': 540.7755737304688, 'w': 25.35171127319336, 'h': 54.35371017456055, 'cat': '9s', 'score': 0.9653339982032776}, {'x': 400.993408203125, 'y': 551.0549926757812, 'w': 23.68934440612793, 'h': 57.677574157714844, 'cat': 'Qs', 'score': 0.9616369009017944}, {'x': 164.66079711914062, 'y': 543.0469970703125, 'w': 28.911296844482422, 'h': 61.0533447265625, 'cat': 'Qh', 'score': 0.9598925113677979}, {'x': 1744.4132080078125, 'y': 752.6278076171875, 'w': 29.895742416381836, 'h': 64.60011291503906, 'cat': '3s', 'score': 0.9350876808166504}, {'x': 308.1356201171875, 'y': 760.7088012695312, 'w': 26.45454978942871, 'h': 58.509033203125, 'cat': 'Qh', 'score': 0.9156994223594666}, {'x': 1476.8355712890625, 'y': 154.28506469726562, 'w': 26.958837509155273, 'h': 52.085269927978516, 'cat': 'Ac', 'score': 0.5981754660606384}, {'x': 1325.9444580078125, 'y': 160.67523193359375, 'w': 29.04208755493164, 'h': 62.84800720214844, 'cat': 'Ac', 'score': 0.5756915211677551}, {'x': 1330.6275634765625, 'y': 377.03778076171875, 'w': 28.016014099121094, 'h': 57.630645751953125, 'cat': 'Ac', 'score': 0.5076291561126709}]";

        JSONArray jsonArray = new JSONArray(jsonString);
        System.out.println(jsonArray.toString());
        try {
            // JSON array

            // convert JSON array to Java List
            List<JsonDTO> jsonDTOList = new ObjectMapper().readValue(jsonArray.toString(), new TypeReference<List<JsonDTO>>() {});

            // print list of users
            for(int i = 0; i<jsonDTOList.size();i++){
                System.out.println(jsonDTOList.get(i).getCat());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}