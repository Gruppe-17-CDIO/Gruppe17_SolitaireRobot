package computerVision.Converter;

import computerVision.Converter.Util.Sorting.SortingHelperClass;
import DarkNet_Connection.Darknet_Stub;
import DarkNet_Connection.DarknetConnection;
import DarkNet_Connection.I_Connection;
import Data.JsonDTO;

import Exceptions.ComputerVisionException;
import Exceptions.DarknetConnectionException;
import computerVision.I_ComputerVisionController;
import dataObjects.TopCards;
import javafx.scene.image.Image;

import java.util.*;

/**
 * * @author Andreas B.G. Jensen
 */
public class Convertion implements I_ComputerVisionController {

    boolean test = true;
    SortingHelperClass sorting;
    I_Connection connection;
    BoxMapping mapper;

    public Convertion(){
        if(test){
            connection = new Darknet_Stub();
        }
        else{
            connection = new DarknetConnection();
        }
        sorting = new SortingHelperClass();
        mapper = new BoxMapping(sorting);

    }



//TODO: Implement so that this method
    @Override
    public TopCards getSolitaireCards(Image img) throws ComputerVisionException {

        try {
            List<JsonDTO> returnImages = getOutputDarknet(img);
           // buffer = new BufferElement(returnImages,sorting);
            //mapper = new BoxMapping(buffer,sorting);
            //return boxCreator.boxMapping(returnImages,boxesArea,img);
            return mapper.makeBoxMapping(returnImages);

        }catch (Exception e){
            e.printStackTrace();
           throw new ComputerVisionException(e.getMessage());
        }
    }





    public List<JsonDTO> getOutputDarknet(Image img) throws DarknetConnectionException {
            return connection.Get_Image_Information(img);

    }






}
