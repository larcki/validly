package com.validly;


/**
 * Class for starting a validation rule in Fail-Fast mode:
 * <p>
 * {@link com.validly.excpetion.ValidationFailureException} will be thrown when the first validation failure occurs.
 */
public class FailFastValidator {

    /**
     * Starts a type {@link String} validation rule.
     *
     * @param value value being validated
     * @return {@link PreConditionString} for defining the first predicate
     */
    public static PreConditionString valid(String value) {
        ValidationEngineString validationEngineString = new ValidationEngineString(value);
        return new PreConditionString(validationEngineString);
    }

    /**
     * Starts a type {@link Integer} validation rule.
     *
     * @param value value being validated
     * @return {@link PreConditionInteger} for defining the first predicate
     */
    public static PreConditionInteger valid(Integer value) {
        ValidationEngineInteger validationEngineInteger = new ValidationEngineInteger(value);
        return new PreConditionInteger(validationEngineInteger);
    }

    /**
     * Starts a type {@link T} validation rule.
     *
     * @param value value being validated
     * @return {@link PreCondition} for defining the first predicate
     */
    public static <T> PreCondition<T, ValidationEngine> valid(T value) {
        ValidationEngine<T, ValidationEngine> validationEngine = new ValidationEngine<>(value);
        return new PreCondition<>(validationEngine);
    }

}
