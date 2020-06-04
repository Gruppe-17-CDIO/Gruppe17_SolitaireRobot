import dataObjects.SolitaireState;

import java.util.Stack;

public interface CompletionCallBack {
        void OnSuccess(String status, Stack<SolitaireState> history);

        void OnFailure(String message, Stack<SolitaireState> history);

        void OnError(Exception e);
}
