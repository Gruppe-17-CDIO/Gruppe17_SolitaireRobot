package controller;

import dataObjects.Move;
import dataObjects.SolitaireState;

import java.util.List;
import java.util.Stack;

public interface NextMoveCallBack {
        void OnSuccess(List<Move> moves, Stack<SolitaireState> history, boolean won);

        void OnFailure(String message, List<Move> moves, Stack<SolitaireState> history);

        void OnError(Exception e);
}
