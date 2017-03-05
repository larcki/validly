package com.validly.validator;

import java.util.Arrays;
import java.util.function.Predicate;

public class FieldValidator<T, FV extends FieldValidator> {

    private final String fieldName;
    private final T value;
    private final ValidlyNote note;
    private boolean validationFailed;
    private boolean nullIsValid;

    protected FieldValidator(String fieldName, T value, ValidlyNote note) {
        this.fieldName = fieldName;
        this.value = value;
        this.note = note;
    }

    public static StringPreCondition field(String fieldName, String value, ValidlyNote note) {
        StringFieldValidator stringFieldValidator = new StringFieldValidator(fieldName, value, note);
        return new StringPreCondition(stringFieldValidator);
    }

    public static IntegerFieldValidator field(String fieldName, Integer value, ValidlyNote note) {
        return new IntegerFieldValidator(fieldName, value, note);
    }

    public FV must(Predicate<T> predicate, String message) {
        if (!validationFailed && !valueIsNullAndItsValid() && !predicate.test(value)) {
            note.add(fieldName, message);
            validationFailed = true;
        }
        return (FV) this;
    }

    void setNullIsValid(boolean nullIsValid) {
        this.nullIsValid = nullIsValid;
    }

    private boolean valueIsNullAndItsValid() {
        return value == null && nullIsValid;
    }

    public WhenCondition when(boolean condition) {
        return new WhenCondition(condition);
    }

    public WhenCondition when(Predicate<T> predicate) {
        return new WhenCondition(predicate.test(value));
    }

    public class WhenCondition {

        private boolean condition;

        public WhenCondition(boolean condition) {
            this.condition = condition;
        }

        @SafeVarargs
        public final FV then(ValidationPredicate<T>... predicates) {
            if (condition) {
                Arrays.stream(predicates)
                        .forEach(p -> must(p, p.getMessage()));
            }
            return (FV) FieldValidator.this;
        }

    }

}

