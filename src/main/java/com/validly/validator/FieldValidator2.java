package com.validly.validator;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public class FieldValidator2<T, FV extends FieldValidator2> {

    private final String fieldName;
    private final T value;
    private final ValidlyNote note;
    private boolean validationFailed;

    private FieldValidator2(String fieldName, T value, ValidlyNote note) {
        this.fieldName = fieldName;
        this.value = value;
        this.note = note;
    }

    public static class Start<T, FV extends FieldValidator2> {

        private final String fieldName;
        private final T value;
        private final ValidlyNote note;

        public Start(String fieldName, T value, ValidlyNote note) {
            this.fieldName = fieldName;
            this.value = value;
            this.note = note;
        }

        public static Start field(String fieldName, String value, ValidlyNote note) {
            return new Start<>(fieldName, value, note);
        }

        public FieldValidator2<T, FV> isRequired() {
            FieldValidator2.field(fieldName)
        }

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

    public OnGoingWhenCondition whenNotNull() {
        return new OnGoingWhenCondition<FV>(Objects.nonNull(value));
    }

    public class OnGoingWhenCondition<WT> {

        private boolean condition;

        public OnGoingWhenCondition(boolean condition) {
            this.condition = condition;
        }

        @SafeVarargs
        public final WT thenCheck(ValidationPredicate<T>... predicates) {
            if (condition) {
                Arrays.stream(predicates)
                        .forEach(p -> check(p, p.getMessage()));
            }
            return (WT) FieldValidator2.this;
        }

    }

    public static class StringFieldValidator extends FieldValidator2<String, StringFieldValidator> {

        public StringFieldValidator(String fieldName, String value, ValidlyNote note) {
            super(fieldName, value, note);
        }

        public StringFieldValidator checkNotBlank(String message) {
            return check(ValidationRules.isNotEmpty(), message);
        }

        public StringFieldValidator checkNotEmpty(String message) {
            return check(ValidationRules.isNotEmpty(), message);
        }

        public StringFieldValidator checkNotTrimmedEmpty(String message) {
            return check(ValidationRules.isNotTrimmedEmpty(), message);
        }

        public StringFieldValidator checkMaxLength(int max, String message) {
            return check(ValidationRules.isWithinMax(max), message);
        }

        public StringFieldValidator checkMinLength(int min, String message) {
            return check(ValidationRules.isWithinMin(min), message);
        }

    }

    public static class IntegerFieldValidator extends FieldValidator2<Integer, IntegerFieldValidator> {

        public IntegerFieldValidator(String fieldName, Integer value, ValidlyNote note) {
            super(fieldName, value, note);
        }

        public IntegerFieldValidator checkMaxValue(int max, String message) {
            return check(ValidationRules.maxValue(max), message);
        }

    }
}
