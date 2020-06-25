package model;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import view.MainGUI;
import view.components.webCamImageView.WebCamImageView;

import java.awt.*;

/**
 * @author Rasmus Sander Larsen
 */
public class WebCamSettings {

    //-------------------------- Fields --------------------------

    private static WebCamSettings instance;

    private final Dimension[] resolutionOptions = new Dimension[] {
            new Dimension(320,180),
            new Dimension(424,240),
            new Dimension(640,360),
            new Dimension(848,480),
            new Dimension(1280,720),
            new Dimension(1920,1080)
    };

    private Webcam webcam;
    private boolean isMirrored = false;
    private Dimension selectedResolution;// = resolutionOptions[5];

    //----------------------- Constructor -------------------------

    private WebCamSettings(){ }

    //------------------------ Properties -------------------------

    // region Properties

    public Dimension[] getResolutionOptions() {
        return resolutionOptions;
    }

    public Webcam getWebcam() {
        return webcam;
    }

    public void setWebcam(Webcam webcam) {

        this.webcam = webcam;
        //TODO:REMOVE!!!
        //MainGUI.printToOutputAreaNewline("Print from setWebcam().\n" +toString());
    }

    public boolean isMirrored() {
        return isMirrored;

    }

    public void setMirrored(boolean mirrored) {
        isMirrored = mirrored;
        //TODO:REMOVE!!!
        //MainGUI.printToOutputAreaNewline("Print from setMirrored().\n" +toString());
    }

    public Dimension getSelectedResolution() {
        return selectedResolution;
    }

    public void setSelectedResolution(Dimension selectedResolution) {

        this.selectedResolution = selectedResolution;
        //TODO:REMOVE!!!
        //MainGUI.printToOutputAreaNewline("Print from setSelectedResolution().\n" + toString());
    }

    // endregion

    //---------------------- Public Methods -----------------------

    public static WebCamSettings getInstance() {
        if (instance == null) {
            instance = new WebCamSettings();
        }
        return instance;
    }

    public boolean isSettingsValid () {
        if (webcam == null) {
            return false;
        } else if (selectedResolution == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean setWebCamImageViewWithSetting (String TAG, WebCamImageView webCamImageView) {
        if (isSettingsValid()) {
            webCamImageView.setWebCam(webcam);
            webCamImageView.mirrorWebCamImage(isMirrored);
            webCamImageView.setWebCamResolution(selectedResolution);
            if (MainGUI.isTesting) {
                MainGUI.printToOutputAreaNewline("Settings Applied to WebCamImageView in " + TAG);
            }
            return true;
        } else {
            webCamImageView.resetVariables();
            if (MainGUI.isTesting){
                MainGUI.printToOutputAreaNewline("Setting NOT applied to WebCamImageView in " + TAG +" as no settings is set");
            }
            return false;
        }
    }

    public int getMirrorSettingAsScaling(){
        return isMirrored ? -1 : 1;
    }

    public void reset(){
        instance = new WebCamSettings();
    }

    public String toString () {
        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append("~~~~~~~~~~~~~~~ Webcam Settings ~~~~~~~~~~~~~~~\n");

        toStringBuilder.append("Is Setting Valid: " + isSettingsValid() + "\n");
        toStringBuilder.append("Is Webcam input Mirrored: " + isMirrored + "\n");
        if (webcam != null) {
            toStringBuilder.append("Webcam Name: " + webcam.getName() + "\n");
        } else {
            toStringBuilder.append("Webcam Name: Nothing selected\n");
        }
        if (selectedResolution != null) {
            toStringBuilder.append("Webcam resolution: " +selectedResolution.width+"x"+selectedResolution.height + "\n");
        } else {
            toStringBuilder.append("Webcam resolution: Nothing selected\n");
        }
        toStringBuilder.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        return toStringBuilder.toString();
    }

    //---------------------- Support Methods ----------------------    



}
