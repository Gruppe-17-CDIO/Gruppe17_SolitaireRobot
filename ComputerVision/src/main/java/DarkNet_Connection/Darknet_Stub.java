package DarkNet_Connection;

import Data.JsonDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.scene.image.Image;
import kong.unirest.json.JSONArray;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * * @author Andreas B.G. Jensen
 */
public class Darknet_Stub implements I_Connection {


    final static int DRAW = 1;
    final static int ROW = 7;
    final static int FOUNDATION = 4;
    final static int BOX_HEIGHT = 100;
    final static int BOX_WIDTH = 50;
    // Koordinat af bounding boxes
    // Højde og bredda af bounding boxes
    // Procentsats for hvad hvilket kort den tror det er
    // Klassificering af, hvilket kort det er.

    @Override
    public List<JsonDTO> Get_Image_Information(Image img) {

        //return init_Stup_Cards(); //Used for all other tests
        //return missing_Darknet_input_when_Callibrating(); //Only used for testing Throwing_Exception_Test
        return overLappingImageForCardCallibration_Test();
    }

    public List<JsonDTO> missing_Darknet_input_when_Callibrating() {
       String jsonString = "[{\"x\": 475.3180847167969, \"y\": 497.787109375, \"w\": 25.06568145751953, \"h\": 49.03480529785156, \"cat\": \"5s\", \"score\":\n" +
                "0.9942284226417542}, {\"x\": 1274.840576171875, \"y\": 893.518798828125, \"w\": 26.601520538330078, \"h\": 45.375030517578125,\n" +
                "\"cat\": \"9c\", \"score\": 0.9922652244567871}, {\"x\": 612.0394287109375, \"y\": 547.72705078125, \"w\": 22.258716583251953, \"h\":\n" +
                "39.20811462402344, \"cat\": \"2h\", \"score\": 0.9921427369117737}, {\"x\": 710.2655029296875, \"y\":\n" +
                "681.357666015625, \"w\": 20.94573402404785, \"h\": 43.64979553222656, \"cat\": \"2h\", \"score\": 0.9877640604972839}, {\"x\":\n" +
                "1177.9715576171875, \"y\": 744.0900268554688, \"w\": 23.771923065185547, \"h\": 46.77185821533203, \"cat\": \"9c\", \"score\":\n" +
                "0.9838494658470154}, {\"x\": 565.234619140625, \"y\": 634.8668212890625, \"w\": 24.85626792907715, \"h\": 52.1278076171875,\n" +
                "\"cat\": \"5s\", \"score\": 0.9831768870353699}, {\"x\": 1040.630859375, \"y\": 667.0514526367188, \"w\": 23.078596115112305, \"h\":\n" +
                "44.036834716796875, \"cat\": \"5c\", \"score\": 0.9823360443115234}, {\"x\": 989.600830078125, \"y\": 769.746337890625, \"w\":\n" +
                "19.854719161987305, \"h\": 44.0780143737793, \"cat\": \"2c\", \"score\": 0.9791268110275269}, {\"x\":\n" +
                "1123.384765625, \"y\": 819.838134765625, \"w\": 24.567583084106445, \"h\": 47.41136932373047, \"cat\": \"5c\", \"score\":\n" +
                "0.9749412536621094}, {\"x\": 760.6384887695312, \"y\": 729.334716796875, \"w\": 25.5239315032959, \"h\": 39.172794342041016,\n" +
                "\"cat\": \"8h\", \"score\": 0.8651166558265686},  {\"x\": 855.621826171875, \"y\": 587.8221435546875, \"w\":\n" +
                "20.876346588134766, \"h\": 46.429908752441406, \"cat\": \"8h\", \"score\": 0.6907708048820496}, {\"x\": 1442.623046875, \"y\":\n" +
                "792.1492919921875, \"w\": 21.523893356323242, \"h\": 42.75442123413086, \"cat\": \"8c\", \"score\": 0.543028712272644}, {\"x\":\n" +
                "1444.564453125, \"y\": 938.439697265625, \"w\": 25.660446166992188, \"h\": 47.30094909667969, \"cat\": \"8c\", \"score\":\n" +
                "0.5105763673782349}]";


        return mappingDarknetOutput(jsonString);
    }

