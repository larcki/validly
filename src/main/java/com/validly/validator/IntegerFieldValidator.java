package com.validly.validator;

import java.util.Map;

public final class IntegerFieldValidator extends FieldValidator<Integer, IntegerFieldValidator> {

    protected IntegerFieldValidator(String fieldName, Integer value, Map note) {
        super(fieldName, value, note);
    }

    public IntegerFieldValidator valueMustBeAtLeast(int min) {
        return must(ValidationRules.minValue(min), "valueMustBeAtLeast");
    }

    public IntegerFieldValidator valueMustNotExceed(int min) {
        return must(ValidationRules.maxValue(min), "valueMustNotExceed");
    }


    public IntegerFieldValidator valueMustBeWithin(int min, int max) {
        return must(ValidationRules.minValue(min)
                .and(ValidationRules.maxValue(max)), "valueMustBeWithin");
    }

}