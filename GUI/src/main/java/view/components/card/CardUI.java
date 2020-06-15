package view.components.card;

import dataObjects.Card;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import view.MainGUI;
import view.components.FxUtil;

/**
 * @author Rasmus Sander Larsen
 */
public class CardUI extends VBox {

    //-------------------------- Fields --------------------------

    public static final int CARD_HEIGHT = 70;
    public static final int CARD_WIDTH = 54;
    public static final int CARD_PADDING = 3;
    public static final int CARD_HEIGHT_PADDED = 70+(2*CARD_PADDING);
    public static final int CARD_WIDTH_PADDED = 54 +(2*CARD_PADDING);
    private final int SPACING = 3;
    private final int CORNER_WIDTH = 12;
    private final String BACK_STYLE = "-fx-background-color: white,  darkred;-fx-background-insets: 0, 4; -fx-background-radius: 6;-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5;";
    private final String FRONT_STYLE = "-fx-background-color: white; -fx-background-radius: 6;-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5;";

    private final SuitEnum.ImageSize DEFAULT_SUIT_IMAGE_SIZE = SuitEnum.ImageSize.px24;

    private String value;
    private int rank;
    private SuitEnum suit;

    public int pressCounterBoolean = 0;
    private boolean isFrontShowing = true;

    private FlowPane emptyTopSpace;
    private GridPane cardCornerGrid;
    private Text valueText;
    private ImageView suitImageV;
    private ImageView suitImageH;

    //----------------------- Constructor -------------------------

    public CardUI() {
        applySettings();
        //applyOnPressFlipsCard();
    }

    public CardUI(String value, SuitEnum suit) {
        applySettings();

        setValueAndApply(value);
        setSuitAndApply(suit);

        //applyOnPressFlipsCard();
    }

    //------------------------ Properties -------------------------

    // region Properties

    public SuitEnum getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public int getRank() {
        return rank;
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

    public FlowPane getEmptyTopSpace() {
        return emptyTopSpace;
    }

    // endregion

    //---------------------- Public Methods -----------------------

    public String toString () {
        return "Rank:" + rank + ", Suit: " + suit;
    }

    public static CardUI ofCard(Card card) {
        CardUI cardUI = new CardUI();
        cardUI.setValueAndApply(card.getRank());
        cardUI.setSuitAndApply(SuitEnum.ofSuit(card.getSuit()));
        return cardUI;
    }

    public void setValueAndApply(String value) {
        this.value = value;
        valueText.setText(value);
    }

    public void setValueAndApply(int rank) {
        this.rank = rank;
        switch (rank) {
            case 1:
                setValueAndApply("A");
                break;
            case 2:
                setValueAndApply("2");
                break;
            case 3:
                setValueAndApply("3");
                break;
            case 4:
                setValueAndApply("4");
                break;
            case 5:
                setValueAndApply("5");
                break;
            case 6:
                setValueAndApply("6");
                break;
            case 7:
                setValueAndApply("7");
                break;
            case 8:
                setValueAndApply("8");
                break;
            case 9:
                setValueAndApply("9");
                break;
            case 10:
                setValueAndApply("10");
                break;
            case 11:
                setValueAndApply("J");
                break;
            case 12:
                setValueAndApply("Q");
                break;
            case 13:
                setValueAndApply("K");
                break;
            default:
                break;

        }
    }

    public void setSuitAndApply(SuitEnum suit) {
        this.suit = suit;
        suitImageH.setImage(suit.getImage(DEFAULT_SUIT_IMAGE_SIZE));
        suitImageV.setImage(suit.getImage(DEFAULT_SUIT_IMAGE_SIZE));
    }

    public void showBackside(){
        // Set Style
        cardCornerGrid.setStyle(BACK_STYLE);

        // Hide Values and Suit
        if (valueText != null) {
            valueText.setVisible(false);
        }
        if (suitImageV != null) {
            suitImageV.setVisible(false);
            suitImageH.setVisible(false);
        }

        isFrontShowing = false;
    }

    public void showFrontSide(){
        // Set Style
        cardCornerGrid.setStyle(FRONT_STYLE);

        // Hide Value and Suit
        if (valueText != null && suitImageV != null) {
            valueText.setVisible(true);
            suitImageV.setVisible(true);
            suitImageH.setVisible(true);
        }

        isFrontShowing = true;
    }

    public void flipCard() {
        if(isFrontShowing) {
            showBackside();
        } else {
            showFrontSide();
        }
    }

    public int press (){
        return pressCounterBoolean = ++pressCounterBoolean %2;
    }

    public void setEmptySpaceHeight (int height) {
        emptyTopSpace.setMaxHeight(height);
        emptyTopSpace.setMinHeight(height);
    }

    //---------------------- Support Methods ----------------------    

    private void applySettings () {
        setMinSize(CARD_WIDTH,CARD_HEIGHT);
        setMinSize(CARD_WIDTH,CARD_HEIGHT);
        setAlignment(Pos.TOP_CENTER);

        emptyTopSpace = FxUtil.emptySpace(Orientation.VERTICAL,0);

        cardCornerGrid = new GridPane();
        cardCornerGrid.setMinSize(CARD_WIDTH,CARD_HEIGHT);
        cardCornerGrid.setMaxSize(CARD_WIDTH,CARD_HEIGHT);
        cardCornerGrid.setStyle(FRONT_STYLE);

        cardCornerGrid.setAlignment(Pos.TOP_LEFT);
        cardCornerGrid.setVgap(SPACING);
        cardCornerGrid.setHgap(SPACING);
        cardCornerGrid.setPadding(new Insets(SPACING));

        for (int i = 0; i < 2; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(CORNER_WIDTH);
            columnConstraints.setMaxWidth(CORNER_WIDTH);
            columnConstraints.setHalignment(HPos.CENTER);
            cardCornerGrid.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < 2; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(CORNER_WIDTH);
            rowConstraints.setMaxHeight(CORNER_WIDTH);
            rowConstraints.setValignment(VPos.CENTER);
            cardCornerGrid.getRowConstraints().add(rowConstraints);
        }

        getChildren().addAll(emptyTopSpace, cardCornerGrid);

        addCardCornerValues();
    }

    private void addCardCornerValues() {
        valueText = new Text();
        valueText.setFont(FxUtil.fontDefault(12));
        valueText.setTextAlignment(TextAlignment.CENTER);
        //valueText.setWrappingWidth(CORNER_WIDTH);
        cardCornerGrid.add(valueText,0,0);

        suitImageV = new ImageView();
        suitImageV.setPreserveRatio(true);
        suitImageV.setFitWidth(CORNER_WIDTH);
        cardCornerGrid.add(suitImageV,0,1);

        suitImageH = new ImageView();
        suitImageH.setPreserveRatio(true);
        suitImageH.setFitWidth(CORNER_WIDTH);
        cardCornerGrid.add(suitImageH,1,0);
    }

    private void applyOnPressFlipsCard () {
        pressedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (press()%2==0){
                    flipCard();
                    MainGUI.printToOutputAreaNewline("Flipped");
                }
            }
        });
    }

}