    public List<JsonDTO> empty_darknet_calibration_output(){
       /* String jsonString = "[{\"x\": 475.3180847167969, \"y\": 497.787109375, \"w\": 25.06568145751953, \"h\": 49.03480529785156, \"cat\": \"5s\", \"score\":\n" +
                "0.9942284226417542}, {\"x\": 1274.840576171875, \"y\": 893.518798828125, \"w\": 26.601520538330078, \"h\": 45.375030517578125,\n" +
                "\"cat\": \"9c\", \"score\": 0.9922652244567871}, {\"x\": 612.0394287109375, \"y\": 547.72705078125, \"w\": 22.258716583251953, \"h\":\n" +
                "39.20811462402344, \"cat\": \"2h\", \"score\": 0.9921427369117737}, {\"x\": 710.2655029296875, \"y\":\n" +
                "681.357666015625, \"w\": 20.94573402404785, \"h\": 43.64979553222656, \"cat\": \"2h\", \"score\": 0.9877640604972839}, {\"x\":\n" +
                "1177.9715576171875, \"y\": 744.0900268554688, \"w\": 23.771923065185547, \"h\": 46.77185821533203, \"cat\": \"9c\", \"score\":\n" +
                "0.9838494658470154}, {\"x\": 565.234619140625, \"y\": 634.8668212890625, \"w\": 24.85626792907715, \"h\": 52.1278076171875,\n" +
                "\"cat\": \"5s\", \"score\": 0.9831768870353699}, {\"x\": 1040.630859375, \"y\": 667.0514526367188, \"w\": 23.078596115112305, \"h\":\n" +
                "44.036834716796875, \"cat\": \"5c\", \"score\": 0.9823360443115234}, {\"x\": 989.600830078125, \"y\": 769.746337890625, \"w\":\n" +
                "19.854719161987305, \"h\": 44.0780143737793, \"cat\": \"2c\", \"score\": 0.9791268110275269}, {\"x\":\n" +
                "1123.384765625, \"y\": 819.838134765625, \"w\": 24.567583084106445, \"h\": 47.41136932373047, \"cat\": \"5c\", \"score\":\n" +
                "0.9749412536621094}, {\"x\": 760.6384887695312, \"y\": 729.334716796875, \"w\": 25.5239315032959, \"h\": 39.172794342041016,\n" +
                "\"cat\": \"8h\", \"score\": 0.8651166558265686},  {\"x\": 855.621826171875, \"y\": 587.8221435546875, \"w\":\n" +
                "20.876346588134766, \"h\": 46.429908752441406, \"cat\": \"8h\", \"score\": 0.6907708048820496}, {\"x\": 1442.623046875, \"y\":\n" +
                "792.1492919921875, \"w\": 21.523893356323242, \"h\": 42.75442123413086, \"cat\": \"8c\", \"score\": 0.543028712272644}, {\"x\":\n" +
                "1444.564453125, \"y\": 938.439697265625, \"w\": 25.660446166992188, \"h\": 47.30094909667969, \"cat\": \"8c\", \"score\":\n" +
                "0.5105763673782349}]";*/
       String emptyJsonString = "[]";

        return mappingDarknetOutput(emptyJsonString);


    }

