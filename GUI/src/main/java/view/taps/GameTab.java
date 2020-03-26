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

        CardGenerator cg = new CardGenerator();
        cg.addCard(6, "J", "heart");
        addAllToContent(cg.getBoard());
    }

    @Override
    protected void testMode() {

    }
}
