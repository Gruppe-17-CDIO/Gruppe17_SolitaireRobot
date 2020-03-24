package view;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * @author Rasmus Sander Larsen
 */
public class FxUtil {

    private static final int textSize = 12;
    private static final int headerSize = 18;
    private static final int spacing = 10;
    private static final int padding = 25;

    /*
    -------------------------- Fields --------------------------
     */
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    /*
    ------------------------ Properties -------------------------
     */

    // region Properties

    public static int getTextSize() {
        return textSize;
    }

    public static int getHeaderSize() {
        return headerSize;
    }

    public static int getSpacing() {
        return spacing;
    }

    public static int getPadding() {
        return padding;
    }

    // endregion
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    public static Text textDefault(String textString) {
        Text text = new Text(textString);
        text.setFont(textFont());
        return text;
    }

    public static Text headerTextDefault(String textString) {
        Text text = new Text(textString);
        text.setFont(headerFont());
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
    }

    public static Font textFont() {
        return fontDefault(textSize);
    }
    public static Font headerFont() {
        return fontDefault(headerSize);
    }

    public static Font fontDefault(int size) {
        return Font.font("Tahoma", FontWeight.NORMAL, size);
    }

    public static HBox hBox (boolean centered) {
        HBox hBox = new HBox(spacing);
        if (centered)
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    public static VBox vBox (boolean centered) {
        VBox vBox = new VBox(spacing);
        if (centered)
            vBox.setAlignment(Pos.CENTER);

        return vBox;
    }
}
