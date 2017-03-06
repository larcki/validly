package com.validly.validator;

import java.util.Objects;

class PreCondition<T extends FieldValidator> {

    protected final FieldValidator fieldValidator;

    PreCondition(FieldValidator fieldValidator) {
        this.fieldValidator = fieldValidator;
    }

    @SuppressWarnings("unchecked")
    FieldValidator required(String message) {
        this.fieldValidator.setNullIsValid(false);
        this.fieldValidator.must(Objects::nonNull, message);
        return this.fieldValidator;
    }

    FieldValidator canBeNull() {
        this.fieldValidator.setNullIsValid(true);
        return this.fieldValidator;
    }

    FieldValidator requiredWhen(boolean value, String message) {
        this.fieldValidator.setNullIsValid(!value);
        return this.fieldValidator;
    }

}

