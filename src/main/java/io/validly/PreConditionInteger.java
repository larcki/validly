package io.validly;

public class PreConditionInteger extends PreCondition<Integer, ValidationEngineInteger> {

    PreConditionInteger(ValidationEngine<Integer, ValidationEngineInteger> validationEngine) {
        super(validationEngine);
    }

    @Override
    public ValidationEngineInteger mustNotBeNull(String message) {
        return (ValidationEngineInteger) super.mustNotBeNull(message);
    }

    @Override
    public ValidationEngineInteger mustNotBeNullWhen(boolean value, String message) {
        return (ValidationEngineInteger) super.mustNotBeNullWhen(value, message);
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