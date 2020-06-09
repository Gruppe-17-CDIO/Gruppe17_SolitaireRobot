package DarkNet_Connection;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;


public class Darknet_Stub implements I_Connection {

    Classification classes = new Classification();
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
    public JsonArray Get_Image_Information(Image img) {
        return init_Stup_Cards();
    }



    private JsonArray init_Stup_Cards(){

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
*/

        }

    }

}
