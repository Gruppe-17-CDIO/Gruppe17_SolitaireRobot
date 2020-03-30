package view.components;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Rasmus Sander Larsen
 */
public class WebCamImageView extends ImageView {

    //-------------------------- Fields --------------------------

    private final String TAG = getClass().getSimpleName();

    private Thread webCamThread;
    private WebCamStateCallback stateCallback;

    private Webcam webcam = null;
    private boolean isCameraRunning = true;
    private boolean isWebCamManipulated = false;
    private Image manipulatedImage;
    private int mirrorScaling = 1;

    private final String NO_WEBCAM_IMAGE_TEXT = "No Webcam Is Selected";
    private final String NO_WEBCAM_IMAGE_STYLE = "-fx-background-color: lightgrey; -fx-text-fill:black;-fx-border-color: black;-fx-border-width: 2";

    //----------------------- Constructor -------------------------

    public WebCamImageView () {
        super();
        initializeWebCam();
    }

    public WebCamImageView (Webcam webcam) {
        super();
        this.webcam = webcam;
        initializeWebCam();
    }

    //------------------------ Properties -------------------------

    // region Properties

    public Webcam getWebcam() {
        return webcam;
    }

    public void setStateCallback(WebCamStateCallback stateCallback) {
        this.stateCallback = stateCallback;
    }

    // endregion

    //---------------------- Public Methods -----------------------

    public void setWebCam(Webcam webCamToSet) {
        // Closes the "old" webCam
        if (webcam != null){
            disposeWebCamCamera();
        }
        // Sets the new webCam as current.
        webcam = webCamToSet;
        System.out.println(TAG +".setWebCam() - Selected webcam: " + webcam);

        // Kills the thread of an live webCamThread
        if (webCamThread.isAlive()){
            webCamThread.interrupt();
        }

        // Starts the stream of the new webCam.
        initializeWebCam();
    }

    public void initializeWebCam() {
        Task<Void> webCamTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                startWebCamCamera();
                return null;
            }
        };
        webCamThread = new Thread(webCamTask);
        webCamThread.setDaemon(true);
        webCamThread.start();
    }

    public void mirrorWebCamImage(boolean isMirrored) {
        if (isMirrored) {
            mirrorScaling = -1;
        } else {
            mirrorScaling = 1;
        }
    }

    public void startManipulationOfWebCam (Image imageToShow) {
        if (imageToShow != null) {
            System.out.println(TAG + ".startManipulationOfWebCam()");
            isWebCamManipulated = true;
            manipulatedImage = imageToShow;
        }
    }

    public void stopManipulationOfWebCam(){
        if (isWebCamManipulated) {
            System.out.println(TAG + ".stopManipulationOfWebCam()");
            isWebCamManipulated = false;
            manipulatedImage = null;
        }
    }

    public void disposeWebCamCamera() {
        isCameraRunning = false;
        isWebCamManipulated = false;
        manipulatedImage = null;

        webcam.close();
        webcam = null;

        // removes the last captured image.
        setImage(useDefaultNoWebCamFeed());

        stateCallback.onDispose();
    }

    public void startWebCamCamera() {
        isCameraRunning = true;
        startWebCamStream();

        stateCallback.onStart();
    }

    public void stopWebCamCamera() {
        isCameraRunning = false;

        stateCallback.onStop();
    }

    //---------------------- Support Methods ----------------------    

    /**
     * Interface for callback used when the state of the webcam is changing.
     */
    public interface WebCamStateCallback {
        void onStart();
        void onStop();
        void onDispose();
    }

    private void useWebCamFeed() {
        try {
            // Checks if the is NOT open AND locked. Opens it if needed.
            if (!webcam.isOpen() && !webcam.getLock().isLocked()){
                webcam.open();
            }

            // WritableImage Reference and Buffer variables.
            final AtomicReference<WritableImage> ref = new AtomicReference<>();
            BufferedImage img = null;

            // Takes picture and sets it as imageProperty.
            if ((img = webcam.getImage()) != null) {
                ref.set(SwingFXUtils.toFXImage(img, ref.get()));
                img.flush();

                // Platform.runLater is used as this need to happen on UI Thread.
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        imageProperty().set(ref.get());
                    }
                });
            }
            // Flips the image so it works as a mirror
            setScaleX(mirrorScaling);
        } catch (Exception e) {
            System.out.println("WebCamImageView.useWebCamFeed() - Exception:");
            e.printStackTrace();
        }
    }

    private void useManipulationFeed() {
        imageProperty().set(manipulatedImage);
        // Makes sure that the image it isn't mirrored.
        setScaleX(1);
    }

    private Image useDefaultNoWebCamFeed() {
        Label label = new Label(NO_WEBCAM_IMAGE_TEXT);
        double width = getBoundsInParent().getWidth(), height = getBoundsInParent().getHeight();
        label.setMinSize(width,height);
        label.setMaxSize(width,height);
        label.setPadding(new Insets(20));
        label.setStyle(NO_WEBCAM_IMAGE_STYLE);
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Scene scene = new Scene(new Group(label));
                WritableImage img = new WritableImage((int)width, (int)height) ;
                scene.snapshot(img);
                imageProperty().set(img);
                setScaleX(1);
            }
        });
        return null;
    }

    private void startWebCamStream() {
        Task<Void> steamTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while (isCameraRunning) {
                    try {
                        if (isWebCamManipulated) {
                            useManipulationFeed();
                        } else if (webcam != null) {
                            useWebCamFeed();
                        } else {
                            useDefaultNoWebCamFeed();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };

        Thread steamThread = new Thread(steamTask);
        steamThread.setDaemon(true);
        steamThread.start();
    }
}
