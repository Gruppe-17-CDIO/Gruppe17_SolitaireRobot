package view.taps;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.MainGUI;
import view.components.FxUtil;
import view.components.TabStd;
import view.components.WebCamManiButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Rasmus Sander Larsen
 */
public class CameraSourceTab extends TabStd {

    //-------------------------- Fields --------------------------

    private final String comboBoxPromptText = "Select webcam to use as source";

    private ImageView imgWebCamCapturedImage;
    private Webcam webcam = null;
    private boolean isCameraRunning = true;
    private boolean isWebCamSelected = false;
    private boolean isWebCamManipulated = false;
    ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();

    private ComboBox<Webcam> webCamOptions;
    protected File selectedDirectory;
    private FlowPane bottomCameraControlPane;
    private WebCamManiButton webCamManiBtn;
    private Button btnCameraStop;
    private Button btnCameraStart;
    private Button btnCameraDispose;

    //----------------------- Constructor -------------------------
    public CameraSourceTab (){
        super("Source",
                "Select camera input source",
                "Selected the preferred camera source to record the game your are playing");
        addDropdown();
        addVideoFrame();
        addBottomControlPane();
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
                    initializeWebCam(newValue);
                } else {
                    isWebCamSelected = false;
                }
            }
        });

        topPane.getChildren().add(webCamOptions);
        webCamManiBtn = new WebCamManiButton();
        if (MainGUI.isTesting) {
            topPane.getChildren().add(webCamManiBtn);
        }
        addToContent(topPane);
    }

    private void addVideoFrame() {
        BorderPane webCamPane = new BorderPane();
        imgWebCamCapturedImage = new ImageView();
        webCamPane.setCenter(imgWebCamCapturedImage);
        webCamPane.maxHeight(400);
        webCamPane.maxWidth(400);
        webCamPane.setStyle("-fx-background-color: grey;");

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

    protected void initializeWebCam(Webcam selectedWebCam) {

        Task<Void> webCamTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                if (webcam != null) {
                    disposeWebCamCamera();
                }

                webcam = selectedWebCam;

                webcam.setCustomViewSizes(new Dimension[]{new Dimension(1024, 768)});
                webcam.setViewSize(new Dimension(1024, 768));

                webcam.open();

                startWebCamCamera();

                return null;
            }
        };

        Thread webCamThread = new Thread(webCamTask);
        webCamThread.setDaemon(true);
        webCamThread.start();
    }

    protected void startWebCamStream() {

        isCameraRunning = true;

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                while (isCameraRunning) {
                    try {
                        if (webCamManiBtn.isWebCamManipulated()) {
                            imageProperty.set(webCamManiBtn.imageOfFile());
                        } else {
                            final AtomicReference<WritableImage> ref = new AtomicReference<>();
                            BufferedImage img = null;

                            if ((img = webcam.getImage()) != null) {

                                ref.set(SwingFXUtils.toFXImage(img, ref.get()));
                                img.flush();

                                Platform.runLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        imageProperty.set(ref.get());
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        imgWebCamCapturedImage.imageProperty().bind(imageProperty);
        // Flips the image so it works as a mirror
        imgWebCamCapturedImage.setScaleX(-1);
    }

    private void createCameraControls() {

        btnCameraStop = new Button("Stop Camera");
        btnCameraStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                stopWebCamCamera();
            }
        });

        btnCameraStart = new Button("Start Camera");
        btnCameraStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                startWebCamCamera();
            }
        });

        btnCameraDispose = new Button("Dispose Camera");
        btnCameraDispose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                isWebCamSelected = false;
                disposeWebCamCamera();
            }
        });
        bottomCameraControlPane.getChildren().add(btnCameraStart);
        bottomCameraControlPane.getChildren().add(btnCameraStop);
        bottomCameraControlPane.getChildren().add(btnCameraDispose);
    }

    protected void disposeWebCamCamera() {
        isCameraRunning = false;
        webcam.close();
        btnCameraStart.setDisable(true);
        btnCameraStop.setDisable(true);
        if (!isWebCamSelected) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    webCamOptions.getSelectionModel().clearSelection();
                    webCamOptions.setPromptText("Select a webcam as input");
                    webCamOptions.setDisable(false);
                    // removes the last captured image.
                    imgWebCamCapturedImage.imageProperty().unbind();
                    imgWebCamCapturedImage.setImage(null);
                }
            });
        }
    }

    protected void startWebCamCamera() {
        isCameraRunning = true;
        startWebCamStream();
        bottomCameraControlPane.setDisable(false);
        btnCameraStop.setDisable(false);
        btnCameraStart.setDisable(true);
        if (isWebCamSelected) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    webCamOptions.getSelectionModel().select(webcam);
                    webCamOptions.setDisable(true);
                }
            });
        }
    }

    protected void stopWebCamCamera() {
        isCameraRunning = false;
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
