package io.validly;

import io.validly.excpetion.ValidationErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ValidationEngine<T, FV extends ValidationEngine> {

    private final String fieldName;
    private final T value;
    private final ValidlyNote note;
    private boolean stopValidation;
    private boolean nullIsValid;
    private boolean ignore;
    private boolean failOnFirst;

    protected ValidationEngine(String fieldName, T value, ValidlyNote note) {
        this.fieldName = fieldName;
        this.value = value;
        this.note = note;
    }

    protected ValidationEngine(T value, List<String> note) {
        this(null, value, new FlatNotification(note));
    }

    protected ValidationEngine(T value) {
        this.fieldName = null;
        this.value = value;
        this.note = null;
    }

    /**
     * Set the value for which the predicate test returns false to be considered as invalid.
     *
     * @param predicate predicate the evaluate
     * @param message   validation error
     * @return validation engine
     */
    public ValidationEngine<T, FV> must(Predicate<T> predicate, String message) {
        if (checkFailure(predicate)) {
            markAsFailed(message);
        }
        return this;
    }

    /**
     * Same as method {@link #must} but the remaining validation predicates defined for this value will not be evaluated
     * if the predicate test returns false (Note-All Mode).
     * <p>
     * E.g.<br>
     * value(name, note)<br>
     * .mustNotBeNull("")<br>
     * .mustFatally(s -&gt; s.equals("something"), "") -- if this fails<br>
     * .must(s -&gt; s.startsWith("so"), "")           -- this won't be evaluated<br>
     *
     * @param predicate predicate to evaluate
     * @param message   validation error
     * @return validation engine
     */
    public ValidationEngine<T, FV> mustFatally(Predicate<T> predicate, String message) {
        if (checkFailure(predicate)) {
            stopValidation = true;
            markAsFailed(message);
        }
        return this;
    }

    /**
     * Applies the given function with the value. This allows values to be converted into a different types during the
     * validation. If the provided function returns null or throws an exception the value will be considered as invalid
     * and no further predicates will be evaluated (Note-All Mode).
     * <p>
     * valid(date, "date", note)<br>
     * .mustNotBeNull("must not be null")<br>
     * .mustConvert(s -&gt; LocalDate.parse(s, ofPattern("dd.MM.yyyy")), "invalid date")<br>
     * .must(d -&gt; d.isAfter(LocalDate.now()), "date must be in the future");<br>
     *
     * @param conversionFunction function the is applied with the value as input
     * @param message            validation error
     * @param <NEW_TYPE>         return type of the function
     * @return validation engine typed as NEW_TYPE
     */
    public <NEW_TYPE> ValidationEngine<NEW_TYPE, ValidationEngine> mustConvert(Function<T, NEW_TYPE> conversionFunction, String message) {
        NEW_TYPE convertedValue = null;
        try {
            if (!stopValidation) {
                convertedValue = conversionFunction.apply(value);
            }
            if (convertedValue == null) {
                markAsFailed(message);
                stopValidation = true;
            }
        } catch (Exception e) {
            markAsFailed(message);
            stopValidation = true;
        }
        return copyValidator(convertedValue, this);
    }

    /**
     * Evaluate the provided Then predicate(s) if the first predicate is true.
     *
     * @param predicate      determines whether to evaluate the then predicate(s)
     * @param thenPredicates array of then predicates
     * @return validation engine
     */
    @SafeVarargs
    public final ValidationEngine<T, FV> when(Predicate<T> predicate, Then<T>... thenPredicates) {
        return thenValidation(evaluatePredicate() && predicate.test(value), thenPredicates);
    }

    /**
     * Evaluate the provided Then predicate(s) if the given boolean value is true.
     *
     * @param value          determines whether to evaluate the then predicate(s)
     * @param thenPredicates array of then predicates
     * @return validation engine
     */
    @SafeVarargs
    public final ValidationEngine<T, FV> when(boolean value, Then<T>... thenPredicates) {
        return thenValidation(value, thenPredicates);
    }

    private boolean checkFailure(Predicate<T> predicate) {
        return evaluatePredicate() && !predicate.test(value);
    }

    private boolean evaluatePredicate() {
        return !ignore && !stopValidation && !valueIsNullAndItsValid();
    }

    private boolean valueIsNullAndItsValid() {
        return value == null && nullIsValid;
    }


    // When-Then Construct
    //
    // FIXME: these will not return the typed envine (e.g. ValidationEngineString) and cannot be overriden
    // workaraund: If only provide on predicate then no need to declare final (with safeVarargs) and override them
    @SafeVarargs
    private final ValidationEngine<T, FV> thenValidation(boolean whenConditionResult, Then<T>... thenPredicates) {
        if (whenConditionResult) {
            Arrays.stream(thenPredicates)
                    .forEach(p -> must(p.getPredicate(), p.getMessage()));
        }
        return ValidationEngine.this;
    }

    private void markAsFailed(String message) {
        if (note != null) {
            note.addMessage(fieldName, message);
        } else {
            throw new ValidationErrorException("Validation error: " + message);
        }
        if (failOnFirst) {
            stopValidation = true;
        }
    }

    private static <NEW_TYPE> ValidationEngine<NEW_TYPE, ValidationEngine> copyValidator(NEW_TYPE value, ValidationEngine source) {
        ValidationEngine<NEW_TYPE, ValidationEngine> newValidator = new ValidationEngine<>(source.fieldName, value, source.note);
        newValidator.setIgnore(source.ignore);
        newValidator.setNullIsValid(source.nullIsValid);
        newValidator.setStopValidation(source.stopValidation);
        newValidator.setFailOnFirst(source.failOnFirst);
        return newValidator;
    }

    protected void setFailOnFirst(boolean failOnFirst) {
        this.failOnFirst = failOnFirst;
    }

    protected void setStopValidation(boolean stopValidation) {
        this.stopValidation = stopValidation;
    }

    void setNullIsValid(boolean nullIsValid) {
        this.nullIsValid = nullIsValid;
    }

    void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }


}
