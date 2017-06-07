package com.validly.validator;

import java.util.Objects;

class PreCondition<T extends FieldValidator> {

    protected final FieldValidator fieldValidator;

    PreCondition(FieldValidator fieldValidator) {
        this.fieldValidator = fieldValidator;
    }

    @SuppressWarnings("unchecked")
    FieldValidator mustNotBeNull() {
        this.fieldValidator.setNullIsValid(false);
        this.fieldValidator.must(Objects::nonNull, Conditions.mustNotBeNull);
        return this.fieldValidator;
    }

    FieldValidator validateWhenNotNull() {
        this.fieldValidator.setNullIsValid(true);
        return this.fieldValidator;
    }

    @SuppressWarnings("unchecked")
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

