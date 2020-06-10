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
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.WebCamSettings;
import view.MainGUI;
import view.components.FxUtil;
import view.components.TabStd;
import view.components.webCamImageView.WebCamImageView;
import view.components.webCamManipulationButton.ManipulationStateCallback;
import view.components.webCamManipulationButton.WebCamManiButton;
import view.components.webCamImageView.WebCamStateCallback;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class CameraSourceTabNew extends TabStd {

    //-------------------------- Fields --------------------------

    private final String WEBCAM_SELECTOR_LABEL_TEXT = "Select webcam to use as source:";
    private final String RESOLUTION_SELECTOR_LABEL_TEXT = "Select webcam resolution (width x height):";
    private final String MIRRORING_LABEL_TEXT = "Mirror webcam image:";
    private final String PREVIEW_LABEL_TEXT = "Preview of currently selected settings.";
    private final int COMBOBOX_MAX_WIDTH = 150;
    private final int PREVIEW_WEBCAM_VIEW_HEIGHT = 144;
    private final int PREVIEW_WEBCAM_VIEW_WIDTH = 256;

    private WebCamImageView imgWebCamCapturedImage;

    private ComboBox<WebCamCbHolder> webCamComboBox;
    private ComboBox<ResolutionCbHolder> resolutionComboBox;

    private FlowPane bottomCameraControlPane;
    private CheckBox cbWebCamMirroring;
    private Button btnGetImage;
    private Button btnCameraStop;
    private Button btnCameraStart;
    private Button btnCameraDispose;

    //----------------------- Constructor -------------------------
    public CameraSourceTabNew(){
        super("Image source & settings",
                "Select image source and settings",
                "Selected the preferred camera source to monitor the game your are playing " +
                        "and select the preferred settings for the selected camera.");

        addWebCamSelector();
        addPreviewVideoSection();
        addResolutionSelector();
        addMirroringCheckBox();
        addBottomControlPane();
        addGetImageButton();
        addTabUserData();
        testMode();
        setDefaultLayout();
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------


    //---------------------- Support Methods ----------------------

    private void addWebCamSelector() {
        HBox webCamSelectorBox = FxUtil.hBox(true);

        Text selectLabelText = FxUtil.textDefault(WEBCAM_SELECTOR_LABEL_TEXT);
        webCamSelectorBox.getChildren().add(selectLabelText);

        List<WebCamCbHolder> webCamCbHolderList = WebCamCbHolder.webCamCbHolderListOfWebCamList(Webcam.getWebcams());
        webCamComboBox = new ComboBox<>(FXCollections.observableList(webCamCbHolderList));
        webCamComboBox.setMaxWidth(COMBOBOX_MAX_WIDTH);
        webCamComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WebCamCbHolder>() {
            @Override
            public void changed(ObservableValue<? extends WebCamCbHolder> observable, WebCamCbHolder oldValue, WebCamCbHolder newValue) {
                if (newValue != null) {
                    if (MainGUI.isTesting){
                        MainGUI.printToOutputAreaNewline("Selected webcam: " + newValue.getName()
                                + " // Thread No: " + Thread.currentThread().getId());
                    }

                    imgWebCamCapturedImage.setWebCam(newValue.getWebcam());
                    imgWebCamCapturedImage.startRunning();
                    // Saves the selected webcam to WebCamSettings
                    WebCamSettings.getInstance().setWebcam(newValue.getWebcam());

                    resolutionComboBox.getSelectionModel().selectFirst();

                    cbWebCamMirroring.setSelected(WebCamSettings.getInstance().isMirrored());
                    imgWebCamCapturedImage.mirrorWebCamImage(WebCamSettings.getInstance().isMirrored());

                    bottomCameraControlPane.setDisable(false);
                    resolutionComboBox.setDisable(false);
                    cbWebCamMirroring.setDisable(false);
                }
            }
        });

        webCamSelectorBox.getChildren().add(webCamComboBox);

        addToContent(webCamSelectorBox);
    }

    private void addResolutionSelector() {
        HBox resolutionSelectorBox = FxUtil.hBox(true);

        Text resolutionLabel = FxUtil.textDefault(RESOLUTION_SELECTOR_LABEL_TEXT);

        List<ResolutionCbHolder> resolutionCbHolderList =
                ResolutionCbHolder.listOfDimensionList(Arrays.asList((WebCamSettings.getInstance().getResolutionOptions())));

        resolutionComboBox = new ComboBox<>(FXCollections.observableList(resolutionCbHolderList));
        resolutionComboBox.setMaxWidth(COMBOBOX_MAX_WIDTH);
        resolutionComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ResolutionCbHolder>() {
            @Override
            public void changed(ObservableValue<? extends ResolutionCbHolder> observable, ResolutionCbHolder oldValue, ResolutionCbHolder newValue) {
                if (newValue != null) {
                    MainGUI.printToOutputAreaNewline("Selected webcam resolution: " + newValue.resolutionText);
                    imgWebCamCapturedImage.setWebCamResolution(newValue.dimension);
                    WebCamSettings.getInstance().setSelectedResolution(newValue.dimension);
                }
            }
        });

        resolutionSelectorBox.getChildren().addAll(resolutionLabel,resolutionComboBox);

        addToContent(resolutionSelectorBox);
    }

    private void addMirroringCheckBox () {
        HBox mirroringBox = FxUtil.hBox(true);

        Text mirroringLabel = FxUtil.textDefault(MIRRORING_LABEL_TEXT);

        cbWebCamMirroring = new CheckBox();
        //cbWebCamMirroring.setSelected(WebCamSettings.getInstance().isMirrored());
        cbWebCamMirroring.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                imgWebCamCapturedImage.mirrorWebCamImage(newValue);
                WebCamSettings.getInstance().setMirrored(newValue);
            }
        });

        mirroringBox.getChildren().addAll(mirroringLabel,cbWebCamMirroring);

        addToContent(mirroringBox);
    }

    private void addPreviewVideoSection() {
        VBox previewBox = FxUtil.vBox(true);

        Text previewLabel = FxUtil.textDefault(PREVIEW_LABEL_TEXT);

        HBox webCamPane = new HBox();
        imgWebCamCapturedImage = new WebCamImageView();
        imgWebCamCapturedImage.setStateCallback(new WebCamStateCallback() {
           @Override
            public void onStarted() {
                startWebCamCamera();
            }

            @Override
            public void onStopped() {
                stopWebCamCamera();
            }

            @Override
            public void onDisposed() {
                disposeWebCamCamera();
            }
        });
        webCamPane.getChildren().add(imgWebCamCapturedImage);
        webCamPane.maxHeight(PREVIEW_WEBCAM_VIEW_HEIGHT);
        webCamPane.maxWidth(PREVIEW_WEBCAM_VIEW_WIDTH);
        webCamPane.setAlignment(Pos.CENTER);

        webCamPane.setStyle("-fx-border-color: black; -fx-padding :5 ; -fx-border-width: 2");

        imgWebCamCapturedImage.setFitHeight(PREVIEW_WEBCAM_VIEW_HEIGHT);
        imgWebCamCapturedImage.setFitWidth(PREVIEW_WEBCAM_VIEW_WIDTH);
        imgWebCamCapturedImage.setPreserveRatio(true);

        previewBox.getChildren().addAll(previewLabel,new Group( webCamPane));
        addSeparator();
        addToContent(previewBox);
        addSeparator();
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
        btnGetImage = new Button("Get image");
        btnGetImage.setOnAction(event -> {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imgWebCamCapturedImage.getImage(), null);
            try {
                ImageIO.write(bufferedImage,"PNG", new File("export.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        addToContent(btnGetImage);
    }

    private void createCameraControls() {

        btnCameraStop = new Button("Stop Camera");
        btnCameraStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                imgWebCamCapturedImage.stopRunning();
            }
        });

        btnCameraStart = new Button("Start Camera");
        btnCameraStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                imgWebCamCapturedImage.startRunning();
            }
        });

        btnCameraDispose = new Button("Dispose Camera");
        btnCameraDispose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                imgWebCamCapturedImage.resetVariables();
            }
        });
        bottomCameraControlPane.getChildren().add(btnCameraStart);
        bottomCameraControlPane.getChildren().add(btnCameraStop);
        bottomCameraControlPane.getChildren().add(btnCameraDispose);
    }

    protected void disposeWebCamCamera() {
        // Runs UI updates on the JavaFX Thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Enable
                webCamComboBox.setDisable(false);
                // Disable
                resolutionComboBox.setDisable(true);
                cbWebCamMirroring.setDisable(true);
                bottomCameraControlPane.setDisable(true);
                btnGetImage.setDisable(true);

                // Clears ComboBox's
                webCamComboBox.getSelectionModel().clearSelection();
                resolutionComboBox.getSelectionModel().clearSelection();
                WebCamSettings.getInstance().reset();
            }
        });
    }

    protected void startWebCamCamera() {
        // Runs UI updates on the JavaFX Thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Enable
                bottomCameraControlPane.setDisable(false);
                btnCameraStop.setDisable(false);
                btnCameraDispose.setDisable(false);
                btnGetImage.setDisable(false);
                cbWebCamMirroring.setDisable(false);
                resolutionComboBox.setDisable(false);
                // Disable
                webCamComboBox.setDisable(true);
                btnCameraStart.setDisable(true);
            }
        });
    }

    protected void stopWebCamCamera() {
        // Runs UI updates on the JavaFX Thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Enable
                btnCameraStart.setDisable(false);
                // Disable
                btnCameraStop.setDisable(true);
            }
        });

        MainGUI.printToOutputAreaNewline(WebCamSettings.getInstance().toString());
    }

    private void setDefaultLayout() {
        resolutionComboBox.setDisable(true);
        cbWebCamMirroring.setDisable(true);
        btnGetImage.setDisable(true);
        btnCameraStop.setDisable(true);
        btnCameraDispose.setDisable(true);
        btnCameraStart.setDisable(true);
    }

    @Override
    protected void testMode() {
        if (MainGUI.isTesting) {
            if (webCamComboBox.getItems().size() != 0) {
                webCamComboBox.getSelectionModel().select(0);
                //imgWebCamCapturedImage.getWebcam().setCustomViewSizes(new Dimension[]{new Dimension(1024, 768)});
                //imgWebCamCapturedImage.getWebcam().setViewSize(new Dimension(1024, 768));
                imgWebCamCapturedImage.startImageBinding(TAG + ".testMode()");
            }
        }
    }

    private void addTabUserData() {
        TabUserData tabUserData = new TabUserData.Builder()
                .usedInClassName(TAG)
                .closeRunnable(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(TAG + " // StopWebCamera");
                        imgWebCamCapturedImage.stopRunning();
                    }
                }).openRunnable(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(TAG + " // StartImageBinding");
                        if (webCamComboBox.getSelectionModel().getSelectedItem() != null) {
                            imgWebCamCapturedImage.startRunning();
                        }
                    }
                })
                .build();

        setUserData(tabUserData);

    }

    // region Placeholder for ComboBox to select webcam
    private static class WebCamCbHolder {
        private Webcam webcam;
        private String name;

        public WebCamCbHolder (Webcam webcam) {
            this.webcam = webcam;
            defineName();
        }

        public Webcam getWebcam() {
            return webcam;
        }

        public String getName() {
            return name;
        }

        public static List<WebCamCbHolder> webCamCbHolderListOfWebCamList (List<Webcam> webcamList) {
            List<WebCamCbHolder> tempHolderList = new ArrayList<>();
            for (Webcam webcam : webcamList) {
                tempHolderList.add(new WebCamCbHolder(webcam));
            }
            return tempHolderList;
        }

        public String toString () {
            return name;
        }

        public void defineName() {
            int lastSpaceIndex = -1;
            try {
                lastSpaceIndex = webcam.getName().lastIndexOf(" ");
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (lastSpaceIndex != -1) {
                name = webcam.getName().substring(0,lastSpaceIndex);
            } else {
                name = webcam.getName();
            }
        }
    }

    // endregion

    // region Placeholder for ComboBox to select resolution
    private static class ResolutionCbHolder {
        private Dimension dimension;
        private String resolutionText;

        public ResolutionCbHolder (Dimension dimension) {
            this.dimension = dimension;
            defineWidthHeight();
        }

        public static List<ResolutionCbHolder> listOfDimensionList(List<Dimension> dimensionList) {
            List<ResolutionCbHolder> tempHolderList = new ArrayList<>();
            for (Dimension dimension : dimensionList) {
                tempHolderList.add(new ResolutionCbHolder(dimension));
            }
            return tempHolderList;
        }

        public String toString () {
            return resolutionText;
        }

        public void defineWidthHeight() {
            resolutionText = String.format("%d x %d", dimension.width,dimension.height);
        }
    }

    // endregion

}
