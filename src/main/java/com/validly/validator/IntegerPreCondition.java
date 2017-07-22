package com.validly.validator;

public class IntegerPreCondition extends PreCondition<Integer, IntegerFieldValidator> {

    public IntegerPreCondition(FieldValidator<Integer, IntegerFieldValidator> fieldValidator) {
        super(fieldValidator);
    }

    @Override
    public IntegerFieldValidator mustNotBeNull() {
        return (IntegerFieldValidator) super.mustNotBeNull();
    }

    @Override
    public IntegerFieldValidator mustNotBeNullWhen(boolean value) {
        return (IntegerFieldValidator) super.mustNotBeNullWhen(value);
    }

    @Override
    public IntegerFieldValidator canBeNull() {
        return (IntegerFieldValidator) super.canBeNull();
    }

    @Override
    public IntegerPreCondition validateWhen(boolean validate) {
        return (IntegerPreCondition) super.validateWhen(validate);
    }
}