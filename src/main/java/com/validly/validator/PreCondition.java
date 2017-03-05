package com.validly.validator;

class PreCondition {

    protected final FieldValidator fieldValidator;

    public PreCondition(FieldValidator fieldValidator) {
        this.fieldValidator = fieldValidator;
    }

    protected FieldValidator required(String message) {
        this.fieldValidator.setNullIsValid(false);
        return this.fieldValidator;
    }

    protected FieldValidator canBeNull() {
        this.fieldValidator.setNullIsValid(true);
        return this.fieldValidator;
    }

    protected FieldValidator requiredWhen(boolean value, String message) {
        this.fieldValidator.setNullIsValid(!value);
        return this.fieldValidator;
    }

}

