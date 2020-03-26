package dataObjects;

/**
 * This class represent a single card-object.
 *
 * @author Andreas
 */

public class Card {
    private int rank;
    private Suit suit;
    private Status status;

    /*
    Constructor for testing
    */
    public Card(Suit suit, int rank) throws Exception {
        if (rank < 0 || rank > 13) {
            throw new Exception("Invalid card: " + rank + " " + suit + ". Use 1 to 13.");
        }
        this.suit = suit;
        this.rank = rank;
        this.status = Status.FACEUP;
    }

    public Card(Status status) {
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
            throw new Exception("Asked card for a card rank that doesn't exist " + rank + ".");
        }
        return rank + 1;
    }

    /*
    return the lower rank (The card value of the next crd in the line ).
    This method have no use so far, but I thought it would be relevant later on.
     */
    public int GetPreviousRank() throws Exception {
        if (rank <= 1) {
            throw new Exception("Asked card for a card rank that doesn't exist " + rank + ".");
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

