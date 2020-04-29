package dataObjects;

import org.jetbrains.annotations.NotNull;
import utilities.StatePrinterUtility;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andreas, Erlend
 * This class represents state, or 'all cards on the table'.
 * <p>
 * 'stock' is the pile you draw from, a boolean for empty or not empty.
 * 'drawnCards' is a list of 0 - 3 cards that are currently drawn.
 * 'foundations' are the sorted cards, the result of the game. 4 top cards are visible.
 * 'piles' (List of 7 Lists) are the seven columns of cards.
 * <p>
 * Note that the drawnCards, foundations and piles can be empty lists, but not null.
 * The inner lists handle null by making a new list.
 */

public class SolitaireState {
    public final String time = new Timestamp(System.currentTimeMillis()).toString();// Timestamp ID for test and log

    private int stock = 52; // Cards to draw, face not visible
    private Card drawnCard = null; // Drawn card, just one
    private List<Card> foundations = new ArrayList<>(); // Four piles, goal, only top card visible
    private List<List<Card>> piles = new ArrayList<>(); // The seven rows
    private List<Move> suggestedMoves = new ArrayList<>(); // Moves to do based on this state

    public SolitaireState() {
        for (int i = 0; i < 4; i++) {
             foundations.add(new Card(Card.Status.FACEDOWN));
        }
        for (int i = 0; i < 7; i++) {
            piles.add(new ArrayList<>());
        }
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Card getDrawnCard() {
        return drawnCard;
    }

    public void setDrawnCard(Card drawnCard) {
        this.drawnCard = drawnCard;
    }

    public List<Card> getFoundations() {
        return foundations;
    }

    public void setFoundations(@NotNull ArrayList<Card> foundations) throws Exception {
        if (foundations == null) {
            throw new Exception("The List 'foundations' must not be null.");
        }
        if (foundations.size() > 4) {
            throw new Exception("Maximum four foundations (including empty piles), was " + foundations.size() + ".");
        }
        this.foundations = foundations;
    }

    public List<List<Card>> getPiles() {
        return piles;
    }

    public void setPiles(@NotNull List<List<Card>> piles) throws Exception {
        if (piles.size() != 7) {
            throw new Exception("Should always be seven piles (including empty piles), was " + piles.size() + ".");
        }
        for (int i = 0; i < piles.size(); i++) {
            if (piles.get(i) == null) {
                piles.add(i, new ArrayList<>());
            }
        }
        this.piles = piles;
    }


    // Add one row to pile at a time.
    public void addColumnToPile(@NotNull List<Card> col) {
        piles.add(col);
    }

    public String toString() {
        return time;
    }

    public String getPrintFormat() throws Exception {
        // Returns human readable version of this class.
        return new StatePrinterUtility().getPrintFormat(this);
    }

    public List<Move> getSuggestedMoves() {
        return suggestedMoves;
    }

    public void setSuggestedMoves(List<Move> suggestedMoves) {
        this.suggestedMoves = suggestedMoves;
    }
}

