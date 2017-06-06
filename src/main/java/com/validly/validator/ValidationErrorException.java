package com.validly.validator;

public class ValidationErrorException extends RuntimeException {

    public ValidationErrorException(String message) {
        super(message);
    }
}
