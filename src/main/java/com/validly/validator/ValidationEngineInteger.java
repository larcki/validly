package com.validly.validator;

import java.util.List;
import java.util.function.Predicate;

public final class ValidationEngineInteger extends FieldValidator<Integer, ValidationEngineInteger> {

    protected ValidationEngineInteger(String fieldName, Integer value, Notification note) {
        super(fieldName, value, note);
    }

    protected ValidationEngineInteger(Integer value, List<String> note) {
        super(value, note);
    }

    protected ValidationEngineInteger(Integer value) {
        super(value);
    }

    public ValidationEngineInteger valueMustBeAtLeast(int min) {
        return must(ValidationRules.minValue(min), "valueMustBeAtLeast");
    }

    public ValidationEngineInteger valueMustNotExceed(int min) {
        return must(ValidationRules.maxValue(min), "valueMustNotExceed");
    }

    public ValidationEngineInteger valueMustBeWithin(int min, int max) {
        return must(ValidationRules.minValue(min)
                .and(ValidationRules.maxValue(max)), "valueMustBeWithin");
    }

    public ValidationEngineInteger must(Predicate<Integer> predicate, String message) {
        return (ValidationEngineInteger) super.must(predicate, message);
    }

}