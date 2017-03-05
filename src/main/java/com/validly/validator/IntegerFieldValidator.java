package com.validly.validator;

public final class IntegerFieldValidator extends FieldValidator<Integer, IntegerFieldValidator> {

    protected IntegerFieldValidator(String fieldName, Integer value, ValidlyNote note) {
        super(fieldName, value, note);
    }

    public IntegerFieldValidator checkMaxValue(int max, String message) {
        return must(ValidationRules.maxValue(max), message);
    }

}