package com.validly.validator;

public class FailFastValidator {

    public static StringPreCondition field(String value) {
        StringFieldValidator stringFieldValidator = new StringFieldValidator(value);
        return new StringPreCondition(stringFieldValidator);
    }

    public static IntegerPreCondition field(Integer value) {
        IntegerFieldValidator integerFieldValidator = new IntegerFieldValidator(value);
        return new IntegerPreCondition(integerFieldValidator);
    }

    public static <T> PreCondition<T, FieldValidator> field(T value) {
        FieldValidator<T, FieldValidator> fieldValidator = new FieldValidator<>(value);
        return new PreCondition<>(fieldValidator);
    }

}
