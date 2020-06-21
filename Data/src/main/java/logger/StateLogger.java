package logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dataObjects.SolitaireState;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erlend
 */

public class StateLogger implements I_StateLogger {
    // Filepath for current session with timestamp
    Timestamp stamp = new Timestamp(System.currentTimeMillis());
    final String FILE_PATH = "Data/src/main/resources/SolitaireData_" +
            stamp.toString().substring(0, 10) + "_" + stamp.toString().substring(11, 22) +
            ".json";
    boolean newGame = true;

    @Override
    public synchronized void logState(SolitaireState state) {
        List<SolitaireState> historyCards;

        historyCards = getHistory();

        historyCards.add(state);
        try {
                Writer writer = new FileWriter(FILE_PATH);
                new Gson().toJson(historyCards, writer);
                writer.close();

        } catch (IOException e) {

            System.out.println("StateLogger failed to log game data in the file '" + FILE_PATH + "'.");
            e.printStackTrace();
        }
    }

    // Returns states from this session and previous.
    @Override
    public List<SolitaireState> getHistory() {
        List<SolitaireState> history = null;
        try {
            FileReader reader = new FileReader(FILE_PATH);
            Type type = new TypeToken<List<SolitaireState>>() {
            }.getType();
            history = new Gson().fromJson(reader, type);
        } catch (IOException i) {
            // Suppress error message if new history.
            if (!newGame) {
                System.out.println("StateLogger could not read file '" + FILE_PATH + "'.");
                //i.printStackTrace();
            }
            newGame = false;
        }
        if (history == null) {
            history = new ArrayList<>();
        }
        return history;
    }

    @Override
    public void deleteCurrentSessionData() {
        Writer writer;
        try {
            writer = new FileWriter(FILE_PATH);
            new Gson().toJson(null, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getLogFileName() {
        return FILE_PATH;
    }
}
