package io.validly;

import java.util.List;

/**
 * Class for starting a validation rule in Note-All mode:
 * <p>
 * In this mode each validation predicate will be evaluated and failures are gathered with a message into a {@link List}
 * or {@link ValidlyNote} object provided.
 * <p>
 * The evaluation process will stop without completing all the predicates in the following cases:<br>
 * <p>
 * 1. The first predicate (mustNotBeNull etc. in {@link PreCondition}) fails.<br>
 * 2. Function given to {@link ValidationEngine#mustConvert} throws an exception or returns a null.<br>
 * 3. Predicate given to {@link ValidationEngine#mustFatally} fails.
 */
public class NoteAllValidator {

    /**
     * Starts a type {@link String} validation rule with {@link ValidlyNote} note.
     *
     * @param value     value being validated
     * @param fieldName identifier for this validation rule
     * @param note      notification object where the messages with the identifier are gathered
     * @return {@link PreConditionString} for defining the first predicate
     */
    public static PreConditionString valid(String value, String fieldName, ValidlyNote note) {
        ValidationEngineString validator = new ValidationEngineString(fieldName, value, note);
        validator.setFailOnFirst(false);
        return new PreConditionString(validator);
    }

    /**
     * Starts a type {@link String} validation rule with {@link List} note.
     *
     * @param value value being validated
     * @param note  notification object where the messages are gathered
     * @return {@link PreConditionString} for defining the first predicate
     */
    public static PreConditionString valid(String value, List<String> note) {
        ValidationEngineString validator = new ValidationEngineString(value, note);
        validator.setFailOnFirst(false);
        return new PreConditionString(validator);
    }

    /**
     * Starts a type {@link Integer} validation rule with {@link ValidlyNote} note.
     *
     * @param value     value being validated
     * @param fieldName identifier for this validation rule
     * @param note      notification object where the messages with the identifier are gathered
     * @return {@link PreConditionInteger} for defining the first predicate
     */
    public static PreConditionInteger valid(Integer value, String fieldName, ValidlyNote note) {
        ValidationEngineInteger validator = new ValidationEngineInteger(fieldName, value, note);
        validator.setFailOnFirst(false);
        return new PreConditionInteger(validator);
    }

    /**
     * Starts a type {@link Integer} validation rule with {@link List} note.
     *
     * @param value value being validated
     * @param note  notification object where the messages are gathered
     * @return {@link PreConditionInteger} for defining the first predicate
     */
    public static PreConditionInteger valid(Integer value, List<String> note) {
        ValidationEngineInteger validator = new ValidationEngineInteger(value, note);
        validator.setFailOnFirst(false);
        return new PreConditionInteger(validator);
    }

    /**
     * Starts a type {@link T} validation rule with {@link ValidlyNote} note.
     *
     * @param value     value being validated
     * @param fieldName identifier for this validation rule
     * @param note      notification object where the messages with the identifier are gathered
     * @return {@link PreCondition} for defining the first predicate
     */
    public static <T> PreCondition<T, ValidationEngine> valid(T value, String fieldName, ValidlyNote note) {
        ValidationEngine<T, ValidationEngine> validationEngine = new ValidationEngine<>(fieldName, value, note);
        validationEngine.setFailOnFirst(false);
        return new PreCondition<>(validationEngine);
    }

    /**
     * Starts a type {@link T} validation rule with {@link List} note.
     *
     * @param value value being validated
     * @param note  notification object where the messages are gathered
     * @return {@link PreCondition} for defining the first predicate
     */
    public static <T> PreCondition<T, ValidationEngine> valid(T value, List<String> note) {
        ValidationEngine<T, ValidationEngine> validationEngine = new ValidationEngine<>(value, note);
        validationEngine.setFailOnFirst(false);
        return new PreCondition<>(validationEngine);
    }

}
