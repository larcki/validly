package com.validly.validator;

import java.util.Objects;
import java.util.function.Predicate;

public class ValidationPredicate<T> implements Predicate<T> {

    protected final String message;
    protected final Predicate<T> predicate;

    public static ValidationPredicate notNull(String message) {
        return new ValidationPredicate<>(message, Objects::nonNull);
    }

    public static ValidationPredicate<String> notEmpty(String message) {
        return new ValidationPredicate<>(message, ValidationRules.notEmptyString());
    }

    public static ValidationPredicate<Integer> maxValue(int max, String message) {
        return new ValidationPredicate<>(message, ValidationRules.maxValue(max));
    }

    public ValidationPredicate(String message, Predicate<T> predicate) {
        this.message = message;
        this.predicate = predicate;
    }

    @Override
    public boolean test(T t) {
        return predicate.test(t);
    }

    String getMessage() {
        return message;
    }
}
