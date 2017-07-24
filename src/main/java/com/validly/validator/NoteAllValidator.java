package com.validly.validator;

import java.util.List;

public class NoteAllValidator {

    // STRING
    public static PreConditionString field(String fieldName, String value, Notification note) {
        ValidationEngineString validator = new ValidationEngineString(fieldName, value, note);
        validator.setFailOnFirst(false);
        return new PreConditionString(validator);
    }
    public static PreConditionString field(String value, List<String> note) {
        ValidationEngineString validator = new ValidationEngineString(value, note);
        validator.setFailOnFirst(false);
        return new PreConditionString(validator);
    }


    // INTEGER
    public static PreConditionInteger field(String fieldName, Integer value, Notification note) {
        ValidationEngineInteger validator = new ValidationEngineInteger(fieldName, value, note);
        validator.setFailOnFirst(false);
        return new PreConditionInteger(validator);
    }
    public static PreConditionInteger field(Integer value, List<String> note) {
        ValidationEngineInteger validator = new ValidationEngineInteger(value, note);
        validator.setFailOnFirst(false);
        return new PreConditionInteger(validator);
    }


    // GENERIC
    public static <T> PreCondition<T, ValidationEngine> field(String fieldName, T value, Notification note) {
        ValidationEngine<T, ValidationEngine> validationEngine = new ValidationEngine<>(fieldName, value, note);
        validationEngine.setFailOnFirst(false);
        return new PreCondition<>(validationEngine);
    }
    public static <T> PreCondition<T, ValidationEngine> field(T value, List<String> note) {
        ValidationEngine<T, ValidationEngine> validationEngine = new ValidationEngine<>(value, note);
        validationEngine.setFailOnFirst(false);
        return new PreCondition<>(validationEngine);
    }

}
