package io.validly;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public class PreCondition<T, FV extends ValidationEngine> {

    protected final ValidationEngine<T, FV> validationEngine;

    PreCondition(ValidationEngine<T, FV> validationEngine) {
        this.validationEngine = validationEngine;
    }

    /**
     * Set the null value to be considered as a validation error.
     *
     * @param message error message.
     * @return validation engine
     */
    public ValidationEngine<T, ? extends ValidationEngine> mustNotBeNull(String message) {
        this.validationEngine.setNullIsValid(false);
        this.validationEngine.mustFatally(Objects::nonNull, message);
        return this.validationEngine;
    }

    /**
     * Set the null value to be considered as valid. If the value is null the engine will not even evaluate other predicates.
     *
     * @return validation engine
     */
    public ValidationEngine<T, ? extends ValidationEngine> canBeNull() {
        this.validationEngine.setNullIsValid(true);
        return this.validationEngine;
    }

    /**
     * Set the null value to be considered as a validation error if the provided boolean is true.
     *
     * @param mustNotBeNull boolean to determine if null value should be invalid
     * @param message       error message
     * @return validation engine
     */
    public ValidationEngine<T, ? extends ValidationEngine> mustNotBeNullWhen(boolean mustNotBeNull, String message) {
        if (mustNotBeNull) {
            return mustNotBeNull(message);
        } else {
            return canBeNull();
        }
    }

    /**
     * Ignores the whole validation rule if the given value is false.
     *
     * @param validate false will ignore the validation
     * @return validation engine
     */
    public PreCondition<T, ? extends ValidationEngine> validateWhen(boolean validate) {
        this.validationEngine.setIgnore(!validate);
        return this;
    }

    /**
     * Evaluate the provided Then predicate(s) if the given boolean value is true. Note that using the when() -condition
     * as the first step, all the predicates will be executed using {@link ValidationEngine#mustFatally} method.
     *
     * @param value          determines whether to evaluate the then predicate(s)
     * @param thenPredicates array of then predicates
     * @return validation engine
     */
    @SafeVarargs
    public final ValidationEngine<T, ? extends ValidationEngine> when(boolean value, Then<T>... thenPredicates) {
        if (value) {
            Arrays.stream(thenPredicates)
                    .forEach(p -> this.validationEngine.mustFatally(p.getPredicate(), p.getMessage()));
        }
        return this.validationEngine;
    }

    public ValidationEngine<T, ? extends ValidationEngine> must(Predicate<T> predicate, String message) {
        return this.validationEngine.must(predicate, message);
    }

}

