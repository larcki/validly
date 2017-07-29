package io.validly.excpetion;

/**
 * Exception for Fail-Fast validator mode.
 */
public class ValidationErrorException extends RuntimeException {

    public ValidationErrorException(String message) {
        super(message);
    }
}
