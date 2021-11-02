package com.rad.ms.corona_view.access.ErrorHandling;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String msg) {
        super(msg);
    }
}