    public List<JsonDTO> init_Stup_Cards(){
        String jsonString = "[{\"x\": 475.3180847167969, \"y\": 497.787109375, \"w\": 25.06568145751953, \"h\": 49.03480529785156, \"cat\": \"5s\", \"score\":\n" +
                "0.9942284226417542}, {\"x\": 1274.840576171875, \"y\": 893.518798828125, \"w\": 26.601520538330078, \"h\": 45.375030517578125,\n" +
                "\"cat\": \"9c\", \"score\": 0.9922652244567871}, {\"x\": 612.0394287109375, \"y\": 547.72705078125, \"w\": 22.258716583251953, \"h\":\n" +
                "39.20811462402344, \"cat\": \"2h\", \"score\": 0.9921427369117737}, {\"x\": 710.2655029296875, \"y\":\n" +
                "681.357666015625, \"w\": 20.94573402404785, \"h\": 43.64979553222656, \"cat\": \"2h\", \"score\": 0.9877640604972839}, {\"x\":\n" +
                "1177.9715576171875, \"y\": 744.0900268554688, \"w\": 23.771923065185547, \"h\": 46.77185821533203, \"cat\": \"9c\", \"score\":\n" +
                "0.9838494658470154}, {\"x\": 565.234619140625, \"y\": 634.8668212890625, \"w\": 24.85626792907715, \"h\": 52.1278076171875,\n" +
                "\"cat\": \"5s\", \"score\": 0.9831768870353699}, {\"x\": 1040.630859375, \"y\": 667.0514526367188, \"w\": 23.078596115112305, \"h\":\n" +
                "44.036834716796875, \"cat\": \"5c\", \"score\": 0.9823360443115234}, {\"x\": 989.600830078125, \"y\": 769.746337890625, \"w\":\n" +
                "19.854719161987305, \"h\": 44.0780143737793, \"cat\": \"2c\", \"score\": 0.9791268110275269}, {\"x\": 620.2969360351562, \"y\":\n" +
                "224.7909698486328, \"w\": 22.64548683166504, \"h\": 44.59173583984375, \"cat\": \"Jc\", \"score\": 0.9752715826034546}, {\"x\":\n" +
                "1123.384765625, \"y\": 819.838134765625, \"w\": 24.567583084106445, \"h\": 47.41136932373047, \"cat\": \"5c\", \"score\":\n" +
                "0.9749412536621094}, {\"x\": 760.6384887695312, \"y\": 729.334716796875, \"w\": 25.5239315032959, \"h\": 39.172794342041016,\n" +
                "\"cat\": \"8h\", \"score\": 0.8651166558265686}, {\"x\": 711.1718139648438, \"y\": 352.4980163574219, \"w\": 21.16898536682129, \"h\":\n" +
                "44.60894775390625, \"cat\": \"Jc\", \"score\": 0.8584449887275696}, {\"x\": 855.621826171875, \"y\": 587.8221435546875, \"w\":\n" +
                "20.876346588134766, \"h\": 46.429908752441406, \"cat\": \"8h\", \"score\": 0.6907708048820496}, {\"x\": 1442.623046875, \"y\":\n" +
                "792.1492919921875, \"w\": 21.523893356323242, \"h\": 42.75442123413086, \"cat\": \"8c\", \"score\": 0.543028712272644}, {\"x\":\n" +
                "1444.564453125, \"y\": 938.439697265625, \"w\": 25.660446166992188, \"h\": 47.30094909667969, \"cat\": \"8c\", \"score\":\n" +
                "0.5105763673782349}]";

        return mappingDarknetOutput(jsonString);

    }

