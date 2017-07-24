package com.validly;

public class FailFastValidator {

    public static PreConditionString valid(String value) {
        ValidationEngineString validationEngineString = new ValidationEngineString(value);
        return new PreConditionString(validationEngineString);
    }

    public static PreConditionInteger valid(Integer value) {
        ValidationEngineInteger validationEngineInteger = new ValidationEngineInteger(value);
        return new PreConditionInteger(validationEngineInteger);
    }

    public static <T> PreCondition<T, ValidationEngine> valid(T value) {
        ValidationEngine<T, ValidationEngine> validationEngine = new ValidationEngine<>(value);
        return new PreCondition<>(validationEngine);
    }

}
