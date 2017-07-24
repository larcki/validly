package com.validly.validator;

public class PreConditionInteger extends PreCondition<Integer, ValidationEngineInteger> {

    public PreConditionInteger(FieldValidator<Integer, ValidationEngineInteger> fieldValidator) {
        super(fieldValidator);
    }

    @Override
    public ValidationEngineInteger mustNotBeNull() {
        return (ValidationEngineInteger) super.mustNotBeNull();
    }

    @Override
    public ValidationEngineInteger mustNotBeNullWhen(boolean value) {
        return (ValidationEngineInteger) super.mustNotBeNullWhen(value);
    }

    @Override
    public ValidationEngineInteger canBeNull() {
        return (ValidationEngineInteger) super.canBeNull();
    }

    @Override
    public PreConditionInteger validateWhen(boolean validate) {
        return (PreConditionInteger) super.validateWhen(validate);
    }
}