package com.validly.validator;

public final class StringPreCondition extends PreCondition {

    public StringPreCondition(FieldValidator fieldValidator) {
        super(fieldValidator);
    }

    @Override
    public StringFieldValidator mustNotBeNull() {
        return (StringFieldValidator) super.mustNotBeNull();
    }

    @Override
    public StringFieldValidator mustNotBeNullWhen(boolean value) {
        return (StringFieldValidator) super.mustNotBeNullWhen(value);
    }

    @Override
    public StringFieldValidator canBeNull() {
        return (StringFieldValidator) super.canBeNull();
    }

}