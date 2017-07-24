package com.validly.validator;

public class FailFastValidator {

    public static PreConditionString field(String value) {
        ValidationEngineString validationEngineString = new ValidationEngineString(value);
        return new PreConditionString(validationEngineString);
    }

    public static PreConditionInteger field(Integer value) {
        ValidationEngineInteger validationEngineInteger = new ValidationEngineInteger(value);
        return new PreConditionInteger(validationEngineInteger);
    }

    public static <T> PreCondition<T, FieldValidator> field(T value) {
        FieldValidator<T, FieldValidator> fieldValidator = new FieldValidator<>(value);
        return new PreCondition<>(fieldValidator);
    }

}
