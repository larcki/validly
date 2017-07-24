package com.validly;

public class FailFastValidator {

    public static PreConditionString field(String value) {
        ValidationEngineString validationEngineString = new ValidationEngineString(value);
        return new PreConditionString(validationEngineString);
    }

    public static PreConditionInteger field(Integer value) {
        ValidationEngineInteger validationEngineInteger = new ValidationEngineInteger(value);
        return new PreConditionInteger(validationEngineInteger);
    }

    public static <T> PreCondition<T, ValidationEngine> field(T value) {
        ValidationEngine<T, ValidationEngine> validationEngine = new ValidationEngine<>(value);
        return new PreCondition<>(validationEngine);
    }

}
