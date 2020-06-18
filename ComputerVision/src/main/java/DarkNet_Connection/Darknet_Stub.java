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
        return init_Stup_Cards();
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

        JSONArray jsonArray = new JSONArray(jsonString);
        System.out.println(jsonArray.toString());
        try {
            // JSON array

            // convert JSON array to Java List
            List<JsonDTO> jsonDTOList = new ObjectMapper().readValue(jsonArray.toString(), new TypeReference<List<JsonDTO>>() {});

            // print list of JsonDTO objects
          /*  for(int i = 0; i<jsonDTOList.size();i++){
                System.out.println(jsonDTOList.get(i).getCat());
            }*/

            return jsonDTOList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;

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

        JSONArray jsonArray = new JSONArray(jsonString);
        System.out.println(jsonArray.toString());
        try {
            // JSON array

            // convert JSON array to Java List
            List<JsonDTO> jsonDTOList = new ObjectMapper().readValue(jsonArray.toString(), new TypeReference<List<JsonDTO>>() {});

            // print list of JsonDTO objects
          /*  for(int i = 0; i<jsonDTOList.size();i++){
                System.out.println(jsonDTOList.get(i).getCat());
            }*/

            return jsonDTOList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;

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

        JSONArray jsonArray = new JSONArray(jsonString);
        System.out.println(jsonArray.toString());
        try {
            // JSON array

            // convert JSON array to Java List
            List<JsonDTO> jsonDTOList = new ObjectMapper().readValue(jsonArray.toString(), new TypeReference<List<JsonDTO>>() {});

            // print list of JsonDTO objects
          /*  for(int i = 0; i<jsonDTOList.size();i++){
                System.out.println(jsonDTOList.get(i).getCat());
            }*/

            return jsonDTOList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;

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
