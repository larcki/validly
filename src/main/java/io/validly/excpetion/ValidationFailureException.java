package io.validly.excpetion;

/**
 * Exception for Fail-Fast validator mode.
 */
public class ValidationFailureException extends RuntimeException {

    public ValidationFailureException(String message) {
        super(message);
    }
}
