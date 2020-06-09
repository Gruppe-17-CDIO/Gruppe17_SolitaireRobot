package view.components.webCamImageView;

/**
 * @author Rasmus Sander Larsen
 *
 * Interface for callback used when the state of the webcam is changing.
 */

public interface WebCamStateCallback {
    void onStarted();
    void onStopped();
    void onDisposed();
}