    public List<JsonDTO> missing_first_Second_and_lastPile_pile_in_mitterRow(){
        String jsonString = "[{\"x\": 1274.840576171875, \"y\": 893.518798828125, \"w\": 26.601520538330078, \"h\": 45.375030517578125,\n" +
                "\"cat\": \"9c\", \"score\": 0.9922652244567871}, {\"x\": 612.0394287109375, \"y\": 547.72705078125, \"w\": 22.258716583251953, \"h\":\n" +
                "39.20811462402344, \"cat\": \"2h\", \"score\": 0.9921427369117737}, {\"x\": 710.2655029296875, \"y\":\n" +
                "681.357666015625, \"w\": 20.94573402404785, \"h\": 43.64979553222656, \"cat\": \"2h\", \"score\": 0.9877640604972839}, {\"x\":\n" +
                "1177.9715576171875, \"y\": 744.0900268554688, \"w\": 23.771923065185547, \"h\": 46.77185821533203, \"cat\": \"9c\", \"score\":\n" +
                "0.9838494658470154}, {\"x\": 1040.630859375, \"y\": 667.0514526367188, \"w\": 23.078596115112305, \"h\":\n" +
                "44.036834716796875, \"cat\": \"5c\", \"score\": 0.9823360443115234}, {\"x\": 620.2969360351562, \"y\":\n" +
                "224.7909698486328, \"w\": 22.64548683166504, \"h\": 44.59173583984375, \"cat\": \"Jc\", \"score\": 0.9752715826034546}, {\"x\":\n" +
                "1123.384765625, \"y\": 819.838134765625, \"w\": 24.567583084106445, \"h\": 47.41136932373047, \"cat\": \"5c\", \"score\":\n" +
                "0.9749412536621094}, {\"x\": 760.6384887695312, \"y\": 729.334716796875, \"w\": 25.5239315032959, \"h\": 39.172794342041016,\n" +
                "\"cat\": \"8h\", \"score\": 0.8651166558265686}, {\"x\": 711.1718139648438, \"y\": 352.4980163574219, \"w\": 21.16898536682129, \"h\":\n" +
                "44.60894775390625, \"cat\": \"Jc\", \"score\": 0.8584449887275696}, {\"x\": 855.621826171875, \"y\": 587.8221435546875, \"w\":\n" +
                "20.876346588134766, \"h\": 46.429908752441406, \"cat\": \"8h\", \"score\": 0.6907708048820496}]";


            return mappingDarknetOutput(jsonString);

    }

    public List<JsonDTO> init_Stup_Cards_missing_one_in_mitter_pile_row(){
        String jsonString = "[{\"x\": 475.3180847167969, \"y\": 497.787109375, \"w\": 25.06568145751953, \"h\": 49.03480529785156, \"cat\": \"5s\", \"score\":\n" +
                "0.9942284226417542}, {\"x\": 1274.840576171875, \"y\": 893.518798828125, \"w\": 26.601520538330078, \"h\": 45.375030517578125,\n" +
                "\"cat\": \"9c\", \"score\": 0.9922652244567871}, {\"x\": 612.0394287109375, \"y\": 547.72705078125, \"w\": 22.258716583251953, \"h\":\n" +
                "39.20811462402344, \"cat\": \"2h\", \"score\": 0.9921427369117737}, {\"x\": 710.2655029296875, \"y\":\n" +
                "681.357666015625, \"w\": 20.94573402404785, \"h\": 43.64979553222656, \"cat\": \"2h\", \"score\": 0.9877640604972839}, {\"x\":\n" +
                "1177.9715576171875, \"y\": 744.0900268554688, \"w\": 23.771923065185547, \"h\": 46.77185821533203, \"cat\": \"9c\", \"score\":\n" +
                "0.9838494658470154}, {\"x\": 565.234619140625, \"y\": 634.8668212890625, \"w\": 24.85626792907715, \"h\": 52.1278076171875,\n" +
                "\"cat\": \"5s\", \"score\": 0.9831768870353699}, {\"x\": 1040.630859375, \"y\": 667.0514526367188, \"w\": 23.078596115112305, \"h\":\n" +
                "44.036834716796875, \"cat\": \"5c\", \"score\": 0.9823360443115234}, {\"x\": 620.2969360351562, \"y\":\n" +
                "224.7909698486328, \"w\": 22.64548683166504, \"h\": 44.59173583984375, \"cat\": \"Jc\", \"score\": 0.9752715826034546}, {\"x\":\n" +
                "1123.384765625, \"y\": 819.838134765625, \"w\": 24.567583084106445, \"h\": 47.41136932373047, \"cat\": \"5c\", \"score\":\n" +
                "0.9749412536621094}, {\"x\": 760.6384887695312, \"y\": 729.334716796875, \"w\": 25.5239315032959, \"h\": 39.172794342041016,\n" +
                "\"cat\": \"8h\", \"score\": 0.8651166558265686}, {\"x\": 711.1718139648438, \"y\": 352.4980163574219, \"w\": 21.16898536682129, \"h\":\n" +
                "44.60894775390625, \"cat\": \"Jc\", \"score\": 0.8584449887275696}, {\"x\": 855.621826171875, \"y\": 587.8221435546875, \"w\":\n" +
                "20.876346588134766, \"h\": 46.429908752441406, \"cat\": \"8h\", \"score\": 0.6907708048820496}, {\"x\": 1442.623046875, \"y\":\n" +
                "792.1492919921875, \"w\": 21.523893356323242, \"h\": 42.75442123413086, \"cat\": \"8c\", \"score\": 0.543028712272644}, {\"x\":\n" +
                "1444.564453125, \"y\": 938.439697265625, \"w\": 25.660446166992188, \"h\": 47.30094909667969, \"cat\": \"8c\", \"score\":\n" +
                "0.5105763673782349}]";



        return mappingDarknetOutput(jsonString);

    }


