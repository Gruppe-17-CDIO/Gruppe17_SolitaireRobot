package stateBuilding;

import PropertyLoader.SinglePropertyLoader;
import dataObjects.Card;
import dataObjects.SolitaireState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StateGenerator {
    final String ROOT = SinglePropertyLoader.getInstance().getProperty("project.root");
    final String filepath = ROOT + "/Data/src/main/resources/builderFiles/build-a-state_";

    public SolitaireState getState(int id) throws Exception {
        String[][] data = readBuilderFile(id);
        return buildState(data);
    }

    SolitaireState buildState(String[][] data) throws Exception {
        SolitaireState state = new SolitaireState();

        // Set stock (cards left in pile)
        state.setStock(Integer.parseInt(data[0][0]));

        // Set drawn cards
        List<Card> drawn = new ArrayList<>();
        if (data[1].length > 0) {
            drawn.add(buildCard(data[1][0]));
        } else {
            drawn = null;
        }

        state.setDrawnCards(drawn);

        // Set foundations
        ArrayList<Card> foundations = new ArrayList<>();
        for (int i = 0; i < data[2].length; i++) {
            foundations.add(buildCard(data[2][i]));
        }

        while (foundations.size() < 4) {
            foundations.add(null);
        }

        state.setFoundations(foundations);

        // All piles in one List
        List<List<Card>> piles = new ArrayList<>();

        // Set piles
        for (int i = 3; i < 10; i++) {
            List<Card> pile = new ArrayList<>();
            for (String s : data[i]) {
                Card card = buildCard(s);
                if (card != null) {
                    pile.add(card);
                }
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
     * @param input A string to be parsed to Card constructor args
     * @return Card object
     * @throws Exception if card can't be parsed.
     */
    Card buildCard(String input) throws Exception {
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
    String[][] readBuilderFile(int id) throws Exception {
        SolitaireState state = new SolitaireState();
        String[][] data = new String[12][];
        int i = 0;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    filepath + id));
            String line = reader.readLine();
            while (line != null) {
                //System.out.println(line);
                if (line.length() != 0 && line.startsWith("#")) {
                    // If line starts with #, parse next line
                    line = reader.readLine();
                    String[] lines = line.split(",");

                    // Simple regex match for valid cards.
                    String regex = "((HEART|SPADE|CLUB|DIAMOND)) \\d+|FACEDOWN|\\d+|";

                    for (String s : lines) {
                        if (!Pattern.matches(regex, s.trim())) {
                            throw new Exception("Bad formatting: '" + s.trim() + "'.");
                        }
                    }
                    data[i++] = lines; // Beware, these are not trimmed!
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Could not parse " + filepath + +id +
                    ". \nMake sure the file exists and is formatted like " + filepath + 0 + ".");
            e.printStackTrace();
        }
        return data;
    }
}
