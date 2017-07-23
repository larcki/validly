package com.validly.validator;

public class FailFastValidator<T> extends FieldValidator<T, FailFastValidator> {

    protected FailFastValidator(T value) {
        super(value);
    }

    public static StringPreCondition field(String value) {
        StringFieldValidator stringFieldValidator = new StringFieldValidator(value);
        return new StringPreCondition(stringFieldValidator);
    }

    public static IntegerPreCondition field(Integer value) {
        IntegerFieldValidator integerFieldValidator = new IntegerFieldValidator(value);
        return new IntegerPreCondition(integerFieldValidator);
    }

}
