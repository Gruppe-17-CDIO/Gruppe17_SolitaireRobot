package controller;

public interface CompletionCallBack {
        void OnSuccess(String status);

        void OnFailure(String message);

        void OnError(Exception e);
}
