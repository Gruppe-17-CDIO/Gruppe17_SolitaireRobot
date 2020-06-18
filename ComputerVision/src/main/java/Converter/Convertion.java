package Converter;

import Converter.Util.Util;
import DarkNet_Connection.DatknetConnection;
import DarkNet_Connection.I_Connection;
import Data.PreCard;
import com.google.gson.JsonArray;
import com.mashape.unirest.http.exceptions.UnirestException;
import computerVision.I_ComputerVisionController;
import dataObjects.TopCards;
import javafx.scene.image.Image;

import java.util.List;

/**
 * * @author Andreas B.G. Jensen
 */
public class Convertion implements I_ComputerVisionController {
    Util utility = new Util();
    I_Connection connection = new DatknetConnection();
    ImageBoxes boxCreator = new ImageBoxes();
    Image img;



//TODO: Implement so that this method
    @Override
    public TopCards getSolitaireCards(Image img) {

        List<PreCard> returnImages = ConvertImage(img);
        List<double[]> boxesArea = boxCreator.calibrateImgBoxes(img);


        //return boxCreator.boxMapping(returnImages,boxesArea,img);
        return null;
    }





    public List<PreCard> ConvertImage(Image img){
        JsonArray returnArray = null;
        try {
            returnArray = connection.Get_Image_Information(img);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return utility.getPreCard(returnArray);

    }
}
