package com.validly.validator;

import java.util.Objects;
import java.util.function.Predicate;

public class ValidationPredicate<T> {

    protected final String message;
    protected final Predicate<T> predicate;

    public static ValidationPredicate notNull(String message) {
        return new ValidationPredicate<>(Objects::nonNull, message);
    }

    public static ValidationPredicate<String> notBlank(String message) {
        return new ValidationPredicate<>(ValidationRules.isNotBlank(), message);
    }

    public static ValidationPredicate<String> mustNotBeEmpty(String message) {
        return new ValidationPredicate<>(ValidationRules.isNotEmpty(), message);
    }

    public static ValidationPredicate<String> notEmptyTrimmed(String message) {
        return new ValidationPredicate<>(ValidationRules.isNotTrimmedEmpty(), message);
    }

    public static ValidationPredicate<Integer> maxValue(int max, String message) {
        return new ValidationPredicate<>(ValidationRules.maxValue(max), message);
    }

    public static <T> ValidationPredicate<T> must(Predicate<T> predicate, String message) {
        return new ValidationPredicate<>(predicate, message);
    }

    public ValidationPredicate(Predicate<T> predicate, String message) {
        this.predicate = predicate;
        this.message = message;
    }

    public Predicate<T> getPredicate() {
        return predicate;
    }

    String getMessage() {
        return message;
    }
}
