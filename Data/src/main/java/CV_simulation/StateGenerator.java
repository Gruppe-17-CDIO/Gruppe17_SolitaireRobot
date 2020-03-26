package CV_simulation;

import dataObjects.Card;
import dataObjects.SolitaireState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StateGenerator {
    static final String PATH = "src/main/resources/builderFiles/build-a-state_";

    public static SolitaireState getState(int id) throws Exception {
        String[][] data = readBuilderFile(id);
        return buildState(data);
    }

    static SolitaireState buildState(String[][] data) throws Exception {
        SolitaireState state = new SolitaireState();

        // Set stock present
        if (data[0][0].equals("true")) {
            state.setStockEmpty(true);
        } else {
            state.setStockEmpty(false);
        }

        // Set drawn cards
        List<Card> drawn = new ArrayList<>();
        if (data[1].length > 0) {
            for (String s : data[1]) {
                if (s.length() > 0) {
                    drawn.add(buildCard(s));
                }
            }
        }
        state.setDrawnCards(drawn);

        // Set foundations
        ArrayList<Card> foundations = new ArrayList<>();
        for (int i = 0; i < data[2].length; i++) {
            foundations.add(buildCard(data[2][i]));
        }
        state.setFoundations(foundations);

        // All piles in one List
        List<List<Card>> piles = new ArrayList<>();

        // Set piles
        for (int i = 3; i < 10; i++) {

            //System.out.println("This is pile " + (i - 2));
            List<Card> pile = new ArrayList<>();
            for (String s : data[i]) {
                //System.out.println(s);
                pile.add(buildCard(s));
            }
            piles.add(pile);
        }
        state.setPiles(piles);
        //state.printState();
        return state;
    }

    /**
     * Parses string to Card
     *
     * @param input A string to be prsed to Card construcotr args
     * @return Card object
     * @throws Exception if card can't be parsed.
     */
    static Card buildCard(String input) throws Exception {
        input = input.trim();
        String[] in = input.split(" ");
        if (input.length() > 0) {
            switch (in[0]) {
                case "FACEDOWN":
                    return new Card(Card.Status.FACEDOWN);
                case "SPADE":
                    return new Card(Card.Suit.SPADE, Integer.parseInt(in[1]));
                case "HEART":
                    return new Card(Card.Suit.HEART, Integer.parseInt(in[1]));
                case "DIAMOND":
                    return new Card(Card.Suit.DIAMOND, Integer.parseInt(in[1]));
                case "CLUB":
                    return new Card(Card.Suit.CLUB, Integer.parseInt(in[1]));
                default:
                    throw new Exception("Bad card input: '" + input + "'.");
            }
        }
        return null;
    }

    /**
     * A Method to read a state build-a-state file, and parse it to lists of tokens
     *
     * @param id id of the file to read
     * @return list of token lists
     */
    static String[][] readBuilderFile(int id) {
        SolitaireState state = new SolitaireState();
        String[][] data = new String[12][];
        int i = 0;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    PATH + id));
            String line = reader.readLine();
            while (line != null) {
                //System.out.println(line);
                if (line.length() != 0 && line.substring(0, 1).equals("#")) {
                    line = reader.readLine();
                    String[] lines = line.split(",");
                    data[i++] = lines; // Beware, these are not trimmed!
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Could not parse " + PATH + id + ". \nMake sure the file exists and is formatted like " + PATH + 0 + ".");
            e.printStackTrace();
        }
        return data;
    }
}
