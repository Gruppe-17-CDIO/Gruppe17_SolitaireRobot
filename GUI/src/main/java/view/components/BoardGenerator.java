package view.components;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import view.MainGUI;

public class BoardGenerator {
    private final int DEFAULT_SPACING = 5;
    private final String STD_DECK_STYLE = "-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; " +
            "-fx-background-color: darkblue; -fx-background-insets: 3; -fx-background-radius: 5;";
    private final String STD_TURNED_CARD_STYLE = "-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; " +
            "-fx-background-color: white; -fx-background-insets: 1; -fx-background-radius: 5";
    private final String STD_MISSING_CARD_STYLE = "-fx-border-color: lightgrey; -fx-border-width: 1; -fx-border-radius: 5;";

    private VBox board;
    private HBox top, topLeft, topRight, bottom;

    // card 0-1 is deck and turned deck cards, 2-5 is the top right ace cards and 5-12 is the bottom cards
    private VBox[] cardBox;
    private Text[] cardValue;
    private String[] suitString = {"","","","","","","","","","","","",""};
    private ImageView[] cardSuitImg;
    private VBox[] highlightedCardBox = new VBox[2];
    private String[] highlightedCardStyle = new String[2];

    public BoardGenerator() {
        board = new VBox();
        top = new HBox();
        topLeft = new HBox();
        topRight = new HBox();
        bottom = new HBox();

        cardBox = new VBox[13];
        cardValue = new Text[13];
        cardSuitImg = new ImageView[13];

        board.setSpacing(20);
        top.setSpacing(DEFAULT_SPACING);
        topLeft.setSpacing(DEFAULT_SPACING);
        topRight.setSpacing(DEFAULT_SPACING);
        bottom.setSpacing(DEFAULT_SPACING);

        drawBoard();
    }

    private void drawBoard() {
        for (int i = 0; i < cardBox.length; i++) { /* Setting up the VBoxes for the cards*/
            cardBox[i] = new VBox();
            cardBox[i].setMinSize(75, 100);
            cardBox[i].setPadding(new Insets(5, 5, 5, 5));
            cardBox[i].setStyle(STD_MISSING_CARD_STYLE);

            if (i == 0) {
                // The deck (card backside style is set here)
                cardBox[i].setStyle(STD_DECK_STYLE);
                topLeft.getChildren().addAll(cardBox[i]);
            } else if (i == 1) {
                // The turned deck card
                topLeft.getChildren().addAll(cardBox[i]);

                // Empty spacer vbox
                VBox spacer = new VBox();
                spacer.setPrefHeight(100);
                spacer.setPrefWidth(75);
                topLeft.getChildren().addAll(spacer);
            } else if (i < 6) {
                // The top right cards
                topRight.getChildren().addAll(cardBox[i]);
            } else {
                // The bottom 7 cards
                bottom.getChildren().addAll(cardBox[i]);
            }

            // Alignment of the value text and suit image
            VBox cardContentAlignment = new VBox();
            BorderPane textAlignment = new BorderPane();
            textAlignment.setMaxWidth(15);
            cardValue[i] = new Text();
            textAlignment.setCenter(cardValue[i]);

            cardSuitImg[i] = new ImageView();
            cardSuitImg[i].setPreserveRatio(true);
            cardSuitImg[i].setFitHeight(15);
            cardSuitImg[i].setFitWidth(15);

            // Adding the value text and suit image to the card's box
            cardContentAlignment.getChildren().addAll(textAlignment, cardSuitImg[i]);
            cardBox[i].getChildren().addAll(cardContentAlignment);

        } /* VBox card setup end */

        // Adding the boxes to the board box
        top.getChildren().addAll(topLeft, topRight);
        board.getChildren().addAll(top, bottom);
    }

    public VBox getBoard() {
        return board;
    }

