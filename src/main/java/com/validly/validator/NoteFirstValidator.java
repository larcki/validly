package com.validly.validator;

import java.util.List;

public class NoteFirstValidator <T> extends FieldValidator<T, NoteFirstValidator> {

    private NoteFirstValidator(String fieldName, T value, Notification note) {
        super(fieldName, value, note);
    }

    private NoteFirstValidator(String fieldName, T value, List<String> note) {
        super(fieldName, value, note);
    }

    // STRING
    public static StringPreCondition field(String fieldName, String value, Notification note) {
        StringFieldValidator validator = new StringFieldValidator(fieldName, value, note);
        validator.setFailOnFirst(true);
        return new StringPreCondition(validator);
    }
    public static StringPreCondition field(String fieldName, String value, List<String> note) {
        StringFieldValidator validator = new StringFieldValidator(fieldName, value, note);
        validator.setFailOnFirst(true);
        return new StringPreCondition(validator);
    }


    // INTEGER
    public static IntegerPreCondition field(String fieldName, Integer value, Notification note) {
        IntegerFieldValidator validator = new IntegerFieldValidator(fieldName, value, note);
        validator.setFailOnFirst(true);
        return new IntegerPreCondition(validator);
    }
    public static IntegerPreCondition field(String fieldName, Integer value, List<String> note) {
        IntegerFieldValidator validator = new IntegerFieldValidator(fieldName, value, note);
        validator.setFailOnFirst(true);
        return new IntegerPreCondition(validator);
    }

}
