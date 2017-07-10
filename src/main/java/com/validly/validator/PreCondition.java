package com.validly.validator;

import java.util.Objects;

public class PreCondition<T, FV extends FieldValidator> {

    protected final FieldValidator<T, FV> fieldValidator;

    PreCondition(FieldValidator<T, FV> fieldValidator) {
        this.fieldValidator = fieldValidator;
    }

    public FieldValidator mustNotBeNull() {
        this.fieldValidator.setNullIsValid(false);
        this.fieldValidator.must(Objects::nonNull, Conditions.mustNotBeNull);
        return this.fieldValidator;
    }

    public FieldValidator nullIsValid() {
        this.fieldValidator.setNullIsValid(true);
        return this.fieldValidator;
    }

    public FieldValidator mustNotBeNullWhen(boolean mustNotBeNull) {
        if (mustNotBeNull) {
            return mustNotBeNull();
        } else {
            return nullIsValid();
        }
    }

    public PreCondition validateWhen(boolean validate) {
        this.fieldValidator.setIgnore(!validate);
        return this;
    }

}

