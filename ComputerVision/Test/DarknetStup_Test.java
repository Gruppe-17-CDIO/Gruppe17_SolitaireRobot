import Converter.Util.Util;
import DarkNet_Connection.Darknet_Stub;
import DarkNet_Connection.I_Connection;
import Data.PreCard;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.List;

public class DarknetStup_Test {
    static Util utility = new Util();
    static I_Connection connection = new Darknet_Stub();


    @Test
    public void Test_Stub(){
        Image img = null;
        JsonArray array = null;
        try {
            array = connection.Get_Image_Information(img);

        System.out.println(array.toString());
        System.out.println(array.get(0).toString());
        JsonObject element = array.get(0).getAsJsonObject();
        System.out.println(element.get("upperKoordinate_X"));
        List<PreCard> list =  utility.getPreCard(array);
        } catch (UnirestException e) {
            e.printStackTrace();
        }



        System.out.println();


    }
}
