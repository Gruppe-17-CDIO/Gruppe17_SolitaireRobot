package view.taps;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import view.MainGUI;
import view.components.FxUtil;
import view.components.TabStd;
import view.components.WebCamImageView;
import view.components.WebCamManiButton;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Rasmus Sander Larsen
 */
public class CameraSourceTabNew extends TabStd {

    //-------------------------- Fields --------------------------

    private final String comboBoxPromptText = "Select webcam to use as source";

    private WebCamImageView imgWebCamCapturedImage;
    private boolean isWebCamSelected = false;

    private ComboBox<Webcam> webCamOptions;
    private FlowPane bottomCameraControlPane;
    private WebCamManiButton webCamManiBtn;
    private CheckBox cbWebCamMirroring;
    private Button btnCameraStop;
    private Button btnCameraStart;
    private Button btnCameraDispose;

    //----------------------- Constructor -------------------------
    public CameraSourceTabNew(){
        super("Source",
                "Select camera input source",
                "Selected the preferred camera source to record the game your are playing");
        addDropdown();
        addMirroringCheckBox();
        addVideoFrame();
        addBottomControlPane();
        addGetImageButton();
        testMode();
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------


    //---------------------- Support Methods ----------------------    

    private void addDropdown() {
        FlowPane topPane = new FlowPane();
        topPane.setAlignment(Pos.CENTER);
        topPane.setHgap(FxUtil.getSpacing());

        webCamOptions = new ComboBox<>(FXCollections.observableList(Webcam.getWebcams()));
        webCamOptions.setPromptText(comboBoxPromptText);
        webCamOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Webcam>() {
            @Override
            public void changed(ObservableValue<? extends Webcam> observable, Webcam oldValue, Webcam newValue) {
                if (newValue != null) {
                    if (MainGUI.isTesting){
                        MainGUI.printToOutputAreaNewline("Selected webcam: " + newValue.getName());
                    }
                    isWebCamSelected = true;

                    //newValue.setCustomViewSizes(new Dimension[]{new Dimension(1024, 768)});
                    //newValue.setViewSize(new Dimension(1024, 768));

                    imgWebCamCapturedImage.mirrorWebCamImage(true);
                    imgWebCamCapturedImage.setWebCam(newValue);

                } else {
                    isWebCamSelected = false;
                }
            }
        });

        topPane.getChildren().add(webCamOptions);
        webCamManiBtn = new WebCamManiButton(new WebCamManiButton.ManipulationStateCallback() {
            @Override
            public void doManipulateAction() {
                imgWebCamCapturedImage.startManipulationOfWebCam(webCamManiBtn.imageOfFile());
            }

            @Override
            public void dontManipulateAction() {
                imgWebCamCapturedImage.stopManipulationOfWebCam();
            }
        });
        if (MainGUI.isTesting) {
            topPane.getChildren().add(webCamManiBtn);
        }
        addToContent(topPane);
    }

    private void addMirroringCheckBox () {
        cbWebCamMirroring = new CheckBox("Mirror webcam image");
        cbWebCamMirroring.setAllowIndeterminate(false);
        cbWebCamMirroring.setSelected(true);
        cbWebCamMirroring.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                imgWebCamCapturedImage.mirrorWebCamImage(newValue);
            }
        });
        addToContent(cbWebCamMirroring);
    }

    private void addVideoFrame() {
        BorderPane webCamPane = new BorderPane();
        imgWebCamCapturedImage = new WebCamImageView();
        imgWebCamCapturedImage.setStateCallback(new WebCamImageView.WebCamStateCallback() {
           @Override
            public void onStart() {
                startWebCamCamera();
            }

            @Override
            public void onStop() {
                stopWebCamCamera();
            }

            @Override
            public void onDispose() {
                disposeWebCamCamera();
            }
        });
        webCamPane.setCenter(imgWebCamCapturedImage);
        webCamPane.maxHeight(400);
        webCamPane.maxWidth(400);
        webCamPane.setStyle("-fx-background-color: black;-fx-border-color: grey ; -fx-border-width: 5");

        double height = 400;
        double width = 400;

        imgWebCamCapturedImage.setFitHeight(height);
        imgWebCamCapturedImage.setFitWidth(width);
        imgWebCamCapturedImage.prefHeight(height);
        imgWebCamCapturedImage.prefWidth(width);
        imgWebCamCapturedImage.maxHeight(height);
        imgWebCamCapturedImage.maxWidth(width);
        imgWebCamCapturedImage.setPreserveRatio(true);
        addToContent(webCamPane);
    }

    private void addBottomControlPane () {
        bottomCameraControlPane = new FlowPane();
        bottomCameraControlPane.setOrientation(Orientation.HORIZONTAL);
        bottomCameraControlPane.setAlignment(Pos.CENTER);
        bottomCameraControlPane.setHgap(20);
        bottomCameraControlPane.setVgap(10);
        bottomCameraControlPane.setPrefHeight(40);
        bottomCameraControlPane.setDisable(true);
        createCameraControls();
        addToContent(bottomCameraControlPane);
    }

    // TODO Det her skal ændres så det er en del af webcamImageView og passet til controlInterface
    private void addGetImageButton () {
        Button button = new Button("Get image");
        button.setOnAction(event -> {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imgWebCamCapturedImage.getImage(), null);
            try {
                ImageIO.write(bufferedImage,"PNG", new File("export.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        addToContent(button);
    }

    private void createCameraControls() {

        btnCameraStop = new Button("Stop Camera");
        btnCameraStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                imgWebCamCapturedImage.stopWebCamCamera();
            }
        });

        btnCameraStart = new Button("Start Camera");
        btnCameraStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                imgWebCamCapturedImage.startWebCamCamera();
            }
        });

        btnCameraDispose = new Button("Dispose Camera");
        btnCameraDispose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                imgWebCamCapturedImage.disposeWebCamCamera();
            }
        });
        bottomCameraControlPane.getChildren().add(btnCameraStart);
        bottomCameraControlPane.getChildren().add(btnCameraStop);
        bottomCameraControlPane.getChildren().add(btnCameraDispose);
    }

    protected void disposeWebCamCamera() {
        btnCameraStart.setDisable(true);
        btnCameraStop.setDisable(true);
        webCamManiBtn.setManipulationState(false);
        if (!isWebCamSelected) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    webCamOptions.getSelectionModel().clearSelection();
                    webCamOptions.setPromptText("Select a webcam as input");
                    webCamOptions.setDisable(false);
                }
            });
        }
    }

    protected void startWebCamCamera() {
        bottomCameraControlPane.setDisable(false);
        btnCameraStop.setDisable(false);
        btnCameraStart.setDisable(true);
        if (isWebCamSelected) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    webCamOptions.getSelectionModel().select(imgWebCamCapturedImage.getWebcam());
                    webCamOptions.setDisable(true);
                }
            });
        }
    }

    protected void stopWebCamCamera() {
        btnCameraStart.setDisable(false);
        btnCameraStop.setDisable(true);
        webCamOptions.setDisable(false);
    }

    @Override
    protected void testMode() {
        if (MainGUI.isTesting) {
            if (webCamOptions.getItems().size() != 0) {
                webCamOptions.getSelectionModel().select(0);
            }
        }
    }

}
