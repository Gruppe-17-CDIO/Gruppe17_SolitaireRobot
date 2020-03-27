package dataObjects;

import org.jetbrains.annotations.NotNull;

/**
 * This class represent a single card-object.
 * <p>
 * There are two constructors:
 * 1. Constructor for a card with face up, which takes suit and rank as arguments.
 * 2. Constructor for a card that lies facedown on the table. This takes the argument 'Card.Status.FACEDOWN'.
 * <p>
 * A card should always be tested for status (myCard.getStatus() == Card.Status.FACEUP) before asking suit
 * and rank, or you could get a nullpointer exception.
 * <p>
 * There are return methods for 'suit', 'rank', and 'color'. Remember that color is not rank, color is red
 * or black.
 *
 * @author Andreas & Erlend
 */

public class Card {
    private int rank;
    private Suit suit;
    private Status status;

    /*
    Constructor for testing
    */
    public Card(@NotNull Suit suit, int rank) throws Exception {
        if (rank < 1 || rank > 13) {
            throw new Exception("Invalid card: " + rank + " " + suit + ". Use 1 to 13.");
        }
        this.suit = suit;
        this.rank = rank;
        this.status = Status.FACEUP;
    }

    public Card(@NotNull Status status) {
        this.status = status;
    }

    // RED OR BLACK!
    public CardColor getColor() {
        return suit == Suit.SPADE || suit == Suit.CLUB ? CardColor.BLACK : CardColor.RED;
    }

    public Suit getSuit() {
        return suit;
    }

    public Status getStatus() {
        return status;
    }

    public String toString() {
        if (this.status == Status.FACEDOWN) {
            return status + "";
        }
        return suit + " " + rank;
    }

    /*
    return the rank of upper rank (The card value of the next crd in the line ).
    This method have no use so far, but I thought it would be relevant later on.
   */
    public int GetNextRank() throws Exception {
        if (rank >= 13) {
            throw new Exception("Asked card for a rank that doesn't exist " + rank + ".");
        }
        return rank + 1;
    }

    /*
    return the lower rank (The card value of the next crd in the line ).
    This method have no use so far, but I thought it would be relevant later on.
     */
    public int GetPreviousRank() throws Exception {
        if (rank <= 1) {
            throw new Exception("Asked card for a crank that doesn't exist " + rank + ".");
        }
        return rank - 1;
    }

    public enum Suit {
        SPADE,
        HEART,
        DIAMOND,
        CLUB,
    }

    public enum Status {
        FACEDOWN,
        FACEUP
    }

    public enum CardColor {
        RED,
        BLACK,
    }

}

