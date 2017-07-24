package com.validly.validator;

public class PreConditionInteger extends PreCondition<Integer, IntegerFieldValidator> {

    public PreConditionInteger(FieldValidator<Integer, IntegerFieldValidator> fieldValidator) {
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
    public PreConditionInteger validateWhen(boolean validate) {
        return (PreConditionInteger) super.validateWhen(validate);
    }
}