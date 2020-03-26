package view.taps;

import javafx.scene.control.ToggleButton;
import view.MainGUI;
import view.components.BoardGenerator;
import view.components.TabStd;

import java.util.Timer;
import java.util.TimerTask;

public class GameTab extends TabStd {
    private BoardGenerator cg;
    private ToggleButton toggleButton;
    private Timer testTimer;

    public GameTab() {
        super("Game",
                "",
                "");

        toggleButton = new ToggleButton("Auto Test");
        toggleButton.setVisible(false);

        cg = new BoardGenerator();
        addAllToContent(toggleButton, cg.getBoard());
        testMode();
    }

    @Override
    protected void testMode() {

        if (MainGUI.isTesting) {
            toggleButton.setVisible(true);

            String[] testValues = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "test"};
            String[] testSuits = {"club", "diamond", "heart", "spade", "test"};

            toggleButton.setOnAction(event -> {
                if (toggleButton.isSelected()) {

                    // Adds random cards to the board each second
                    GameTab.this.testTimer = new Timer(); // Access the outer class variable from this inner class
                    testTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            int randomPlacement = (int)(Math.random() * 16 - 2); // Tests range -1 to 13
                            int randomCardValue = (int)(Math.random() * 14); // Tests range 0 to 13
                            int randomCardSuit = (int)(Math.random() * 5); // Tests range 0 to 4

                            MainGUI.printToOutputAreaNewline("Automatically adding random card: "
                                    + testValues[randomCardValue] + " of " + testSuits[randomCardSuit]
                                    + " at placement: " + randomPlacement);

                            cg.addCard(randomPlacement, testValues[randomCardValue], testSuits[randomCardSuit]);
                        }
                    }, 0, 250);

                } else {
                    testTimer.cancel();
                }
            });

        }
    }

}
