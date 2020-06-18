package controller.ManualTests;

import controller.CompletionCallBack;
import controller.Controller;
import controller.NextMoveCallBack;
import dataObjects.GlobalEnums;
import dataObjects.Move;
import dataObjects.SolitaireState;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Stack;

/**
 * Manual test of controller.
 *
 * @author erlend
 */
public class ControllerTestTUI {
    int gameType;
    private Controller controller;

    public static void main(String[] args) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("WELCOME TO SUPER-TUI.");
        System.out.println("The tui where you test your controller.");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        new ControllerTestTUI().runGame();
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
        int choice = 0;

        if (state.getGameProgress() == GlobalEnums.GameProgress.WON) {
            System.out.println("YOU WON!");
            System.exit(0);
        } else if (state.getGameProgress() == GlobalEnums.GameProgress.LOST) {
            System.out.println("YOU LOST.");
            System.exit(0);
        }
        seeResults();

    }

    private void seeResults() {
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
}

