package cardCalculator;

public class DrawnCardReadException extends Exception {
    public DrawnCardReadException(String errorMessage) {
        super(errorMessage);
    }
}