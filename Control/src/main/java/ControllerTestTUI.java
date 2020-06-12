import dataObjects.Move;
import dataObjects.SolitaireState;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Manual test of controller.
 *
 * @author erlend
 */
public class ControllerTestTUI {
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
        int accept = 0, choice = 0;
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

