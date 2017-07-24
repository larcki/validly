package com.validly.validator;

import java.util.List;

public class NoteAllValidator {

    // STRING
    public static PreConditionString field(String fieldName, String value, Notification note) {
        StringFieldValidator validator = new StringFieldValidator(fieldName, value, note);
        validator.setFailOnFirst(false);
        return new PreConditionString(validator);
    }
    public static PreConditionString field(String value, List<String> note) {
        StringFieldValidator validator = new StringFieldValidator(value, note);
        validator.setFailOnFirst(false);
        return new PreConditionString(validator);
    }


    // INTEGER
    public static PreConditionInteger field(String fieldName, Integer value, Notification note) {
        IntegerFieldValidator validator = new IntegerFieldValidator(fieldName, value, note);
        validator.setFailOnFirst(false);
        return new PreConditionInteger(validator);
    }
    public static PreConditionInteger field(Integer value, List<String> note) {
        IntegerFieldValidator validator = new IntegerFieldValidator(value, note);
        validator.setFailOnFirst(false);
        return new PreConditionInteger(validator);
    }


    // GENERIC
    public static <T> PreCondition<T, FieldValidator> field(String fieldName, T value, Notification note) {
        FieldValidator<T, FieldValidator> fieldValidator = new FieldValidator<>(fieldName, value, note);
        return new PreCondition<>(fieldValidator);
    }
    public static <T> PreCondition<T, FieldValidator> field(T value, List<String> note) {
        FieldValidator<T, FieldValidator> fieldValidator = new FieldValidator<>(value, note);
        return new PreCondition<>(fieldValidator);
    }

}
