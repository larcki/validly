package com.validly;

import java.util.Objects;

public class PreCondition<T, FV extends ValidationEngine> {

    protected final ValidationEngine<T, FV> validationEngine;

    PreCondition(ValidationEngine<T, FV> validationEngine) {
        this.validationEngine = validationEngine;
    }

    /**
     * Set the null value to be considered as a validation failure.
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
     * Set the null value to be considered as a validation failure if the provided boolean is true.
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

    public PreCondition<T, ? extends ValidationEngine> validateWhen(boolean validate) {
        this.validationEngine.setIgnore(!validate);
        return this;
    }

}

