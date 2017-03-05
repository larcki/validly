package com.validly.validator;

class PreCondition<T extends FieldValidator> {

    protected final FieldValidator fieldValidator;

     PreCondition(FieldValidator fieldValidator) {
        this.fieldValidator = fieldValidator;
    }

     FieldValidator required(String message) {
        this.fieldValidator.setNullIsValid(false);
        return this.fieldValidator;
    }

     FieldValidator canBeNull() {
        this.fieldValidator.setNullIsValid(true);
        return this.fieldValidator;
    }

     FieldValidator requiredWhen(boolean value, String message) {
        this.fieldValidator.setNullIsValid(!value);
        return this.fieldValidator;
    }

}

