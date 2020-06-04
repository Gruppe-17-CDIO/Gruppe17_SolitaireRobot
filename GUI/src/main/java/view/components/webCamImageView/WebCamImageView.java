package view.components.webCamImageView;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import model.WebCamSettings;
import view.MainGUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Rasmus Sander Larsen
 */
public class WebCamImageView extends ImageView {

    //-------------------------- Fields --------------------------

    private final String TAG = getClass().getSimpleName();
    
    private Thread imageBindingThread;
    private WebCamStateCallback stateCallback;

    private Webcam webcam = null;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private AtomicBoolean isBindingNeeded = new AtomicBoolean(true);
    private AtomicBoolean isWebCamManipulated = new AtomicBoolean(false);
    private Image manipulatedImage;
    private int mirrorScaling;
    //private boolean isMirrored = true;

    //----------------------- Constructor -------------------------

    public WebCamImageView () {
        super();
        applyDefaultSettings();
    }

    //------------------------ Properties -------------------------

    // region Properties

    public String getTAG() {
        return TAG;
    }

    public Thread getImageBindingThread() {
        return imageBindingThread;
    }

    public void setImageBindingThread(Thread imageBindingThread) {
        this.imageBindingThread = imageBindingThread;
    }

    public WebCamStateCallback getStateCallback() {
        return stateCallback;
    }

    public void setStateCallback(WebCamStateCallback stateCallback) {
        this.stateCallback = stateCallback;
    }

    public Webcam getWebcam() {
        return webcam;
    }

    public void setWebcam(Webcam webcam) {
        this.webcam = webcam;
    }

    public AtomicBoolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(AtomicBoolean isRunning) {
        this.isRunning = isRunning;
    }

    public AtomicBoolean getIsBindingNeeded() {
        return isBindingNeeded;
    }

    public void setIsBindingNeeded(AtomicBoolean isBindingNeeded) {
        this.isBindingNeeded = isBindingNeeded;
    }

    public AtomicBoolean getIsWebCamManipulated() {
        return isWebCamManipulated;
    }

    public void setIsWebCamManipulated(AtomicBoolean isWebCamManipulated) {
        this.isWebCamManipulated = isWebCamManipulated;
    }

    public Image getManipulatedImage() {
        return manipulatedImage;
    }

    public void setManipulatedImage(Image manipulatedImage) {
        this.manipulatedImage = manipulatedImage;
    }

    public int getMirrorScaling() {
        return mirrorScaling;
    }

    public void setMirrorScaling(int mirrorScaling) {
        this.mirrorScaling = mirrorScaling;
    }

    // endregion

    //---------------------- Public Methods -----------------------

    public void setWebCam(Webcam webCamToSet) {
        boolean preIsRunning = isRunning.get();

        // Kills the thread of an live imageBindingThread if there is any.
        killImageBindingThread();

        // Closes the webcam if there is one and it's open.
        if (webcam != null) {
            if (webcam.isOpen()){
                webcam.close();
            }
        }

        // Sets the new webCam as current.
        webcam = webCamToSet;

        // Makes sure that the webcam has the correct customViewSizes is set or sets them.
        if (webcam.getCustomViewSizes() != WebCamSettings.getInstance().getResolutionOptions()) {
            webcam.setCustomViewSizes(WebCamSettings.getInstance().getResolutionOptions());
        }

        // Sets the webcams resolution.
        if (WebCamSettings.getInstance().getSelectedResolution() != null) {
            setWebCamResolution(WebCamSettings.getInstance().getSelectedResolution());
        }

        if (preIsRunning) {
            startRunning();
        }
    }

