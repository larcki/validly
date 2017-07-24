package com.validly.validator;

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

    public ValidationEngineInteger valueMustBeAtLeast(int min) {
        return must(PredicateUtil.minValue(min), "valueMustBeAtLeast");
    }

    public ValidationEngineInteger valueMustNotExceed(int min) {
        return must(PredicateUtil.maxValue(min), "valueMustNotExceed");
    }

    public ValidationEngineInteger valueMustBeWithin(int min, int max) {
        return must(PredicateUtil.minValue(min)
                .and(PredicateUtil.maxValue(max)), "valueMustBeWithin");
    }

    public ValidationEngineInteger must(Predicate<Integer> predicate, String message) {
        return (ValidationEngineInteger) super.must(predicate, message);
    }

}