package com.validly;

public class ValidationFailureException extends RuntimeException {

    public ValidationFailureException(String message) {
        super(message);
    }
}
