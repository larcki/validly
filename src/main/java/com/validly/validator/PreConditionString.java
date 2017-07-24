package com.validly.validator;

public final class PreConditionString extends PreCondition<String, StringFieldValidator> {

    public PreConditionString(FieldValidator<String, StringFieldValidator> fieldValidator) {
        super(fieldValidator);
    }

    public StringFieldValidator mustNotBeBlank() {
        this.fieldValidator.setNullIsValid(false);
        this.fieldValidator.mustFatally(ValidationRules.isNotBlank(), "mustNotBeBlank");
        return (StringFieldValidator) this.fieldValidator;
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

    @Override
    public PreConditionString validateWhen(boolean validate) {
        return (PreConditionString) super.validateWhen(validate);
    }
}