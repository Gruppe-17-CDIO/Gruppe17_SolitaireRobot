package controller;

import dataObjects.GlobalEnums;
import dataObjects.Move;
import dataObjects.SolitaireState;

import java.util.List;
import java.util.Stack;

public interface NextMoveCallBack {
        void OnSuccess(Move move, SolitaireState state, GlobalEnums.GameProgress gameProgress);

        void OnFailure(String message, List<Move> moves, Stack<SolitaireState> history);

        void OnError(Exception e);
}
