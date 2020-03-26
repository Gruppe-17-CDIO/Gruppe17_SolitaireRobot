package view.components.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import view.components.FxUtil;

/**
 * @author Rasmus Sander Larsen
 */
public class CardUI extends VBox {

    //-------------------------- Fields --------------------------

    private final int CARD_HEIGHT = 75;
    private final int CARD_WIDTH = 60;
    private final int SPACING = 3;
    private final int CORNER_WIDTH = 15;
    private final String BACK_STYLE = "-fx-background-color: darkred;-fx-background-insets: 4;-fx-background-radius: 5;-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; ";
    private final String FRONT_STYLE = "-fx-background-color: white;-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5";

    private final SuitEnum.ImageSize DEFAULT_SUIT_IMAGE_SIZE = SuitEnum.ImageSize.px24;

    private String value;
    private SuitEnum suit;
    public int pressCounterBoolean = 0;
    private boolean isFrontShowing = true;

    private Text valueText;
    private ImageView suitImage;

    //----------------------- Constructor -------------------------

    public  CardUI () {
        applySettings();
        applyOnPressFlipsCard();
    }

    public CardUI (String value, SuitEnum suit) {
        this.value  = value;
        this.suit   = suit;

        applySettings();
        fillCardWithValues();
    }

    //------------------------ Properties -------------------------

    // region Properties

    public SuitEnum getSuit() {
        return suit;
    }

    public boolean isFrontShowing() {
        return isFrontShowing;
    }

    public void setFrontShowing(boolean frontShowing) {
        isFrontShowing = frontShowing;
    }

    // endregion

    //---------------------- Public Methods -----------------------

    public void showBackside(){
        if (valueText != null) {
            valueText.setVisible(false);
        }
        if (suitImage != null) {
            suitImage.setVisible(false);
        }
        setStyle(BACK_STYLE);
        isFrontShowing = false;
    }

    public void showFrontside(){
        if (valueText != null && suitImage != null) {
            valueText.setVisible(true);
            suitImage.setVisible(true);
        }
        setStyle(FRONT_STYLE);
        isFrontShowing = true;
    }

    public void flipCard() {
        if(isFrontShowing) {
            showBackside();
        } else {
            showFrontside();
        }
    }

    public int press (){
        return pressCounterBoolean = ++pressCounterBoolean %2;
    }


    //---------------------- Support Methods ----------------------    

    private void applySettings () {
        setMinSize(CARD_WIDTH,CARD_HEIGHT);
        setMaxSize(CARD_WIDTH,CARD_HEIGHT);
        setStyle(FRONT_STYLE);
        setPadding(new Insets(SPACING,SPACING,SPACING,SPACING));
        setSpacing(SPACING);
    }

    private void fillCardWithValues () {
        valueText = new Text(value);
        valueText.setFont(FxUtil.fontDefault(12));
        valueText.setTextAlignment(TextAlignment.CENTER);
        valueText.setWrappingWidth(CORNER_WIDTH);

        suitImage = new ImageView();
        suitImage.setImage(suit.getImage(DEFAULT_SUIT_IMAGE_SIZE));
        suitImage.setPreserveRatio(true);
        suitImage.setFitWidth(CORNER_WIDTH);

        getChildren().addAll(valueText,suitImage);
    }

    private void applyOnPressFlipsCard () {
        pressedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (press()%2==0){
                    flipCard();
                }
            }
        });
    }

}
