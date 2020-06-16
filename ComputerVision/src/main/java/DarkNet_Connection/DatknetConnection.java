package DarkNet_Connection;

import Data.JsonDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.json.JSONArray;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;


/**
 * * @author Andreas B.G. Jensen
 */
public class DatknetConnection implements I_Connection {

    /*
    Sending a post request to python server and return the coordinates of the image recognition.
     */
    @Override
    public List<JsonDTO> Get_Image_Information(Image img) throws UnirestException {
        try {
            byte[] imageByteArray = convertImageToByteArray(img);
            HttpResponse<String> res = Unirest.post("http://127.0.0.1:5000/detect/hello.jpg")
                    .header("accept", "application/json")
                    .header("Content-Type", "image/jpg")
                    .body(imageByteArray)
                    .asString();

            System.out.println(res.getBody());


            JSONArray jsonArray = new JSONArray(res.getBody());
            System.out.println(jsonArray.toString());

                // JSON array

                // convert JSON array to Java List
                List<JsonDTO> jsonDTOList = new ObjectMapper().readValue(jsonArray.toString(), new TypeReference<List<JsonDTO>>() {
                });

                return jsonDTOList;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    Author: Andreas Jensen
    Converts an JavaFX Image to a bytearray.
     */
    public byte[] convertImageToByteArray(Image img) throws IOException {
        BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);

        // Remove alpha-channel from buffered image:
        //This is nessesary because there apparently is a bug in ImageIO liabary
        //This codesnippet is from: https://stackoverflow.com/questions/4386446/issue-using-imageio-write-jpg-file-pink-background
        BufferedImage imageRGB = new BufferedImage(bImage.getWidth(), bImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < bImage.getWidth(); x++) {
            for (int y = 0; y < bImage.getHeight(); y++) {
                imageRGB.setRGB(x, y, bImage.getRGB(x, y));
            }
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.out.println(os.toByteArray().length);
        ImageIO.write(imageRGB, "jpg", os);// Passing: ​(RenderedImage im, String formatName, OutputStream output)
        return os.toByteArray();
    }
}