    public VBox addCard(int cardPlacement, String value, String suit) {
        // Input handling
        if (cardPlacement < 1 || cardPlacement > 12) {
            System.out.println("[ERROR] Incorrect index: " + cardPlacement + ", try again");
            return board;
        }
        if (!(suit.equals("club") || suit.equals("diamond") || suit.equals("heart") || suit.equals("spade"))) {
            System.out.println("[ERROR] Unknown suit type: " + suit + ", try again");
            return board;
        }

        // Styling of the card
        cardBox[cardPlacement].setStyle(STD_TURNED_CARD_STYLE);

        // The text showing the card's value
        cardValue[cardPlacement].setText(value);

        // The image showing the card's suit
        suitString[cardPlacement] = suit;
        cardSuitImg[cardPlacement].setImage(new Image(getClass().getResourceAsStream("/" + suit + "24.png")));

        return board;
    }

    public void highlightMove(int moveFromPlacement, int moveToPlacement) {
        if (moveFromPlacement < 0 || moveFromPlacement > 12 || moveToPlacement < 1 || moveToPlacement > 12) {
            System.out.println("[ERROR] Incorrect move attempt. Tried moving: " +
                    moveFromPlacement + " to " + moveToPlacement);

            clearHighligt();
            return;
        }

        // Return the previously hightlighted cards to their original look
        clearHighligt();

        // Save their styles so they can be returned to their original looks
        highlightedCardBox[0] = cardBox[moveFromPlacement];
        highlightedCardStyle[0] = cardBox[moveFromPlacement].getStyle();
        highlightedCardBox[1] = cardBox[moveToPlacement];
        highlightedCardStyle[1] = cardBox[moveToPlacement].getStyle();

        // Change the style of the cards to highlight
        if (cardBox[moveFromPlacement].getStyle().equals(STD_DECK_STYLE)) {
            cardBox[moveFromPlacement].setStyle("-fx-border-color: green; -fx-border-width: 2; -fx-border-radius: 5;" +
                    "-fx-background-color: darkblue; -fx-background-insets: 3; -fx-background-radius: 5;");
        } else if (cardBox[moveFromPlacement].getStyle().equals(STD_TURNED_CARD_STYLE)) {
            cardBox[moveFromPlacement].setStyle("-fx-border-color: green; -fx-border-width: 2; -fx-border-radius: 5;" +
                    "-fx-background-color: white; -fx-background-insets: 1; -fx-background-radius: 5");
        } else {
            // For STD_MISSING_CARD_STYLE
            cardBox[moveFromPlacement].setStyle("-fx-border-color: green; -fx-border-width: 2; -fx-border-radius: 5;");
        }

        if (cardBox[moveToPlacement].getStyle().equals(STD_DECK_STYLE)) {
            cardBox[moveToPlacement].setStyle("-fx-border-color: darkred; -fx-border-width: 2; -fx-border-radius: 5;" +
                    "-fx-background-color: darkblue; -fx-background-insets: 3; -fx-background-radius: 5;");
        } else if (cardBox[moveToPlacement].getStyle().equals(STD_TURNED_CARD_STYLE)) {
            cardBox[moveToPlacement].setStyle("-fx-border-color: darkred; -fx-border-width: 2; -fx-border-radius: 5;" +
                    "-fx-background-color: white; -fx-background-insets: 1; -fx-background-radius: 5");
        } else {
            // For STD_MISSING_CARD_STYLE
            cardBox[moveToPlacement].setStyle("-fx-border-color: darkred; -fx-border-width: 2; -fx-border-radius: 5;");
        }

        if (!(cardValue[moveFromPlacement].getText().equals("") || cardValue[moveFromPlacement].getText().equals(""))) {
            MainGUI.printToOutputAreaNewline("Move " + cardValue[moveFromPlacement].getText() + " of " +
                    suitString[moveFromPlacement] + "s (GREEN)" + " to " + cardValue[moveToPlacement].getText() + " of " +
                    suitString[moveToPlacement] + "s (RED)");
        } else {
            MainGUI.printToOutputAreaNewline("Move green to red");
        }
    }

    // Return the previously hightlighted cards to their original look
    public void clearHighligt() {
        for (int i = 0; i < highlightedCardBox.length; i++) {
            if (highlightedCardBox[i] != null) {
                highlightedCardBox[i].setStyle(highlightedCardStyle[i]);
                highlightedCardBox[i] = null;
                highlightedCardStyle[i] = null;
            }
        }
    }

}
