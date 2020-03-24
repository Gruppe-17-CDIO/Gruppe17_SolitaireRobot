import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

public class CardLogger implements I_CardLogger {
    static final String FILE_PATH = "src/main/resources/SolitaireData_" + new Timestamp(System.currentTimeMillis()).toString().substring(0, 16) + ".json";

    @Override
    public synchronized void logCards(SolitaireCards currentGameCards) {
        List<SolitaireCards> historyCards = getHistory();
        historyCards.add(currentGameCards);
        try {
            Writer writer = new FileWriter(FILE_PATH);
            new Gson().toJson(historyCards, writer);
            writer.close();
        } catch (IOException e) {
            System.out.println("StateSaver failed to log game data in the file '" + FILE_PATH + "'.");
            e.printStackTrace();
        }
    }

    // Returns states from this session and previous.
    @Override
    public List<SolitaireCards> getHistory() {
        List<SolitaireCards> history = null;
        try {
            FileReader reader = new FileReader(FILE_PATH);
            Type type = new TypeToken<List<SolitaireCards>>() {
            }.getType();
            history = new Gson().fromJson(reader, type);
        } catch (IOException i) {
            System.out.println("StateSaver failed to read game data from the file'" + FILE_PATH + "'.");
            i.printStackTrace();
        }
        if (history == null) {
            history = new ArrayList<>();
        }
        return history;
    }

    @Override
    public void deleteAllData() {
        Writer writer;
        try {
            writer = new FileWriter(FILE_PATH);
            new Gson().toJson(null, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
