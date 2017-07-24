package com.validly.validator;

public final class PreConditionString extends PreCondition<String, ValidationEngineString> {

    public PreConditionString(FieldValidator<String, ValidationEngineString> fieldValidator) {
        super(fieldValidator);
    }

    public ValidationEngineString mustNotBeBlank() {
        this.fieldValidator.setNullIsValid(false);
        this.fieldValidator.mustFatally(ValidationRules.isNotBlank(), "mustNotBeBlank");
        return (ValidationEngineString) this.fieldValidator;
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