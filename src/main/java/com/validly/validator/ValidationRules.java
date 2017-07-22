package com.validly.validator;

import java.util.function.Predicate;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

class ValidationRules {

    static Predicate<String> isNotEmpty() {
        return (v) -> !isEmptyString(v);
    }

    static Predicate<String> isNotTrimmedEmpty() {
        return (v) -> !isTrimmedEmptyString(v);
    }

    static Predicate<String> isNotBlank() {
        return (v) -> !isBlank(v);
    }

    static Predicate<String> isWithinMax(int max) {
        return (v) -> isNull(v) || v.length() <= max;
    }

    static Predicate<String> isWithinMin(int min) {
        return (v) -> nonNull(v) && v.length() >= min;
    }

    static Predicate<Integer> maxValue(int max) {
        return (v) -> v <= max;
    }

    static Predicate<Integer> minValue(int min) {
        return (v) -> v >= min;
    }

    private static boolean isEmptyString(String v) {
        return nonNull(v) && v.isEmpty();
    }

    private static boolean isTrimmedEmptyString(String v) {
        return nonNull(v) && !v.trim().isEmpty();
    }

    private static boolean isBlank(String v) {
        int strLen;
        if (v == null || (strLen = v.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(v.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
