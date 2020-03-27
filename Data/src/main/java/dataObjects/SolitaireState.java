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

    private boolean stockEmpty = true; // Cards to draw, face not visible
    private List<Card> drawnCards = new ArrayList<>(); // Drawn cards, 0 to 3
    private List<Card> foundations = new ArrayList<>(); // Four piles, goal, only top card visible
    private List<List<Card>> piles = new ArrayList<>(); // The seven rows

    public boolean isStockEmpty() {
        return stockEmpty;
    }

    public void setStockEmpty(boolean stockEmpty) {
        // Set this to true if the stock (draw pile) is empty.
        this.stockEmpty = stockEmpty;
    }

    public List<Card> getDrawnCards() {
        return drawnCards;
    }

    public void setDrawnCards(@NotNull List<Card> drawnCards) throws Exception {
        if (drawnCards == null) {
            throw new Exception("The list 'drawnCards' must not be null.");
        }
        if (drawnCards.size() > 3) {
            throw new Exception("Too many (visible) cards drawn: " + drawnCards.size() + ".");
        }
        this.drawnCards = drawnCards;
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
    public void addRowToPile(@NotNull List<Card> row) {
        piles.add(row);
    }

    public String getPrintFormat() throws Exception {
        // Returns human readable version of this class.
        return new StatePrinterUtility().getPrintFormat(this);
    }

}