    public void setWebCamResolution(Dimension resolutionDimension) {
        boolean preIsRunning = isRunning.get();
        killImageBindingThread();

        // Closes the webcam if it's open.
        try {
            if (webcam.isOpen()){
                webcam.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        // Adds the resolutionDimension to the list af customViewSizes if it's not there already.
        if (!Arrays.asList(webcam.getCustomViewSizes()).contains(resolutionDimension)) {
            List<Dimension> customViewSizesList = new ArrayList<>(Arrays.asList(webcam.getCustomViewSizes()));
            customViewSizesList.add(resolutionDimension);
            // Converts List to Array
            Dimension[] customViewSizeArray = new Dimension[customViewSizesList.size()];
            for (int i = 0 ; i < customViewSizeArray.length ; i++ ) {
                customViewSizeArray[i] = customViewSizesList.get(i);
            }
            webcam.setCustomViewSizes(customViewSizeArray);
        }
        // Set the ViewSize
        webcam.setViewSize(resolutionDimension);

        if (preIsRunning) {
            startRunning();
        }
    }

    public void mirrorWebCamImage(boolean isMirrored) {
        if (isMirrored) {
            mirrorScaling = -1;
        } else {
            mirrorScaling = 1;
        }
    }

    public void doManipulationOfImageView() {
        System.out.print(TAG + ".startManipulationOfWebCam()");
        if (manipulatedImage != null) {
            System.out.println(" // STARTED");

            // Set manipulations variables
            isWebCamManipulated.compareAndSet(false,true);

            killImageBindingThread();

            if (isRunning.get()){
                startImageBindingProcess(TAG + ".startManipulationOfWebCam");
            } else {
                doImageBindingOnce(TAG + ".startManipulationOfWebCam");
            }

            /*
            isRunning = true;

            // If no imageBinding is already running then starts one.
            if (isBindingNeeded.compareAndSet(false,true)){
                startImageBinding(TAG + ".startManipulationOfWebCam");
            }
             */

        } else {
            System.out.println(" // manipulationImage is null!!");
        }
    }

    public void startAndSetManipulationImage (Image imageToShow) {
        manipulatedImage = imageToShow;
        doManipulationOfImageView();
    }

    public void stopManipulationOfWebCam(){
        boolean run =isRunning.get();
        boolean bind = isBindingNeeded.get();
        System.out.print(TAG + ".stopManipulationOfWebCam()");
        if (isWebCamManipulated.get()) {
            System.out.println(" // Is stopped");

            killImageBindingThread();

            // Clear manipulation variables
            isWebCamManipulated.compareAndSet(true,false);
            manipulatedImage = null;

            // Starts a image rebind to represent the new state.
            if (isRunning.get()) {
                isBindingNeeded.compareAndSet(false, true);
                MainGUI.printToOutputAreaNewline(""+isBindingNeeded);
                startImageBindingProcess(TAG + ".stopManipulationOfWebCam()");
            } else {
                doImageBindingOnce(TAG + ".stopManipulationOfWebCam()");
            }
        } else {
            System.out.println(" // Is ALREADY stopped");
        }
    }

    public void resetVariables() {
        System.out.println(TAG + ".clearImageBinding()");

        isRunning.compareAndSet(true,false);
        isBindingNeeded.compareAndSet(true,false);
        isWebCamManipulated.compareAndSet(true,false);
        manipulatedImage = null;

        if (webcam != null) {
            webcam.close();
            webcam = null;
        }
        // removes the last captured image.
        clearImage();

        if (stateCallback != null) {
            stateCallback.onDisposed();

        }
    }

    public void startImageBinding(String method) {

        // If a imageBindingThread is alive it will be killed.
        killImageBindingThread();

        // Tells that binding is needed and sets the image as running.
        isBindingNeeded.compareAndSet(false,true);
        //isRunning = true;

        startImageBindingProcess( method+ " called : " +TAG +".startImageBinding()");
        if (stateCallback != null) {
            stateCallback.onStarted();
        }

    }

    public void stopImageBinding() {
        System.out.println(TAG + ".stopImageBinding()");

        //killThreadWithIsRunning();
        //isRunning = false;

        if (stateCallback != null) {
            stateCallback.onStopped();
        }
    }

    public void startRunning() {
        //killImageBindingThread();
        isRunning.compareAndSet(false,true);
        isBindingNeeded.compareAndSet(false,true);

        startImageBinding(TAG+".startRunning()");

        stateCallback.onStarted();
    }

    public void stopRunning(){
        if (isRunning.compareAndSet(true,false)) {
            if (MainGUI.isTesting) {
                MainGUI.printToOutputAreaNewline(TAG +".stop() // isRunning is set to false");
            }

            killImageBindingThread();

            stateCallback.onStopped();
        } else {
            if (MainGUI.isTesting){
                MainGUI.printToOutputAreaNewline(TAG +".stop() // isRunning is already false");
            }
        }
    }


    //---------------------- Support Methods ----------------------

    public boolean isImageBindingThreadAlive() {
        if (imageBindingThread != null) {
            return imageBindingThread.isAlive();
        } else {
            return false;
        }
    }

    private void killImageBindingThread() {
        boolean tempPreMethodIsRunning = isBindingNeeded.get();
        if (isImageBindingThreadAlive()) {
            if (MainGUI.isTesting) {
                long beforeSec = System.currentTimeMillis();

                do {
                    isBindingNeeded.set(false);
                } while (imageBindingThread.isAlive());

                long afterSec = System.currentTimeMillis();
                MainGUI.printToOutputAreaNewline("Time it took to kill imageBindingThread: " + (afterSec-beforeSec) + "ms");
            } else {
                do {
                    isBindingNeeded.set(false);
                } while (imageBindingThread.isAlive());
            }
        }
        isBindingNeeded.set(tempPreMethodIsRunning);
    }

    public void applyDefaultSettings () {
        setStyle("-fx-border-width: 5;-fx-border-color: black;-fx-background-color: white");
        setPreserveRatio(true);
    }

    private void useWebCamFeed() {
        try {
            // Checks if the is NOT open AND locked. Opens it if needed.
            if (!webcam.isOpen() && !webcam.getLock().isLocked() || !webcam.isOpen() && webcam.getLock() != null){
                webcam.getLock().unlock();
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
                        // Flips the image so it works as a mirror
                        setScaleX(mirrorScaling);
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("WebCamImageView.useWebCamFeed() - Exception:");
            e.printStackTrace();
        }
    }

    private void useManipulationFeed() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                imageProperty().set(manipulatedImage);
                // Makes sure that the image it isn't mirrored.
                setScaleX(1);
            }
        });
    }

    private void clearImage() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                imageProperty().set(null);
            }
        });
        //isRunning.compareAndSet(true,false);
    }

    private void startImageBindingProcess(String method) {
        Task<Void> imageBindingTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                long threadNo =Thread.currentThread().getId();
                String threadName =Thread.currentThread().getName();;
                // TODO: Det her skal nok vaek!
                if (MainGUI.isTesting){
                    MainGUI.printToOutputAreaNewline("Thread No: " +threadNo +" just started by: " + method);
                    if (webcam != null) {
                        MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// WebCam name: " +webcam.getName());
                    }
                    MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// isRunnning: " +isRunning);
                    MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// isBindingNeeded: " +isBindingNeeded.get());
                    MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// isWebCamManipulated: " +isWebCamManipulated);
                }
                while (isBindingNeeded.get() && isRunning.get()) {
                    System.out.println("ThreadID: " + Thread.currentThread().getId());
                    try {
                        if (isWebCamManipulated.get()) {
                            useManipulationFeed();
                            isBindingNeeded.set(false);
                            System.out.println("UseManipulationFeed");
                        }

                        if (webcam != null) {
                            if (!isWebCamManipulated.get()){
                                useWebCamFeed();
                               System.out.println("UseWebCamFeed");
                            }
                        } else {
                            clearImage();
                            isBindingNeeded.set(false);
                          System.out.println("UseDefaultNoWebCamFeed");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (MainGUI.isTesting){
                    MainGUI.printToOutputAreaNewline("Thread No: " +threadNo +" just stopped");
                    if (webcam != null) {
                        MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// WebCam name: " +webcam.getName());
                    }
                    MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// isRunnning: " +isRunning);
                    MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName  + "// isBindingNeeded: " +isBindingNeeded.get());
                    MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// isWebCamManipulated: " +isWebCamManipulated);
                }

                return null;
            }
        };

        imageBindingThread = new Thread(imageBindingTask);
        imageBindingThread.setName("ImageBindingThread");
        imageBindingThread.setDaemon(true);
        imageBindingThread.start();
    }

    private void doImageBindingOnce(String method) {
        Task<Void> oneImageBindingTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                if (MainGUI.isTesting){
                    long threadNo =Thread.currentThread().getId();
                    String threadName = Thread.currentThread().getName();
                    MainGUI.printToOutputAreaNewline("Thread No: " +threadNo +" do ONE binding: " + method);
                    if (webcam != null) {
                        MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// WebCam name: " +webcam.getName());
                    }
                    MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// isRunnning: " +isRunning);
                    MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// isBindingNeeded: " +isBindingNeeded.get());
                    MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// isWebCamManipulated: " +isWebCamManipulated);
                }

                try {
                    if (isWebCamManipulated.get()) {
                        useManipulationFeed();
                        System.out.println("UseManipulationFeed");
                    }

                    if (webcam != null) {
                        if (!isWebCamManipulated.get()){
                            useWebCamFeed();
                            System.out.println("UseWebCamFeed");
                        }
                    } else {
                        if (!isWebCamManipulated.get()){
                            clearImage();
                            System.out.println("UseDefaultNoWebCamFeed");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (MainGUI.isTesting){
                    printThreadStatus();
                }
                return null;
            }
        };

        imageBindingThread = new Thread(oneImageBindingTask);
        imageBindingThread.setName("OneImageBindingThread");
        imageBindingThread.setDaemon(true);
        imageBindingThread.start();
    }

    private void printThreadStatus() {
        long threadNo =Thread.currentThread().getId();
        String threadName = Thread.currentThread().getName();
        MainGUI.printToOutputAreaNewline("Thread No: " +threadNo +" just stopped");
        if (webcam != null) {
            MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// WebCam name: " +webcam.getName());
        }
        MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// isRunnning: " +isRunning);
        MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName  + "// isBindingNeeded: " +isBindingNeeded.get());
        MainGUI.printToOutputAreaNewline(threadNo +"@" + threadName + "// isWebCamManipulated: " +isWebCamManipulated);
    }
}
