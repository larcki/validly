package com.validly.validator;

import java.util.function.Predicate;

import static java.util.Objects.nonNull;

class ValidationRules {

    static Predicate<String> notEmptyString() {
        return (v) -> nonNull(v) && !v.trim().isEmpty();
    }

    static Predicate<Integer> maxValue(int max) {
        return (v) -> v == null || v <= max;
    }

    public static Predicate<String> maxLength(int max) {
        return (v) -> v == null || v.length() <= max;

    }
}
