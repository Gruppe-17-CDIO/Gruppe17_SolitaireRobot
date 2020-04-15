package view.components.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import view.components.FxUtil;

/**
 * @author Rasmus Sander Larsen
 */
public class CardUIGridPane extends VBox {

    //-------------------------- Fields --------------------------

    public static final int CARD_HEIGHT = 70;
    public static final int CARD_WIDTH = 55;
    private final int SPACING = 3;
    private final int CORNER_WIDTH = 15;
    private final String BACK_STYLE = "-fx-background-color: darkred;-fx-background-insets: 4;-fx-background-radius: 5;-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; ";
    private final String FRONT_STYLE = "-fx-background-color: white; -fx-background-radius: 6;-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5";

    private final SuitEnum.ImageSize DEFAULT_SUIT_IMAGE_SIZE = SuitEnum.ImageSize.px24;

    private String value;
    private SuitEnum suit;
    public int pressCounterBoolean = 0;
    private boolean isFrontShowing = true;

    private FlowPane emptyTopSpace;
    private GridPane cardGrid;
    private Text valueText;
    private ImageView suitImageV;
    private ImageView suitImageH;

    //----------------------- Constructor -------------------------

    public CardUIGridPane() {
        applySettings();
        applyOnPressFlipsCard();
    }

    public CardUIGridPane(String value, SuitEnum suit) {
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

    public String getValue() {
        return value;
    }

    public boolean isFrontShowing() {
        return isFrontShowing;
    }

    public void setFrontShowing(boolean frontShowing) {
        isFrontShowing = frontShowing;
    }

    public void setEmptyTopSpace(FlowPane emptyTopSpace) {
        this.emptyTopSpace = emptyTopSpace;
    }

    // endregion

    //---------------------- Public Methods -----------------------

    public void showBackside(){
        if (valueText != null) {
            valueText.setVisible(false);
        }
        if (suitImageV != null) {
            suitImageV.setVisible(false);
            suitImageH.setVisible(false);
        }
        setStyle(BACK_STYLE);
        isFrontShowing = false;
    }

    public void showFrontside(){
        if (valueText != null && suitImageV != null) {
            valueText.setVisible(true);
            suitImageV.setVisible(true);
            suitImageH.setVisible(true);
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

    public void setEmptySpaceHeight (int height) {
        emptyTopSpace = FxUtil.emptySpace(Orientation.VERTICAL,height);
    }

    //---------------------- Support Methods ----------------------    

    private void applySettings () {
        setMinSize(CARD_WIDTH,CARD_HEIGHT);
        setMaxSize(CARD_WIDTH,CARD_HEIGHT);
        setAlignment(Pos.TOP_CENTER);

        emptyTopSpace = FxUtil.emptySpace(Orientation.VERTICAL,0);

        cardGrid = new GridPane();
        cardGrid.setMinSize(CARD_WIDTH,CARD_HEIGHT);
        cardGrid.setMaxSize(CARD_WIDTH,CARD_HEIGHT);
        cardGrid.setStyle(FRONT_STYLE);

        cardGrid.setAlignment(Pos.TOP_LEFT);
        cardGrid.setVgap(SPACING);
        cardGrid.setHgap(SPACING);
        cardGrid.setPadding(new Insets(SPACING));

        for (int i = 0; i < 2; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(CORNER_WIDTH);
            columnConstraints.setMaxWidth(CORNER_WIDTH);
            columnConstraints.setHalignment(HPos.CENTER);
            cardGrid.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < 2; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(CORNER_WIDTH);
            rowConstraints.setMaxHeight(CORNER_WIDTH);
            rowConstraints.setValignment(VPos.CENTER);
            cardGrid.getRowConstraints().add(rowConstraints);
        }



        getChildren().addAll(emptyTopSpace,cardGrid);
    }

    private void fillCardWithValues () {
        valueText = new Text(value);
        valueText.setFont(FxUtil.fontDefault(12));
        valueText.setTextAlignment(TextAlignment.CENTER);
        valueText.setWrappingWidth(CORNER_WIDTH);
        cardGrid.add(valueText,0,0);

        suitImageV = new ImageView();
        suitImageV.setImage(suit.getImage(DEFAULT_SUIT_IMAGE_SIZE));
        suitImageV.setPreserveRatio(true);
        suitImageV.setFitWidth(CORNER_WIDTH);
        cardGrid.add(suitImageV,0,1);

        suitImageH = new ImageView();
        suitImageH.setImage(suit.getImage(DEFAULT_SUIT_IMAGE_SIZE));
        suitImageH.setPreserveRatio(true);
        suitImageH.setFitWidth(CORNER_WIDTH);
        cardGrid.add(suitImageH,1,0);
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
