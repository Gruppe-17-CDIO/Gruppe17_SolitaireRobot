package view.components.confetti;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Rasmus Sander Larsen
 */
public class ConfettiPane extends Pane {

    //-------------------------- Fields --------------------------

    private final Random random = new Random();
    private final List<MovingDot> dots = new ArrayList<>();
    private final Timeline timelineConfetti = new Timeline();
    private final Timeline timelineGIF = new Timeline();

    private final ImageView gifImageView = new ImageView();

    private boolean isShooting = false;

    //----------------------- Constructor -------------------------

    public ConfettiPane() {
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

    // TODO: Skal bare fjernes
    public void activateConfetti (boolean isConfettiActive) {
        if (isConfettiActive) {
            this.getChildren().clear();
            this.getChildren().removeAll();
            this.dots.clear();
            for (int i = 0; i < Math.round(100); i++) {
                MovingDot dot = new MovingDot(getRandomNumberInRange(1, 3),
                        getRandomNumberInRange(1, 3),
                        getRandomNumberInRange(0, (int) getWidth()),
                        getRandomNumberInRange(0, (int) getHeight()
                        ));
                this.dots.add(dot);
                this.getChildren().add(dot);
            }
            //Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), this::timelineEvent));
            timelineConfetti.getKeyFrames().add(new KeyFrame(Duration.millis(20), this::timelineEvent));
            timelineConfetti.setCycleCount(Timeline.INDEFINITE);
            timelineConfetti.setCycleCount(500);
            timelineConfetti.play();
            timelineConfetti.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    setVisible(false);
                }
            });

            setVisible(true);
        } else {
            timelineConfetti.stop();
            setVisible(false);
        }

    }

    //---------------------- Support Methods ----------------------    

    //TODO: skal bare fjernes.
    private void timelineEvent(ActionEvent e) {
        Bounds bounds = this.getBoundsInLocal();

        for (MovingDot dot : dots) {
            dot.move(bounds);
        }
    }

    // TODO: skal bare fjernes
    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return this.random.nextInt((max - min) + 1) + min;
    }

}
