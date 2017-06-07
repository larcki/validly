package com.validly.validator;

import java.util.Objects;

class PreCondition<T, FV extends FieldValidator> {

    protected final FieldValidator<T, FV> fieldValidator;

    PreCondition(FieldValidator<T, FV> fieldValidator) {
        this.fieldValidator = fieldValidator;
    }

    FieldValidator mustNotBeNull() {
        this.fieldValidator.setNullIsValid(false);
        this.fieldValidator.must(Objects::nonNull, Conditions.mustNotBeNull);
        return this.fieldValidator;
    }

    FieldValidator validateWhenNotNull() {
        this.fieldValidator.setNullIsValid(true);
        return this.fieldValidator;
    }

    FieldValidator mustNotBeNullWhen(boolean mustNotBeNull) {
        if (mustNotBeNull) {
            return mustNotBeNull();
        } else {
            return validateWhenNotNull();
        }
    }

    PreCondition validateWhen(boolean validate) {
        this.fieldValidator.setIgnore(!validate);
        return this;
    }

}

