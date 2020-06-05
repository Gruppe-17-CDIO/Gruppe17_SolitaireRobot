package stateBuilding;

import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;

import java.util.List;

public class StatePrinterUtility {
    final String faceDown = "|‾‾‾‾‾‾‾‾‾‾|";
    final String empty = "                ";
    private final StringBuilder result = new StringBuilder();

    /**
     * Method to print state to terminal (readable)
     *
     * @param state the state to print.
     */
    public String getPrintFormat(SolitaireState state) throws Exception {
        String[] line = new String[7]; // Table is 7 lines wide.

        String item;

        System.out.println("SolitaireState ID:" + state.time);

        // Decoration
        for (int i = 0; i < 7; i++) {
            line[i] = "----------------";
        }
        printRow(line);

        // First text line
        line[0] = "[Stock: " + state.getStock() + "]";

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
        Card drawn = state.getDrawnCards().get(0);
        if (drawn == null) {
            line[0] = empty;
        } else {
            line[0] = drawn.toString();
        }

        // Two empty spaces after drawn card
        line[1] = empty;
        line[2] = empty;

        // Spaces next to drawn card:
        line[1] = empty;
        line[2] = empty;

        // Foundations
        List<Card> foundations = state.getFoundations();
        for (int i = 0; i < foundations.size(); i++) {
            if (foundations.get(i) == null) {
                line[i + 3] = empty;
            } else {
                line[i + 3] = foundations.get(i).toString();
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
                } else if (piles.get(j).size() - 1 < i || piles.get(j).get(i) == null) {
                    line[j] = empty;
                } else if (piles.get(j).get(i).getStatus() == Card.Status.FACEDOWN) {
                    line[j] = "|''''''''''" + i + "|";
                } else if (piles.get(j).get(i) != null) {
                    line[j] = piles.get(j).get(i).toString();
                } else {
                    throw new Exception("Bad input: " + piles.get(j).get(i).toString() + ".");
                }
            }
            // Ignore empty lines
            boolean print = false;
            for (String s : line) {
                if (!s.equals(empty)) {
                    print = true;
                    break;
                }
            }
            if (print) {
                printRow(line);
            }
        }

        printEmptyRow();

        for (int i = 0; i < 7; i++) {
            line[i] = "----------------";
        }
        printRow(line);

        // Print moves
        List<Move> moves = state.getSuggestedMoves();
        if (moves == null || moves.size() < 1) {
            result.append("No moves suggested.");
        } else {
            result.append("Possible moves: \n");
            for (int i = 0; i < moves.size(); i++) {
                result.append(i + ": " + moves.get(i) + "\n");
            }
        }

        return result.toString();
    }

    private void printRow(String[] s) {
        String line = "";
        for (int i = 0; i < 7; i++) {
            String temp = String.format("%-16s", s[i]);
            line += temp.substring(0, 16);
        }
        result.append(line + "\n");
    }

    private void printEmptyRow() {
        result.append("\n");
    }
}

