package view.components;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.MainGUI;
import view.components.webCamManipulationButton.ManipulationStateCallback;

import java.io.File;

/**
 * @author Rasmus Sander Larsen
 */
public class WebCamManiMenuItem extends MenuItem {

    //-------------------------- Fields --------------------------

    private ManipulationStateCallback callback;

    private final String DEFAULT_BUTTON_TEXT = "Select webcam image";
    private final String REMOVE_BUTTON_TEXT = "Remove webcam image";
    private final String DEFAULT_SELECTED_TEXT = "Selected file: ";

    private File selectedFile;
    private boolean isWebCamManipulated = false;

    private FileChooser fileChooser;

    //----------------------- Constructor -------------------------

    public WebCamManiMenuItem(String text) {
        super(text);
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

    public void setManipulationState(boolean isWebCamManipulated) {
        if (isWebCamManipulated) {
            setText(REMOVE_BUTTON_TEXT);
        } else {
            setText(DEFAULT_BUTTON_TEXT);
        }

        this.isWebCamManipulated = isWebCamManipulated;
    }

    public Image imageOfFile() {
        return new Image(selectedFile.toURI().toString());
    }

    //---------------------- Support Methods ----------------------    

    private void applySettings () {
        setText(DEFAULT_BUTTON_TEXT);
        callback = new ManipulationStateCallback() {
            @Override
            public void startManipulateAction() {

            }

            @Override
            public void stopManipulateAction() {

            }
        };
        setOnAction(event -> {
            if (isWebCamManipulated) {
                setManipulationState(false);
                callback.stopManipulateAction();
            } else {
                browseForImageFile();
            }
        });
    }

    private void browseForImageFile () {
        fileChooser = new FileChooser();
        // Starts the filechooser from the directory of the lastly selected file.
        if (selectedFile != null) {
            fileChooser.setInitialDirectory(folderOfFile(selectedFile));
        } else {
            fileChooser.setInitialDirectory(null);
        }
        // The file selected in the filechooser is loaded into "selectedFile".
        selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            MainGUI.printToOutputAreaNewline(DEFAULT_SELECTED_TEXT + "\n" + selectedFile.getAbsolutePath());
            isWebCamManipulated = true;

            setText(REMOVE_BUTTON_TEXT);
            // Runs the manipulation method
            callback.startManipulateAction();
        } else {
            MainGUI.printToOutputAreaNewline(DEFAULT_SELECTED_TEXT + "\nNothing");
            isWebCamManipulated = false;

            setText(DEFAULT_BUTTON_TEXT);
            // Runs the no manipulation method
            callback.stopManipulateAction();
        }
    }

    private File folderOfFile (File file) {
        String filePath = file.getAbsolutePath();
        int lastBackSlash = filePath.lastIndexOf("\\");
        String folder = filePath.substring(0,lastBackSlash);
        return new File(folder);
    }

}
