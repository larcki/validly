package com.validly.validator;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public class FieldValidator<T, FV extends FieldValidator> {

    private final String fieldName;
    private final T value;
    private final ValidlyNote note;
    private boolean validationFailed;
    boolean nullIsValid;

    private FieldValidator(String fieldName, T value, ValidlyNote note) {
        this.fieldName = fieldName;
        this.value = value;
        this.note = note;
    }

    abstract static class BaseStart {
        FieldValidator fieldValidator;

        public BaseStart(FieldValidator fieldValidator) {
            this.fieldValidator = fieldValidator;
        }

        abstract FieldValidator required(String message);
        abstract FieldValidator requiredWhen(boolean value, String message);
        abstract FieldValidator canBeNull();

    }

    public static class StringStart extends BaseStart {

        public StringStart(StringFieldValidator fieldValidator) {
            super(fieldValidator);
        }

        public StringFieldValidator required(String message) {
            return (StringFieldValidator) fieldValidator;
        }

        @Override
        public StringFieldValidator requiredWhen(boolean value, String message) {
            fieldValidator.nullIsValid = !value;
            return null;
        }

        @Override
        public StringFieldValidator canBeNull() {
            fieldValidator.nullIsValid = true;
            return (StringFieldValidator) fieldValidator;
        }

    }

    public static StringStart field(String fieldName, String value, ValidlyNote note) {
        StringFieldValidator stringFieldValidator = new StringFieldValidator(fieldName, value, note);
        return new StringStart(stringFieldValidator);
    }

    public static IntegerFieldValidator field(String fieldName, Integer value, ValidlyNote note) {
        return new IntegerFieldValidator(fieldName, value, note);
    }

    private boolean valueIsNullAndItsValid() {
        return value == null && nullIsValid;
    }

    public FV must(Predicate<T> predicate, String message) {
        if (!validationFailed && !valueIsNullAndItsValid() && !predicate.test(value)) {
            note.add(fieldName, message);
            validationFailed = true;
        }
        return (FV) this;
    }

    public OnGoingWhenCondition when(boolean condition) {
        return new OnGoingWhenCondition(condition);
    }

    public OnGoingWhenCondition when(Predicate<T> predicate) {
        return new OnGoingWhenCondition(predicate.test(value));
    }

    public class OnGoingWhenCondition {

        private boolean condition;

        public OnGoingWhenCondition(boolean condition) {
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

    public static class StringFieldValidator extends FieldValidator<String, StringFieldValidator> {

        public StringFieldValidator(String fieldName, String value, ValidlyNote note) {
            super(fieldName, value, note);
        }


        public StringFieldValidator mustNotBeBlank(String message) {
            return must(ValidationRules.isNotBlank(), message);
        }

        public StringFieldValidator checkNotEmpty(String message) {
            return must(ValidationRules.isNotEmpty(), message);
        }

        public StringFieldValidator checkNotTrimmedEmpty(String message) {
            return must(ValidationRules.isNotTrimmedEmpty(), message);
        }

        public StringFieldValidator lengthMustNotExceed(int max, String message) {
            return must(ValidationRules.isWithinMax(max), message);
        }

        public StringFieldValidator lengthMustBeAtLeast(int min, String message) {
            return must(ValidationRules.isWithinMin(min), message);
        }

        public StringFieldValidator lengthMustBeWithin(int min, int max, String message) {
            return must(ValidationRules.isWithinMin(min)
                    .and(ValidationRules.isWithinMax(max)), message);
        }

    }

    public static class IntegerFieldValidator extends FieldValidator<Integer, IntegerFieldValidator> {

        public IntegerFieldValidator(String fieldName, Integer value, ValidlyNote note) {
            super(fieldName, value, note);
        }

        public IntegerFieldValidator checkMaxValue(int max, String message) {
            return must(ValidationRules.maxValue(max), message);
        }

    }
}

