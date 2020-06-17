package view.components;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import view.MainGUI;
import view.components.confetti.ConfettiPane;
import view.components.confetti.MovingDot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Rasmus Sander Larsen
 */
public abstract class TabStd extends Tab {

    //-------------------------- Fields --------------------------

    protected final String TAG = getClass().getSimpleName();

    protected String header, desc;
    protected StackPane stackPane = new StackPane();
    protected ConfettiPane confettiPane = new ConfettiPane();
    protected VBox content = FxUtil.vBox(true);

    // Confetti
    private final Random random = new Random();
    private final List<MovingDot> dots = new ArrayList<>();

    //----------------------- Constructor -------------------------

    public TabStd(String title, String header, String desc) {
        this.header = header;
        this.desc = desc;
        setText(title);
        setHeaderAndDesc();
        applySettings();
    }


    //------------------------ Properties -------------------------

    // <editor-folder desc="Properties"


    // </editor-folder>


    //---------------------- Public Methods -----------------------

    protected void addSeparator() {
        Separator separator = new Separator();
        separator.setMaxWidth(450);
        separator.setPadding(new Insets(5, 0, 5, 0));
        addToContent(separator);
    }

    //---------------------- Support Methods ----------------------

    private void applySettings() {
        content.setAlignment(Pos.TOP_CENTER);
        content.setSpacing(FxUtil.getSpacing());
        content.setPadding(new Insets(FxUtil.getPadding()));
        setClosable(false);

        // Makes sure that the dotsPane does not block for nodes below it.
        confettiPane.setMouseTransparent(true);
        stackPane.getChildren().addAll(content, confettiPane);
        setContent(stackPane);
    }

    protected void addToContent(Node node) {
        content.getChildren().add(node);
    }

    protected void addAllToContent(Node... nodes) {
        content.getChildren().addAll(nodes);
    }

    protected void setHeaderAndDesc() {
        //region Header
        Text headerText = FxUtil.headerTextDefault(header);
        // endregion

        // region Description
        Text descText = FxUtil.textDefault(desc);
        descText.setWrappingWidth(400);
        descText.setTextAlignment(TextAlignment.JUSTIFY);
        // endregion

        addAllToContent(headerText, descText);
    }

    protected void onShotOfGifConfetti() {
        //dotsPane.activateConfetti(isDotsActive);
        confettiPane.onShotOfGifConfetti();
    }

    protected void activateConfetti (boolean isConfettiActive) {
        confettiPane.getChildren().clear();
        confettiPane.getChildren().removeAll();
        dots.clear();
        for (int i = 0; i < Math.round(100); i++) {
            MovingDot dot = new MovingDot(getRandomNumberInRange(1, 3),
                    getRandomNumberInRange(1, 3),
                    getRandomNumberInRange(0, MainGUI.sceneWidth/2),
                    getRandomNumberInRange(0, MainGUI.sceneHeight));
            dots.add(dot);
            confettiPane.getChildren().add(dot);
        }

        applySettings();
        setHeaderAndDesc();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), this::timelineEvent));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void timelineEvent(ActionEvent e) {
        Bounds bounds = content.getBoundsInParent();

        for (MovingDot dot : dots) {
            dot.move(bounds);
        }
    }

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return this.random.nextInt((max - min) + 1) + min;
    }

    private File folderOfFile(File file) {
        String filePath = file.getAbsolutePath();
        int lastBackSlash = filePath.lastIndexOf("\\");
        String folder = filePath.substring(0, lastBackSlash);
        return new File(folder);
    }

    protected abstract void testMode();
}
