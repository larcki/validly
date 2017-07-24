package com.validly;

import java.util.Objects;

public class PreCondition<T, FV extends ValidationEngine> {

    protected final ValidationEngine<T, FV> validationEngine;

    PreCondition(ValidationEngine<T, FV> validationEngine) {
        this.validationEngine = validationEngine;
    }

    public ValidationEngine<T, ? extends ValidationEngine> mustNotBeNull() {
        this.validationEngine.setNullIsValid(false);
        this.validationEngine.mustFatally(Objects::nonNull, "mustNotBeNull");
        return this.validationEngine;
    }

    public ValidationEngine<T, ? extends ValidationEngine> canBeNull() {
        this.validationEngine.setNullIsValid(true);
        return this.validationEngine;
    }

    public ValidationEngine<T, ? extends ValidationEngine> mustNotBeNullWhen(boolean mustNotBeNull) {
        if (mustNotBeNull) {
            return mustNotBeNull();
        } else {
            return canBeNull();
        }
    }

    public PreCondition<T, ? extends ValidationEngine> validateWhen(boolean validate) {
        this.validationEngine.setIgnore(!validate);
        return this;
    }

}

