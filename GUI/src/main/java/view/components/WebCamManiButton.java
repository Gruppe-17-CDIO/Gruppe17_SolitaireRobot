package view.components;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.MainGUI;

import java.io.File;
import java.util.PrimitiveIterator;

/**
 * @author Rasmus Sander Larsen
 */
public class WebCamManiButton extends Button {

    //-------------------------- Fields --------------------------

    private final String DEFAULT_BUTTON_TEXT = "Select webcam image";
    private final String REMOVE_BUTTON_TEXT = "Remove webcam image";
    private final String DEFAULT_SELECTED_TEXT = "Selected file: ";

    private File selectedFile;
    private boolean isWebCamManipulated = false;

    private FileChooser fileChooser;

    //----------------------- Constructor -------------------------

    public WebCamManiButton () {
        super();
        applySettings();
    }

    //------------------------ Properties -------------------------

    // region Properties

    public File getSelectedFile() {
        return selectedFile;
    }

    public boolean isWebCamManipulated() {
        return isWebCamManipulated;
    }

    // endregion

    //---------------------- Public Methods -----------------------

    public Image imageOfFile() {
        return new Image(selectedFile.toURI().toString());
    }

    //---------------------- Support Methods ----------------------    

    private void applySettings () {
        setText(DEFAULT_BUTTON_TEXT);
        setOnAction(event -> {
            if (isWebCamManipulated) {
                isWebCamManipulated = false;
            } else {
                browseForImageFile();
            }
        });
    }

    private void browseForImageFile () {
        fileChooser = new FileChooser();
        if (selectedFile != null) {
            fileChooser.setInitialDirectory(folderOfFile(selectedFile));
        } else {
            fileChooser.setInitialDirectory(null);
        }
        selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            MainGUI.printToOutputAreaNewline(DEFAULT_SELECTED_TEXT + "\n" + selectedFile.getAbsolutePath());
            isWebCamManipulated = true;

            setText(REMOVE_BUTTON_TEXT);
        } else {
            MainGUI.printToOutputAreaNewline(DEFAULT_SELECTED_TEXT + "\nNothing");
            isWebCamManipulated = false;

            setText(DEFAULT_BUTTON_TEXT);
        }
    }

    private File folderOfFile (File file) {
        String filePath = file.getAbsolutePath();
        int lastBackSlash = filePath.lastIndexOf("\\");
        String folder = filePath.substring(0,lastBackSlash);
        return new File(folder);
    }
}
