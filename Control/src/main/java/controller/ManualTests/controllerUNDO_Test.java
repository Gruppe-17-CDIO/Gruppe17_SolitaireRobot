package controller.ManualTests;

import controller.CompletionCallBack;
import controller.Controller;
import controller.NextMoveCallBack;
import dataObjects.Card;
import dataObjects.GlobalEnums;
import dataObjects.Move;
import dataObjects.SolitaireState;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Stack;

public class controllerUNDO_Test {
    private Controller controller;
    private SolitaireState beforeLastState;
    private int turnCount = 0;
    private boolean errorFound = false;

    public static void main(String[] args) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Test of the UNDO-method.");
        System.out.println("This test will show that undo resets\n" +
                "to previous state. In an actual game,\n" +
                "cards from computervision must be read\n" +
                "again. This test does not account for \nthat.");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        new controllerUNDO_Test().runGame();
    }


    public void runGame() {
        controller = new Controller();

        controller.setTestModeOn(true, new CompletionCallBack() {
            @Override
            public void OnSuccess(String status) {
                System.out.println(status);
                setupGame();
            }

            @Override
            public void OnFailure(String message) {
                System.out.println(message);
            }

            @Override
            public void OnError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setupGame() {

        controller.startNewGame(new Image(new InputStream() { // Dummy InputStream
            @Override
            public int read() throws IOException {
                return 0;
            }
        }), new NextMoveCallBack() {
            @Override

            public void OnSuccess(Move move, SolitaireState state, GlobalEnums.GameProgress progress) {
                try {
                    System.out.println(state.getPrintFormat());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                playTurn(move, state);
            }

            @Override
            public void OnFailure(String message, List<Move> moves, Stack<SolitaireState> history) {
                System.out.println(message);
            }

            @Override
            public void OnError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void playTurn(Move move, SolitaireState state) {
        turnCount++;
        if (turnCount == 1) {
            beforeLastState = state;
        }
        if (turnCount > 1) {
            controller.undo(new CompletionCallBack() {
                @Override
                public void OnSuccess(String status) {
                    System.out.println("\n**************" +
                            "\n" + status +
                            "\n**************\n");

                    SolitaireState revertedState = null;
                    try {
                        revertedState = controller.getHistory().peek();
                        validate(revertedState);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void OnFailure(String message) {
                    System.out.println(message);
                }

                @Override
                public void OnError(Exception e) {
                    e.printStackTrace();
                }
            });
        }

        performMove();

    }

    private void performMove() {
        System.out.println("Performing move!");
        controller.getNextMove(new Image(new InputStream() { // Dummy InputStream
            @Override
            public int read() throws IOException {
                return 0;
            }
        }), new NextMoveCallBack() {
            @Override
            public void OnSuccess(Move move, SolitaireState state, GlobalEnums.GameProgress progress) {
                try {
                    System.out.println(state.getPrintFormat());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                playTurn(move, state);
            }

            @Override
            public void OnFailure(String message, List<Move> moves, Stack<SolitaireState> history) {
                System.out.println(message);
            }

            @Override
            public void OnError(Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void validate(SolitaireState state) {
        List<Move> moves = state.getSuggestedMoves();

        for (int i = 0; i < moves.size() - 1; i++) {
            if (!(moves.get(i).toString().equals(beforeLastState.getSuggestedMoves().get(i).toString()))) {
                errorFound = true;
                System.out.println(
                        "Error!: \n" +
                                moves.get(i).toString() +
                                "\ndoes not match \n" +
                                beforeLastState.getSuggestedMoves().get(i).toString() + ".");
            }
        }

        List<List<Card>> piles = state.getPiles();
        List<List<Card>> oldPiles = beforeLastState.getPiles();
        for (int pileIndex = 0; pileIndex < piles.size() - 1; pileIndex++) {
            List<Card> cards = piles.get(pileIndex);
            List<Card> oldCards = oldPiles.get(pileIndex);
            for (int cardIndex = 0; cardIndex < cards.size() - 1; cardIndex++) {
                if (!(cards.get(cardIndex).toString().equals(oldCards.get(cardIndex).toString()))) {
                    errorFound = true;
                    System.out.println(
                            "Error!: \n" +
                                    cards.get(cardIndex).toString() +
                                    "\ndoes not match \n" +
                                    oldCards.get(cardIndex).toString() + ".");
                }
            }
        }
        if (!state.time.equals(beforeLastState.time)) {
            errorFound = true;
            System.out.println("ID's don't match.\nOld: " + beforeLastState.time + "\nNew: " + state.time);
        }


        if (!errorFound) {
            System.out.println("\nSUMMARY");
            System.out.println("Test passed: The moves and piles match.");
            System.out.println("Reverted to state with ID:");
            System.out.println(state.time);
        }
        System.exit(0);
    }
}



