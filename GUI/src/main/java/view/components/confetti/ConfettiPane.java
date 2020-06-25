package view.components.confetti;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import view.MainGUI;
import view.components.FxUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Rasmus Sander Larsen
 */
public class ConfettiPane extends HBox {

    //-------------------------- Fields --------------------------

    private final Timeline timelineGIF = new Timeline();

    private final ImageView gifImageView = new ImageView();

    private boolean isShooting = false;

    //----------------------- Constructor -------------------------

    public ConfettiPane() {
        setAlignment(Pos.CENTER);
        getChildren().add(gifImageView);
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    public void onShotOfGifConfetti() {
        if (!isShooting) {
            // Shows the confetti GIF image.
            gifImageView.setImage(new Image(this.getClass().getResourceAsStream("/confetti.gif")));
            gifImageView.setPreserveRatio(true);
            gifImageView.setX(MainGUI.SCREEN_WIDTH/2.0);
            setVisible(true);

            isShooting = true;

            // Stops the GIF after 1 sec.
            timelineGIF.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    gifImageView.setImage(null);
                    setVisible(false);
                    isShooting = false;
                }
            }));

            timelineGIF.setCycleCount(1);
            timelineGIF.play();
        }
    }

    //---------------------- Support Methods ----------------------    

}
