package io.validly;

import java.util.List;
import java.util.function.Predicate;

/**
 * Validation engine for Integer type value. Provides convenience methods for String validation predicate.
 */
public final class ValidationEngineInteger extends ValidationEngine<Integer, ValidationEngineInteger> {

    protected ValidationEngineInteger(String fieldName, Integer value, ValidlyNote note) {
        super(fieldName, value, note);
    }

    protected ValidationEngineInteger(Integer value, List<String> note) {
        super(value, note);
    }

    protected ValidationEngineInteger(Integer value) {
        super(value);
    }

    /**
     * Set a value that is less than the max to be considered as invalid.
     * valid: v >= min
     *
     * @param min     minimum valid value
     * @param message validation error
     * @return validation engines
     */
    public ValidationEngineInteger valueMustBeAtLeast(int min, String message) {
        return must(PredicateUtil.minValue(min), message);
    }

    /**
     * Set a value that is greater than the max to be considered as invalid.
     * valid: v <= max
     *
     * @param max     maximum valid value
     * @param message validation error
     * @return validation engines
     */
    public ValidationEngineInteger valueMustNotExceed(int max, String message) {
        return must(PredicateUtil.maxValue(max), message);
    }

    /**
     * Convenience method for defining max and min values.
     * <p>
     * valueMustBeWithin(1, 3) is same as: valueMustBeAtLeast(1) AND valueMustNotExceed(3)
     *
     * @param min     minimum value
     * @param max     maximum value
     * @param message validation error
     * @return validation engine
     */
    public ValidationEngineInteger valueMustBeWithin(int min, int max, String message) {
        return must(PredicateUtil.minValue(min)
                .and(PredicateUtil.maxValue(max)), message);
    }

    @Override
    public ValidationEngineInteger must(Predicate<Integer> predicate, String message) {
        return (ValidationEngineInteger) super.must(predicate, message);
    }

}