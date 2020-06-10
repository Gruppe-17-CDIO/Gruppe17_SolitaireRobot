package DarkNet_Connection;

import com.google.gson.JsonArray;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.image.Image;

public interface I_Connection {

    JsonArray Get_Image_Information(Image img) throws UnirestException;

}
