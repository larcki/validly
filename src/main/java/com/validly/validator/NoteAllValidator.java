package com.validly.validator;

import java.util.List;

public class NoteAllValidator {

    // STRING
    public static StringPreCondition field(String fieldName, String value, Notification note) {
        StringFieldValidator validator = new StringFieldValidator(fieldName, value, note);
        validator.setFailOnFirst(false);
        return new StringPreCondition(validator);
    }
    public static StringPreCondition field(String fieldName, String value, List<String> note) {
        StringFieldValidator validator = new StringFieldValidator(fieldName, value, note);
        validator.setFailOnFirst(false);
        return new StringPreCondition(validator);
    }


    // INTEGER
    public static IntegerPreCondition field(String fieldName, Integer value, Notification note) {
        IntegerFieldValidator validator = new IntegerFieldValidator(fieldName, value, note);
        validator.setFailOnFirst(false);
        return new IntegerPreCondition(validator);
    }
    public static IntegerPreCondition field(String fieldName, Integer value, List<String> note) {
        IntegerFieldValidator validator = new IntegerFieldValidator(fieldName, value, note);
        validator.setFailOnFirst(false);
        return new IntegerPreCondition(validator);
    }


    // GENERIC
    public static <T> PreCondition<T, FieldValidator> field(String fieldName, T value, Notification note) {
        FieldValidator<T, FieldValidator> fieldValidator = new FieldValidator<>(fieldName, value, note);
        return new PreCondition<>(fieldValidator);
    }
    public static <T> PreCondition<T, FieldValidator> field(String fieldName, T value, List<String> note) {
        FieldValidator<T, FieldValidator> fieldValidator = new FieldValidator<>(fieldName, value, note);
        return new PreCondition<>(fieldValidator);
    }

}
