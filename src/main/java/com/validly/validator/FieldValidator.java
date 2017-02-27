package com.validly.validator;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public class FieldValidator<T, FV extends FieldValidator> {

    private final String fieldName;
    private final T value;
    private final ValidlyNote note;
    private boolean validationFailed;

    private FieldValidator(String fieldName, T value, ValidlyNote note) {
        this.fieldName = fieldName;
        this.value = value;
        this.note = note;
    }

    public static StringFieldValidator field(String fieldName, String value, ValidlyNote note) {
        return new StringFieldValidator(fieldName, value, note);
    }

    public static IntegerFieldValidator field(String fieldName, Integer value, ValidlyNote note) {
        return new IntegerFieldValidator(fieldName, value, note);
    }

    public FV checkNotNull(String message) {
        return check(Objects::nonNull, message);
    }

    public FV check(Predicate<T> predicate, String message) {
        if (!validationFailed && !predicate.test(value)) {
            note.add(fieldName, message);
            validationFailed = true;
        }
        return (FV) this;
    }

    public OnGoingWhenCondition when(boolean condition) {
        return new OnGoingWhenCondition<FV>(condition);
    }

    public OnGoingWhenCondition when(Predicate<T> predicate) {
        return new OnGoingWhenCondition<FV>(predicate.test(value));
    }

    public class OnGoingWhenCondition<FV> {

        private boolean condition;

        public OnGoingWhenCondition(boolean condition) {
            this.condition = condition;
        }

        @SafeVarargs
        public final FV thenCheck(ValidationPredicate<T>... predicates) {
            if (condition) {
                Arrays.stream(predicates)
                        .forEach(p -> check(p, p.getMessage()));
            }
            return (FV) FieldValidator.this;
        }

    }

    public static class StringFieldValidator extends FieldValidator<String, StringFieldValidator> {

        public StringFieldValidator(String fieldName, String value, ValidlyNote note) {
            super(fieldName, value, note);
        }

        public StringFieldValidator checkNotEmpty(String message) {
            return check(ValidationRules.notEmptyString(), message);
        }

        public StringFieldValidator checkMaxLength(int max, String message) {
            return check(ValidationRules.maxLength(max), message);
        }

    }

    public static class IntegerFieldValidator extends FieldValidator<Integer, IntegerFieldValidator> {

        public IntegerFieldValidator(String fieldName, Integer value, ValidlyNote note) {
            super(fieldName, value, note);
        }

        public IntegerFieldValidator checkMaxValue(int max, String message) {
            return check(ValidationRules.maxValue(max), message);
        }

    }
}

