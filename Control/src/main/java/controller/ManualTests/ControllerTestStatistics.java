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

public class ControllerTestStatistics {
    // RAM and CPU heavy, may take several minutes. (Game completion time varies a lot!)
    static final int iterations = 10; // Number of whole games played.
    static int wins = 0;
    static boolean roundFinished = false;
    static I_Controller controller;

    public static void main(String[] args) {
        for (int i = 0; i < iterations; i++) {

            System.out.println("Starting game test run " + (i + 1) + ".");
            roundFinished = false;

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
                            try {
                                //System.out.println(state.getPrintFormat());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            while (!roundFinished) {
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
                                                roundFinished = true;
                                            } else if (progress == GlobalEnums.GameProgress.WON) {
                                                System.out.println("GAME WON");
                                                wins++;
                                                roundFinished = true;
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
        System.out.println("Wins: " + wins + "\nIterations: " + iterations + "\nWin ratio: " + (double) wins / iterations);
    }
}
