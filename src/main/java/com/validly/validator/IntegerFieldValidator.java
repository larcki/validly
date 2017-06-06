package com.validly.validator;

import java.util.Map;

public final class IntegerFieldValidator extends FieldValidator<Integer, IntegerFieldValidator> {

    protected IntegerFieldValidator(String fieldName, Integer value, Map note) {
        super(fieldName, value, note);
    }

}