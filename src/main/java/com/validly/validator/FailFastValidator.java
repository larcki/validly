package com.validly.validator;

public class FailFastValidator {

    public static PreConditionString field(String value) {
        StringFieldValidator stringFieldValidator = new StringFieldValidator(value);
        return new PreConditionString(stringFieldValidator);
    }

    public static PreConditionInteger field(Integer value) {
        IntegerFieldValidator integerFieldValidator = new IntegerFieldValidator(value);
        return new PreConditionInteger(integerFieldValidator);
    }

    public static <T> PreCondition<T, FieldValidator> field(T value) {
        FieldValidator<T, FieldValidator> fieldValidator = new FieldValidator<>(value);
        return new PreCondition<>(fieldValidator);
    }

}
