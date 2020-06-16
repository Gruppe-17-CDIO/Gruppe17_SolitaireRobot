package DarkNet_Connection;

import com.google.gson.JsonArray;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.image.Image;

/**
 * * @author Andreas B.G. Jensen
 */
public interface I_Connection {

    JsonArray Get_Image_Information(Image img) throws UnirestException;

}
