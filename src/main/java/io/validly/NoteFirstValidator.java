package io.validly;

import java.util.List;

/**
 * Class for starting a validation rule in Note-First mode:
 * <p>
 * In this mode the validation predicates will be evaluated in sequential order until the first error occurs which
 * will be then added with a message into a {@link List} or {@link ValidlyNote} object provided.
 */
public class NoteFirstValidator {

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
        validator.setFailOnFirst(true);
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
        validator.setFailOnFirst(true);
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
        validator.setFailOnFirst(true);
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
        validator.setFailOnFirst(true);
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
        validationEngine.setFailOnFirst(true);
        return new PreCondition<>(validationEngine);
    }

    /**
     * Starts a type {@link Integer} validation rule with {@link List} note.
     *
     * @param value value being validated
     * @param note  notification object where the messages are gathered
     * @return {@link PreCondition} for defining the first predicate
     */
    public static <T> PreCondition<T, ValidationEngine> valid(T value, List<String> note) {
        ValidationEngine<T, ValidationEngine> validationEngine = new ValidationEngine<>(value, note);
        validationEngine.setFailOnFirst(true);
        return new PreCondition<>(validationEngine);
    }

}
