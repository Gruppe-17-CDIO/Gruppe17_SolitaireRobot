package view.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import view.MainGUI;
import view.components.card.CardUI;

/**
 * @author Rasmus Sander Larsen
 */
public class CardStackPane extends StackPane {

    //-------------------------- Fields --------------------------

    private final String CARD_OUTLINE = "-fx-border-color: grey; -fx-border-width: 1; -fx-border-radius: 5;";
    private final String HIGHLIGHT_OUTLINE = ";-fx-border-color: darkgreen; -fx-border-width: "+(CardUI.CARD_PADDING+2)+"; -fx-border-radius: 5;";
    private final Insets DEFAULT_MARGIN = new Insets(CardUI.CARD_PADDING,0,CardUI.CARD_PADDING,0);

    private int pressCounterBoolean = 0;
    private boolean isHighlighted = false;

    protected HBox outlineBox;
    protected HBox highLightBox;
    protected StackPane cardStackPane;

    //----------------------- Constructor -------------------------

    public CardStackPane() {
        defaultSettings();

        // applyOnClickAction();
    }

    //------------------------ Properties -------------------------

    // region Properties

    public String getCARD_OUTLINE() {
        return CARD_OUTLINE;
    }

    public String getHIGHLIGHT_OUTLINE() {
        return HIGHLIGHT_OUTLINE;
    }

    public int getPressCounterBoolean() {
        return pressCounterBoolean;
    }

    public void setPressCounterBoolean(int pressCounterBoolean) {
        this.pressCounterBoolean = pressCounterBoolean;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public HBox getOutlineBox() {
        return outlineBox;
    }

    public void setOutlineBox(HBox outlineBox) {
        this.outlineBox = outlineBox;
    }

    // endregion

    //---------------------- Public Methods -----------------------

    public void highlight(boolean toHighlight) {
        if (toHighlight) {
            highLightBox.setVisible(true);
            outlineBox.setVisible(false);
        } else {
            highLightBox.setVisible(false);
            outlineBox.setVisible(true);
        }
    }

    public void showCards(boolean showCards) {
        cardStackPane.setVisible(showCards);
    }

    public void addCardToStackPane (CardUI cardUI) {
        cardStackPane.getChildren().add(cardUI);
    }

    public CardUI drawTopCardFromStackPane() {
        int sizeOfStackPaneChildren = getChildren().size();
        CardUI topCard = null;
        if (sizeOfStackPaneChildren != 1) {
            try {
                topCard = (CardUI) getChildren().get(sizeOfStackPaneChildren-1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return topCard;
    }

    public void clearCardStackPane() {
        cardStackPane.getChildren().removeAll(cardStackPane.getChildren());
    }

    //---------------------- Support Methods ----------------------    

    private void defaultSettings(){
        setMinSize(CardUI.CARD_WIDTH_PADDED,CardUI.CARD_HEIGHT_PADDED);
        setMaxSize(CardUI.CARD_WIDTH_PADDED,CardUI.CARD_HEIGHT_PADDED);
        setAlignment(Pos.TOP_CENTER);

        // Adds OutlineBox, HighlightBox and the stackPane for the CardUI's
        createAndAddOutLineBox();
        createAndAddHighLightBox();
        createAndAddCardStackPane();

    }

    private void applyOnClickAction() {
        pressedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (press()%2==0){
                    isHighlighted = !isHighlighted;
                    highlight(isHighlighted);
                    MainGUI.printToOutputAreaNewline("No of Children: " + getChildren().size());
                    MainGUI.printToOutputAreaNewline("No of Children in cardStackPane: " + cardStackPane.getChildren().size());
                    MainGUI.printToOutputAreaNewline("StackPane Height: " +getMaxHeight());
                    int cardCounter = 0;
                    for (Node node : cardStackPane.getChildren()) {
                        if (node instanceof CardUI) {
                            cardCounter++;
                            double emptySpaceHeight = ((CardUI) node).getEmptyTopSpace().getMaxHeight();
                            MainGUI.printToOutputAreaNewline("Card: " + cardCounter + " EmptyHeight: " + emptySpaceHeight);
                        }
                    }
                }
            }
        });
    }

    private void createAndAddOutLineBox() {
        outlineBox = new HBox();
        outlineBox.setStyle(CARD_OUTLINE);
        outlineBox.setMinSize(CardUI.CARD_WIDTH,CardUI.CARD_HEIGHT);
        outlineBox.setMaxSize(CardUI.CARD_WIDTH,CardUI.CARD_HEIGHT);
        setMargin(outlineBox,DEFAULT_MARGIN);
        getChildren().add(outlineBox);
    }


    private void createAndAddHighLightBox() {
        highLightBox = new HBox();
        highLightBox.setStyle(HIGHLIGHT_OUTLINE);
        highLightBox.setMinSize(CardUI.CARD_WIDTH_PADDED,CardUI.CARD_HEIGHT_PADDED);
        highLightBox.setMaxSize(CardUI.CARD_WIDTH_PADDED,CardUI.CARD_HEIGHT_PADDED);
        highlight(false);
        getChildren().add(highLightBox);
    }

    private void createAndAddCardStackPane() {
        cardStackPane = new StackPane();
        cardStackPane.setAlignment(Pos.TOP_CENTER);
        cardStackPane.setMinSize(CardUI.CARD_WIDTH_PADDED,CardUI.CARD_HEIGHT_PADDED);
        cardStackPane.setMaxSize(CardUI.CARD_WIDTH_PADDED,CardUI.CARD_HEIGHT_PADDED);
        setMargin(cardStackPane, DEFAULT_MARGIN);
        getChildren().add(cardStackPane);
    }

    public int press (){
        return pressCounterBoolean = ++pressCounterBoolean %2;
    }
}