    public List<JsonDTO> overLappingImageForCardCallibration_Test(){
        String jsonString = "[{\"cat\":\"Jc\",\"x\":1373.8367919921875,\"score\":0.981383740901947,\"h\":63.29669189453125,\"y\":857.7057495117188,\"w\":36.73591995239258},{\"cat\":\"5d\",\"x\":651.3854370117188,\"score\":0.9699491262435913,\"h\":63.38581466674805,\"y\":878.5902709960938,\"w\":28.884891510009766},{\"cat\":\"9s\",\"x\":652.3521118164062,\"score\":0.9591683149337769,\"h\":60.370479583740234,\"y\":429.8507080078125,\"w\":32.76305389404297},{\"cat\":\"Ks\",\"x\":447.66522216796875,\"score\":0.9533581137657166,\"h\":65.38304138183594,\"y\":578.3977661132812,\"w\":33.01930618286133},{\"cat\":\"5d\",\"x\":526.8185424804688,\"score\":0.9448500871658325,\"h\":64.8874740600586,\"y\":667.9891357421875,\"w\":28.537879943847656},{\"cat\":\"9s\",\"x\":482.52215576171875,\"score\":0.9288012385368347,\"h\":60.80265426635742,\"y\":246.7541961669922,\"w\":30.416576385498047},{\"cat\":\"Jc\",\"x\":1269.4066162109375,\"score\":0.8957274556159973,\"h\":70.11991882324219,\"y\":646.2429809570312,\"w\":34.36474609375},{\"cat\":\"As\",\"x\":867.3316650390625,\"score\":0.8168576955795288,\"h\":67.3375015258789,\"y\":920.565185546875,\"w\":24.623249053955078},{\"cat\":\"Ad\",\"x\":1804.564208984375,\"score\":0.813459575176239,\"h\":62.12516784667969,\"y\":652.6934204101562,\"w\":25.897571563720703},{\"cat\":\"Ad\",\"x\":1794.7869873046875,\"score\":0.7775792479515076,\"h\":64.08301544189453,\"y\":845.8009643554688,\"w\":25.061243057250977},{\"cat\":\"Ad\",\"x\":1676.05810546875,\"score\":0.7286835312843323,\"h\":61.01034164428711,\"y\":650.0695190429688,\"w\":24.97748374938965},{\"cat\":\"8h\",\"x\":795.2725219726562,\"score\":0.6503865122795105,\"h\":59.70887756347656,\"y\":632.7188720703125,\"w\":26.757585525512695},{\"cat\":\"Ah\",\"x\":1451.8651123046875,\"score\":0.560504138469696,\"h\":67.1929931640625,\"y\":654.706298828125,\"w\":26.728595733642578},{\"cat\":\"8h\",\"x\":934.5382690429688,\"score\":0.5380405187606812,\"h\":58.450950622558594,\"y\":640.1661376953125,\"w\":29.445180892944336},{\"cat\":\"As\",\"x\":1006.13916015625,\"score\":0.5067340135574341,\"h\":62.41029739379883,\"y\":917.966552734375,\"w\":29.109004974365234}]";
        return mappingDarknetOutput(jsonString);
    }


