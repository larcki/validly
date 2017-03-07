package com.validly.validator;

public final class StringPreCondition extends PreCondition {

    public StringPreCondition(FieldValidator fieldValidator) {
        super(fieldValidator);
    }

    @Override
    public StringFieldValidator mustNotBeNull(String message) {
        return (StringFieldValidator) super.mustNotBeNull(message);
    }

    @Override
    public StringFieldValidator mustNotBeNullWhen(boolean value, String message) {
        return (StringFieldValidator) super.mustNotBeNullWhen(value, message);
    }

    @Override
    public StringFieldValidator canBeNull() {
        return (StringFieldValidator) super.canBeNull();
    }

}