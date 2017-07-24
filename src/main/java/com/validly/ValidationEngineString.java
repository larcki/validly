package com.validly;

import java.util.List;
import java.util.function.Predicate;

public final class ValidationEngineString extends ValidationEngine<String, ValidationEngineString> {

    protected ValidationEngineString(String value) {
        super(value);
    }

    protected ValidationEngineString(String fieldName, String value, ValidlyNote note) {
        super(fieldName, value, note);
    }

    protected ValidationEngineString(String value, List<String> note) {
        super(value, note);
    }

    public ValidationEngineString lengthMustNotExceed(int max, String message) {
        return must(PredicateUtil.isWithinMax(max), message);
    }

    public ValidationEngineString lengthMustBeAtLeast(int min, String message) {
        return must(PredicateUtil.isWithinMin(min), message);
    }

    public ValidationEngineString lengthMustBeWithin(int min, int max, String message) {
        return must(PredicateUtil.isWithinMin(min)
                .and(PredicateUtil.isWithinMax(max)), message);
    }

    public ValidationEngineString mustContain(String value, String message) {
        return must(s -> s.contains(value), message);
    }

    public ValidationEngineString mustStartWith(String value, String message) {
        return must(s -> s.startsWith(value), message);
    }

    public ValidationEngineString must(Predicate<String> predicate, String message) {
        return (ValidationEngineString) super.must(predicate, message);
    }


}


