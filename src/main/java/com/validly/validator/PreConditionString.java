package com.validly.validator;

public final class PreConditionString extends PreCondition<String, ValidationEngineString> {

    public PreConditionString(ValidationEngine<String, ValidationEngineString> validationEngine) {
        super(validationEngine);
    }

    public ValidationEngineString mustNotBeBlank() {
        this.validationEngine.setNullIsValid(false);
        this.validationEngine.mustFatally(PredicateUtil.isNotBlank(), "mustNotBeBlank");
        return (ValidationEngineString) this.validationEngine;
    }

    @Override
    public ValidationEngineString mustNotBeNull() {
        return (ValidationEngineString) super.mustNotBeNull();
    }

    @Override
    public ValidationEngineString mustNotBeNullWhen(boolean value) {
        return (ValidationEngineString) super.mustNotBeNullWhen(value);
    }

    @Override
    public ValidationEngineString canBeNull() {
        return (ValidationEngineString) super.canBeNull();
    }

    @Override
    public PreConditionString validateWhen(boolean validate) {
        return (PreConditionString) super.validateWhen(validate);
    }
}