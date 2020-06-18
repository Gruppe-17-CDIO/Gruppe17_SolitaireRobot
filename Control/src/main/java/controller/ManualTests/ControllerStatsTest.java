package controller.ManualTests;

import controller.CompletionCallBack;
import controller.Controller;
import controller.I_Controller;
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
 * This test runs a number of simulated game and prints the ratio of wins / total.
 * The test will also print any Exceptions thrown in the logic.
 * <p>
 * RAM and CPU heavy, may take several minutes. (Game completion time varies a lot!)
 */

public class ControllerStatsTest {
    private static ControllerStatsTest test;
    final int iterations = 10; // Number of whole games played.
    int wins = 0;
    boolean roundFinished = false;
    I_Controller controller;

    public static void main(String[] args) {
        ControllerStatsTest test = ControllerStatsTest.getInstance();
        test.startTest();
    }

    public static ControllerStatsTest getInstance() {
        if (test == null) {
            test = new ControllerStatsTest();
        }
        return test;
    }

    private static void playRound(I_Controller controller, ControllerStatsTest test) {

        controller.getNextMove(new Image(new InputStream() { // Dummy InputStream
            @Override
            public int read() throws IOException {
                return 0;
            }
        }), new NextMoveCallBack() {
            @Override

            public void OnSuccess(Move move, SolitaireState state, GlobalEnums.GameProgress progress) {
                try {
                    if (progress == GlobalEnums.GameProgress.LOST) {
                        System.out.println("GAME LOST");
                    } else if (progress == GlobalEnums.GameProgress.WON) {
                        System.out.println("GAME WON");
                        test.addWin();
                    } else {
                        playRound(controller, test);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    public synchronized void addWin() {
        wins++;
    }

    public void startTest() {

        for (int i = 0; i < iterations; i++) {
            System.out.println("*******************************************************************");
            System.out.println("ITERATION " + (i + 1));
            playGame(this);
        }
        System.out.println("Wins: " + wins + "\nIterations: " + iterations + "\nWin ratio: " + (double) wins / (double) iterations);
    }

    private void playGame(ControllerStatsTest test) {
        controller = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.gc();
            }
        }).start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller = new Controller();
        controller.setTestModeOn(true, new CompletionCallBack() {
            @Override
            public void OnSuccess(String status) {
                //System.out.println(status);
                controller.startNewGame(new Image(new InputStream() { // Dummy InputStream
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                }), new NextMoveCallBack() {
                    @Override

                    public void OnSuccess(Move move, SolitaireState state, GlobalEnums.GameProgress progress) {
                        playRound(controller, test);
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
}

