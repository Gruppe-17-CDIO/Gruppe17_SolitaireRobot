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

    private boolean isSettingsSet;

    private Webcam webcam;
    private boolean isMirrored;
    private Dimension selectedResolution = resolutionOptions[0];

    //----------------------- Constructor -------------------------

    private WebCamSettings(){
        isSettingsSet = false;
    }

    public WebCamSettings(Webcam webcam, boolean isMirrored, Dimension selectedResolution) {
        this.webcam = Webcam.getWebcams().get(Webcam.getWebcams().indexOf(webcam));
        this.isMirrored = isMirrored;
        this.selectedResolution = selectedResolution;
        isSettingsSet = true;
    }

    //------------------------ Properties -------------------------

    // region Properties

    public Dimension[] getResolutionOptions() {
        return resolutionOptions;
    }

    public Dimension getSelectedResolution() {
        return selectedResolution;
    }

    public boolean isMirrored() {
        return isMirrored;
    }

    public boolean isSettingsSet() {
        return isSettingsSet;
    }

    // endregion

    //---------------------- Public Methods -----------------------

    public static WebCamSettings getInstance() {
        if (instance == null) {
            instance = new WebCamSettings();
        }
        return instance;
    }

    public void setVariablesOfWebCamImageView (WebCamImageView webCamImageView) {
        try {
            instance = new WebCamSettings(
                    webCamImageView.getWebcam(),
                    webCamImageView.getMirrorScaling() == -1,
                    webCamImageView.getWebcam().getViewSize());
            isSettingsSet = true;
        } catch (Exception e) {
            e.printStackTrace();
            isSettingsSet = false;
        }

        MainGUI.printToOutputAreaNewline("Saved Settings");
    }

    public void setWebCamImageViewWithSetting (WebCamImageView webCamImageView) {
        if (isSettingsSet) {
            webCamImageView.setWebCam(webcam);
            webCamImageView.mirrorWebCamImage(isMirrored);
            webCamImageView.setWebCamResolution(selectedResolution);
            MainGUI.printToOutputAreaNewline("Settings Applied to WebCamImageView");
        } else {
            MainGUI.printToOutputAreaNewline("Setting NOT applied to WebCamImageView as no settings is set");
        }
    }

    public int getMirrorSettingAsScaling(){
        return isMirrored ? -1 : 1;
    }

    public String toString () {
        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append("~~~~~~~~~~~~~~~ Webcam Settings ~~~~~~~~~~~~~~~\n");

        toStringBuilder.append("isSettingsSet: " + isSettingsSet + "\n");
        if (isSettingsSet) {
            toStringBuilder.append("isMirrored: " + isMirrored + "\n");
            if (webcam != null) {
                toStringBuilder.append("Webcam Name: " + webcam.getName() + "\n");
                toStringBuilder.append("Webcam resolution: " +webcam.getViewSize().width+"x"+webcam.getViewSize().height + "\n");
            }
        }
        toStringBuilder.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        return toStringBuilder.toString();
    }

    //---------------------- Support Methods ----------------------    



}
