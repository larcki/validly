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

    FieldValidator canBeNull() {
        this.fieldValidator.setNullIsValid(true);
        return this.fieldValidator;
    }

    @SuppressWarnings("unchecked")
    FieldValidator mustNotBeNullWhen(boolean mustNotBeNull) {
        if (mustNotBeNull) {
            this.fieldValidator.setNullIsValid(false);
            this.fieldValidator.must(Objects::nonNull, Conditions.mustNotBeNull);
        } else {
            this.fieldValidator.setNullIsValid(true);
        }
        return this.fieldValidator;
    }

}

