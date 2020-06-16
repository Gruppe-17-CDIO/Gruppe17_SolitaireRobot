package DarkNet_Connection;

import Data.JsonDTO;
import com.google.gson.JsonArray;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.image.Image;

import java.util.List;

/**
 * * @author Andreas B.G. Jensen
 */
public interface I_Connection {

    List<JsonDTO> Get_Image_Information(Image img) throws UnirestException;

}
