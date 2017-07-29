package io.validly;

public final class PreConditionString extends PreCondition<String, ValidationEngineString> {

    PreConditionString(ValidationEngine<String, ValidationEngineString> validationEngine) {
        super(validationEngine);
    }


    /**
     * Set a blank value to be considered as a validation error.
     * <p>
     * null       = blank
     * ""         = blank
     * " "        = blank
     * "bob"      = not blank
     * "  bob  "  = not blank
     *
     * @param message error message
     * @return validation engine
     */
    public ValidationEngineString mustNotBeBlank(String message) {
        this.validationEngine.setNullIsValid(false);
        this.validationEngine.mustFatally(PredicateUtil.isNotBlank(), message);
        return (ValidationEngineString) this.validationEngine;
    }

    @Override
    public ValidationEngineString mustNotBeNull(String message) {
        return (ValidationEngineString) super.mustNotBeNull(message);
    }

    @Override
    public ValidationEngineString mustNotBeNullWhen(boolean value, String message) {
        return (ValidationEngineString) super.mustNotBeNullWhen(value, message);
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