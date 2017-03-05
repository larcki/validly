package com.validly.validator;

public final class StringPreCondition extends PreCondition {

    public StringPreCondition(FieldValidator fieldValidator) {
        super(fieldValidator);
    }

    @Override
    public StringFieldValidator required(String message) {
        return (StringFieldValidator) this.fieldValidator;
    }

    @Override
    public StringFieldValidator requiredWhen(boolean value, String message) {
        return (StringFieldValidator) super.requiredWhen(value, message);
    }

    @Override
    public StringFieldValidator canBeNull() {
        return (StringFieldValidator) super.canBeNull();
    }

}