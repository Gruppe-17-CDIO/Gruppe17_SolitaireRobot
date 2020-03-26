package utilities;

import dataObjects.Card;
import dataObjects.SolitaireState;

import java.util.List;

public class StatePrinterUtility {
    static final String faceDown = "|‾‾‾‾‾‾‾‾‾‾|";
    static final String empty = "            ";

    /**
     * Method to print state to terminal (readable)
     *
     * @param state
     */
    public static void printState(SolitaireState state) {
        String[] line = new String[7]; // Table is 7 lines wide.
        String item = "";


        // Decoration
        for (int i = 0; i < 7; i++) {
            line[i] = "----------------";
        }
        printRow(line);

        // First text line
        if (state.isStockEmpty()) {
            item = "[Stock empty]";
        } else {
            item = "[Stock has more cards]";
        }
        line[0] = item;
        for (int i = 1; i < 7; i++) {
            line[i] = empty;
        }
        printRow(line);

        printEmptyRow();

        // Headers
        line[0] = "Drawn cards";
        line[1] = empty;
        line[2] = empty;
        line[3] = "Foundations";
        line[4] = empty;
        line[5] = empty;
        line[6] = empty;
        printRow(line);

        // Drawn cards
        List<Card> drawn = state.getDrawnCards();
        for (int i = 0; i < 3; i++) {
            if (drawn.get(i) == null) {
                line[i] = empty;
            } else {
                line[i] = drawn.get(i).toString();
            }
        }

        // Foundations
        Card[] foundations = state.getFoundations();
        for (int i = 0; i < 4; i++) {
            if (foundations[i] == null) {
                line[i + 3] = empty;
            } else {
                line[i + 3] = foundations[i].toString();
            }
        }
        printRow(line);

        printEmptyRow();

        // Piles, the 7 columns of cards
        List<List<Card>> piles = state.getPiles();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 7; j++) { // Loop reverted, outer loop is rows
                if (piles.get(j) == null) {
                    line[j] = empty;
                } else if (piles.get(j).size() < i + 1) {
                    line[j] = empty;
                } else if (piles.get(j).get(i).getStatus() == Card.Status.FACEDOWN) {
                    line[j] = faceDown;
                } else if (piles.get(j).get(i) != null) {
                    line[j] = piles.get(j).get(i).toString();
                }
            }
            printRow(line);
        }

        printEmptyRow();

        for (int i = 0; i < 7; i++) {
            line[i] = "----------------";
        }
        printRow(line);

    }

    private static void printRow(String[] s) {
        String line = "";
        for (int i = 0; i < 7; i++) {
            line += String.format("%-" + 16 + "s", s[i]);
        }
        System.out.println(line);
    }

    private static void printEmptyRow() {
        String[] line = new String[7];
        for (int i = 0; i < 7; i++) {
            line[i] = empty;
        }
        printRow(line);
    }
}

