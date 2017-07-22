package com.validly.validator;

import java.util.Objects;

public class PreCondition<T, FV extends FieldValidator> {

    protected final FieldValidator<T, FV> fieldValidator;

    PreCondition(FieldValidator<T, FV> fieldValidator) {
        this.fieldValidator = fieldValidator;
    }

    public FieldValidator<T, ? extends FieldValidator> mustNotBeNull() {
        this.fieldValidator.setNullIsValid(false);
        this.fieldValidator.must(Objects::nonNull, "mustNotBeNull");
        return this.fieldValidator;
    }

    public FieldValidator<T, ? extends FieldValidator> canBeNull() {
        this.fieldValidator.setNullIsValid(true);
        return this.fieldValidator;
    }

    public FieldValidator<T, ? extends FieldValidator> mustNotBeNullWhen(boolean mustNotBeNull) {
        if (mustNotBeNull) {
            return mustNotBeNull();
        } else {
            return canBeNull();
        }
    }

    public PreCondition<T, ? extends FieldValidator> validateWhen(boolean validate) {
        this.fieldValidator.setIgnore(!validate);
        return this;
    }

}

