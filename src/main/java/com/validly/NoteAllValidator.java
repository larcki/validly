package com.validly;

import java.util.List;

public class NoteAllValidator {

    // STRING
    public static PreConditionString valid(String fieldName, String value, ValidlyNote note) {
        ValidationEngineString validator = new ValidationEngineString(fieldName, value, note);
        validator.setFailOnFirst(false);
        return new PreConditionString(validator);
    }
    public static PreConditionString valid(String value, List<String> note) {
        ValidationEngineString validator = new ValidationEngineString(value, note);
        validator.setFailOnFirst(false);
        return new PreConditionString(validator);
    }


    // INTEGER
    public static PreConditionInteger valid(String fieldName, Integer value, ValidlyNote note) {
        ValidationEngineInteger validator = new ValidationEngineInteger(fieldName, value, note);
        validator.setFailOnFirst(false);
        return new PreConditionInteger(validator);
    }
    public static PreConditionInteger valid(Integer value, List<String> note) {
        ValidationEngineInteger validator = new ValidationEngineInteger(value, note);
        validator.setFailOnFirst(false);
        return new PreConditionInteger(validator);
    }


    // GENERIC
    public static <T> PreCondition<T, ValidationEngine> valid(String fieldName, T value, ValidlyNote note) {
        ValidationEngine<T, ValidationEngine> validationEngine = new ValidationEngine<>(fieldName, value, note);
        validationEngine.setFailOnFirst(false);
        return new PreCondition<>(validationEngine);
    }
    public static <T> PreCondition<T, ValidationEngine> valid(T value, List<String> note) {
        ValidationEngine<T, ValidationEngine> validationEngine = new ValidationEngine<>(value, note);
        validationEngine.setFailOnFirst(false);
        return new PreCondition<>(validationEngine);
    }

}
