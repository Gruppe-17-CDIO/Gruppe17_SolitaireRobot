package cardCalculator;

public class CardReadException extends Exception {
    public CardReadException(String errorMessage) {
        super(errorMessage);
    }
}