    private List<JsonDTO> mappingDarknetOutput(String outputString){
        JSONArray jsonArray = new JSONArray(outputString);
        System.out.println(jsonArray.toString());
        try {

            // convert JSON array to Java List
            List<JsonDTO> jsonDTOList = new ObjectMapper().readValue(jsonArray.toString(), new TypeReference<List<JsonDTO>>() {});


            return jsonDTOList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<JsonDTO> setDefaultDarknetOutput(String darknetOutput){
        String jsonString = darknetOutput;
        return mappingDarknetOutput(jsonString);
    }


   /* private JsonArray init_Stup_Cards(){

        int fistRowCarPositionX;
        int rowXStride;

        int fistRowCarPositionY;
        int rowYStride;
        JsonArray returnJson = new JsonArray();

        fistRowCarPositionX = 30;
        rowXStride = 0;
        fistRowCarPositionY = 26;
        rowYStride = 0;
        //Add draw
        for (int i = 1; i<=DRAW;i++){

            JsonObject draw = new JsonObject();
            draw.addProperty("upperKoordinate_X",i*rowXStride+fistRowCarPositionX);
            draw.addProperty("upperKoordinate_Y",i*rowYStride+fistRowCarPositionY);
            draw.addProperty("lowerKoordinate_X",i*rowXStride+fistRowCarPositionX+BOX_WIDTH);
            draw.addProperty("lowerKoordinate_Y",i*rowYStride+fistRowCarPositionY+BOX_HEIGHT);
            draw.addProperty("Classification",classes.getClasssification());
            returnJson.add(draw);
        }

        fistRowCarPositionX = 180;
        rowXStride = 120;
        fistRowCarPositionY = 26;
        rowYStride = 0;
        //Adding foundation
        for (int i = 1; i<=FOUNDATION;i++){
            JsonObject foundation = new JsonObject();
            foundation.addProperty("upperKoordinate_X",i*rowXStride+fistRowCarPositionX);
            foundation.addProperty("upperKoordinate_Y",i*rowYStride+fistRowCarPositionY);
            foundation.addProperty("lowerKoordinate_X",i*rowXStride+fistRowCarPositionX+BOX_WIDTH);
            foundation.addProperty("lowerKoordinate_Y",i*rowYStride+fistRowCarPositionY+BOX_HEIGHT);
            foundation.addProperty("Classification",classes.getClasssification());
            returnJson.add(foundation);
        }

        //Add row
        fistRowCarPositionX = 30;
        rowXStride = 120;
        fistRowCarPositionY = 250;
        rowYStride = 15;

        for (int i = 0; i<ROW;i++){

            JsonObject row = new JsonObject();
            row.addProperty("upperKoordinate_X",i*rowXStride+fistRowCarPositionX);
            row.addProperty("upperKoordinate_Y",i*rowYStride+fistRowCarPositionY);
            row.addProperty("lowerKoordinate_X",i*rowXStride+fistRowCarPositionX+BOX_WIDTH);
            row.addProperty("lowerKoordinate_Y",i*rowYStride+fistRowCarPositionY+BOX_HEIGHT);
            row.addProperty("Classification",classes.getClasssification());
            returnJson.add(row);
        }
    return returnJson;
    }



    private class Classification{

        ArrayList<String> classes = new ArrayList<>();
        ArrayList<String> Left_Cards = new ArrayList<>();

        Classification(){
            init();
        }

        public String getClasssification(){
            int rnd = new Random().nextInt(classes.size());
            return classes.remove(rnd);
        }

        private void init(){

            //Generating Aces
            for(int i = 1; i<14;i++){
                String harts = "H"+i;
                String Spades = "S"+i;
                String Klover = "K"+i;
                String Dimonds = "D"+i;
                classes.add(harts);
                classes.add(Spades);
                classes.add(Klover);
                classes.add(Dimonds);
            }
/*
            classes.add("H_J");
            classes.add("S_J");
            classes.add("K_J");
            classes.add("D1_J");

            //Addign queens
            classes.add("H_Q");
            classes.add("S_Q");
            classes.add("K_Q");
            classes.add("D1_Q");

            //Adding kings
            classes.add("H_K");
            classes.add("S_K");
            classes.add("K_K");
            classes.add("D1_K");


        }

    }*/

}
