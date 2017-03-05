package com.validly.validator;

import java.util.Objects;
import java.util.function.Predicate;

public class ValidationPredicate<T> implements Predicate<T> {

    //TODO could be abstract so that can be extend in the usage

    protected final String message;
    protected final Predicate<T> predicate;

    public static ValidationPredicate notNull(String message) {
        return new ValidationPredicate<>(message, Objects::nonNull);
    }

    public static ValidationPredicate<String> notBlank(String message) {
        return new ValidationPredicate<>(message, ValidationRules.isNotBlank());
    }

    public static ValidationPredicate<String> mustNotBeEmpty(String message) {
        return new ValidationPredicate<>(message, ValidationRules.isNotEmpty());
    }

    public static ValidationPredicate<String> notEmptyTrimmed(String message) {
        return new ValidationPredicate<>(message, ValidationRules.isNotTrimmedEmpty());
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
