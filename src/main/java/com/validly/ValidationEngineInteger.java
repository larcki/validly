package com.validly;

import java.util.List;
import java.util.function.Predicate;

public final class ValidationEngineInteger extends ValidationEngine<Integer, ValidationEngineInteger> {

    protected ValidationEngineInteger(String fieldName, Integer value, ValidlyNote note) {
        super(fieldName, value, note);
    }

    protected ValidationEngineInteger(Integer value, List<String> note) {
        super(value, note);
    }

    protected ValidationEngineInteger(Integer value) {
        super(value);
    }

    public ValidationEngineInteger valueMustBeAtLeast(int min, String message) {
        return must(PredicateUtil.minValue(min), message);
    }

    public ValidationEngineInteger valueMustNotExceed(int min, String message) {
        return must(PredicateUtil.maxValue(min), message);
    }

    public ValidationEngineInteger valueMustBeWithin(int min, int max, String message) {
        return must(PredicateUtil.minValue(min)
                .and(PredicateUtil.maxValue(max)), message);
    }

    public ValidationEngineInteger must(Predicate<Integer> predicate, String message) {
        return (ValidationEngineInteger) super.must(predicate, message);
    }

}