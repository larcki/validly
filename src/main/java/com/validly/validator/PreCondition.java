package com.validly.validator;

import java.util.Objects;

class PreCondition<T extends FieldValidator> {

    protected final FieldValidator fieldValidator;

    PreCondition(FieldValidator fieldValidator) {
        this.fieldValidator = fieldValidator;
    }

    @SuppressWarnings("unchecked")
    FieldValidator mustNotBeNull(String message) {
        this.fieldValidator.setNullIsValid(false);
        this.fieldValidator.must(Objects::nonNull, message);
        return this.fieldValidator;
    }

    FieldValidator canBeNull() {
        this.fieldValidator.setNullIsValid(true);
        return this.fieldValidator;
    }

    @SuppressWarnings("unchecked")
    FieldValidator mustNotBeNullWhen(boolean mustNotBeNull, String message) {
        if (mustNotBeNull) {
            this.fieldValidator.setNullIsValid(false);
            this.fieldValidator.must(Objects::nonNull, message);
        } else {
            this.fieldValidator.setNullIsValid(true);
        }
        return this.fieldValidator;
    }

}

