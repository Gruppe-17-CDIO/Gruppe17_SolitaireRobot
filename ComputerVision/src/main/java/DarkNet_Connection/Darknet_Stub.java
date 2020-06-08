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
    final static int BOX_HEIGHT = 10;
    final static int BOX_WIDTH = 10;
    // Koordinat af bounding boxes
    // Højde og bredda af bounding boxes
    // Procentsats for hvad hvilket kort den tror det er
    // Klassificering af, hvilket kort det er.

    @Override
    public JsonArray Get_Image_Information(Image img) {
        return init_Stup_Cards();
    }



    private JsonArray init_Stup_Cards(){
        JsonArray returnJson = new JsonArray();

        //Add draw
        for (int i = 0; i<DRAW;i++){

            JsonObject draw = new JsonObject();
            draw.addProperty("upperKoordinate_X",i);
            draw.addProperty("upperKoordinate_Y",i);
            draw.addProperty("lowerKoordinate_X",i+BOX_WIDTH);
            draw.addProperty("lowerKoordinate_Y",i-BOX_HEIGHT);
            draw.addProperty("Classification",classes.getClasssification());
            returnJson.add(draw);
        }
        //Adding foundation
        for (int i = 0; i<FOUNDATION;i++){
            JsonObject foundation = new JsonObject();
            foundation.addProperty("upperKoordinate_X",i+5);
            foundation.addProperty("upperKoordinate_Y",i+5);
            foundation.addProperty("lowerKoordinate_X",i+BOX_WIDTH);
            foundation.addProperty("lowerKoordinate_Y",i-BOX_HEIGHT);
            foundation.addProperty("Classification",classes.getClasssification());
            returnJson.add(foundation);
        }

        //Add row
        for (int i = 0; i<ROW;i++){

            JsonObject row = new JsonObject();
            row.addProperty("upperKoordinate_X",i);
            row.addProperty("upperKoordinate_Y",i-50);
            row.addProperty("lowerKoordinate_X",i+BOX_WIDTH);
            row.addProperty("lowerKoordinate_Y",i-BOX_HEIGHT);
            row.addProperty("Classification",classes.getClasssification());
            returnJson.add(row);
        }
    return returnJson;
    }



    private class Classification{

        ArrayList<String> classes = new ArrayList<>();
        ArrayList<String> Left_Cards = new ArrayList<>();

        public String getClasssification(){
            init();
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
