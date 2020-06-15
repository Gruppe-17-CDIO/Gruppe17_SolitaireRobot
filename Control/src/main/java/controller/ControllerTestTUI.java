package controller;

import dataObjects.Move;
import dataObjects.SolitaireState;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 * Manual test of controller.
 *
 * @author erlend
 */
public class ControllerTestTUI {
    private Controller controller;
    int gameType;

    public static void main(String[] args) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("WELCOME TO SUPER-TUI.");
        System.out.println("The tui where you test your controller.");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        new ControllerTestTUI().runGame();
    }


    public void runGame() {
        int accept = 0;
        gameType = 1;
        while (accept == 0) {
            System.out.println("Manual: 1, Auto First alt: 2, Random Auto: 3");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                gameType = scanner.nextInt();
                accept = 1;
                if (gameType > 3 || gameType < 1) {
                    System.out.println("Invalid game type.");
                    accept = 0;
                }
            } else {
                System.out.println("Not an int.");
            }
        }
        accept = 0;

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

            public void OnSuccess(List<Move> moves, Stack<SolitaireState> history, boolean won) {
                try {
                    System.out.println(history.peek().getPrintFormat());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                playTurn(moves, history);
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

    private void playTurn(List<Move> moves, Stack<SolitaireState> history) {
        int choice = 0;
        if (gameType == 1) {
            int accept = 0;
            while (accept == 0) {
                System.out.println("Choose your move from the list. Integer, then enter.");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    accept = 1;
                    if (choice > moves.size() - 1 || choice < 0) {
                        System.out.println("Invalid move index.");
                        accept = 0;
                    }
                } else {
                    System.out.println("Not an int.");
                }
            }
        } else if (gameType == 2) {
            choice = 0;
        } else if (gameType == 3) {
            int bound = moves.size() - 1;
            if (bound < 1) {
                bound = 1;
            }
            choice = new Random().nextInt(bound);
        }

        if (history.peek().isWon()) {
            System.out.println("YOU WON!");
            System.exit(0);
        } else if (moves.size() < 1) {
            System.out.println("YOU LOST.");
            System.exit(0);
        }

        controller.performMove(moves.get(choice), new CompletionCallBack() {
            @Override
            public void OnSuccess(String status) {
                System.out.println(status);
                seeResults();
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

    private void seeResults() {
        controller.getNextMove(new Image(new InputStream() { // Dummy InputStream
            @Override
            public int read() throws IOException {
                return 0;
            }
        }), new NextMoveCallBack() {
            @Override
            public void OnSuccess(List<Move> moves, Stack<SolitaireState> history, boolean won) {
                try {
                    System.out.println(history.peek().getPrintFormat());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                playTurn(moves, history);
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

