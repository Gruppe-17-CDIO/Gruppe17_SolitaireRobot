package Converter;

import Converter.Util.Util;
import DarkNet_Connection.Darknet_Stub;
import DarkNet_Connection.I_Connection;
import Data.PreCard;
import com.google.gson.JsonArray;
import dataObjects.Card;
import javafx.scene.image.Image;

import java.util.List;

public class Convertion implements I_ComputerVisionController {
    Util utility = new Util();
    I_Connection connection = new Darknet_Stub();
    Image img;




    @Override
    public Card[] getSolitaireCards(Image img) {

        this.img = img;

        return new Card[0];
    }





    private JsonArray ConvertImage(Image img){
        JsonArray returnArray = connection.Get_Image_Information(img);
        List<PreCard> preCardList = utility.getPreCard(returnArray);

        return null;

    }
}
