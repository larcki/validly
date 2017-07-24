package com.validly;

public class PreConditionInteger extends PreCondition<Integer, ValidationEngineInteger> {

    PreConditionInteger(ValidationEngine<Integer, ValidationEngineInteger> validationEngine) {
        super(validationEngine);
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