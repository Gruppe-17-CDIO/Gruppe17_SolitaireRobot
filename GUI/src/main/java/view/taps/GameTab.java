package view.taps;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.components.CardGenerator;
import view.components.TabStd;

import java.util.ArrayList;

public class GameTab extends TabStd {

    public GameTab() {
        super("Game",
                "",
                "");

        /*
        HBox outer = new HBox();
        outer.setSpacing(10);

        String[] testValues = {"5", "K", "A", "9", "2", "J", "10"};
        String[] testSuit = {"spade", "diamond", "diamond", "spade", "club", "heart", "diamond"};

        CardGenerator cg = new CardGenerator();
        ArrayList<VBox> cards = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            cards.add(cg.makeCard(testValues[i],testSuit[i]));
        }

        outer.getChildren().addAll(cards);
        */

        CardGenerator cg = new CardGenerator();
        addAllToContent(cg.getBoard());
    }

    @Override
    protected void testMode() {

    }
}
