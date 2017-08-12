package io.validly;

import java.util.List;
import java.util.function.Predicate;

/**
 * Validation engine for String type value. Provides convenience methods for String validation predicate.
 */
public final class ValidationEngineString extends ValidationEngine<String, ValidationEngineString> {

    protected ValidationEngineString(String value) {
        super(value);
    }

    protected ValidationEngineString(String fieldName, String value, ValidlyNote note) {
        super(fieldName, value, note);
    }

    protected ValidationEngineString(String value, List<String> note) {
        super(value, note);
    }

    /**
     * Set a value with length greater than the max to be considered as invalid.
     * valid: v.length() less or equal than max
     *
     * @param max     maximum valid value
     * @param message validation error
     * @return validation engines
     */
    public ValidationEngineString lengthMustNotExceed(int max, String message) {
        return must(PredicateUtil.isWithinMax(max), message);
    }

    /**
     * Set a value with length less than the min to be considered as invalid.
     * valid: v.length() greater or equal than min
     *
     * @param min     minimum valid value
     * @param message validation error
     * @return validation engines
     */
    public ValidationEngineString lengthMustBeAtLeast(int min, String message) {
        return must(PredicateUtil.isWithinMin(min), message);
    }

    /**
     * Convenience method for defining max and min lengths
     * <p>
     * lengthMustBeWithin(1, 3) is same as: lengthMustBeAtLeast(1) AND lengthMustNotExceed(3)
     *
     * @param min     minimum value
     * @param max     maximum value
     * @param message validation error
     * @return validation engine
     */
    public ValidationEngineString lengthMustBeWithin(int min, int max, String message) {
        return must(PredicateUtil.isWithinMin(min)
                .and(PredicateUtil.isWithinMax(max)), message);
    }

    /**
     * Set a value that does not contain {@link String#contains} the provided sequence of char values to be considered as invalid.
     *
     * @param value   the sequence to search for
     * @param message validation error
     * @return validation engine
     */
    public ValidationEngineString mustContain(CharSequence value, String message) {
        return must(s -> s.contains(value), message);
    }

    /**
     * Set value that does not start with {@link String#startsWith} the provided string to be considered invalid.
     *
     * @param value   the string to search for
     * @param message validation error
     * @return validation engine
     */
    public ValidationEngineString mustStartWith(String value, String message) {
        return must(s -> s.startsWith(value), message);
    }

    @Override
    public ValidationEngineString must(Predicate<String> predicate, String message) {
        return (ValidationEngineString) super.must(predicate, message);
    }


}


