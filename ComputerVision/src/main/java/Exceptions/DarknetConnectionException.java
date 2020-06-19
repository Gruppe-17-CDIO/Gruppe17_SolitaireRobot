package Exceptions;

import kong.unirest.UnirestException;

public class DarknetConnectionException extends ComputerVisionException {
    public DarknetConnectionException(String errorMessage) {
        super(errorMessage);
    }
}
