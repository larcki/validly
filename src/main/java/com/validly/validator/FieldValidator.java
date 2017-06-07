package com.validly.validator;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

public class FieldValidator<T, FV extends FieldValidator> {

    private final String fieldName;
    private final T value;
    private final Map<Object, Object> note;
    private boolean validationFailed;
    private boolean nullIsValid;
    private boolean ignore;

    protected FieldValidator(String fieldName, T value, Map<Object, Object> note) {
        this.fieldName = fieldName;
        this.value = value;
        this.note = note;
    }

    protected FieldValidator(T value) {
        this(null, value, null);
    }

    public static StringPreCondition field(String fieldName, String value, Map note) {
        StringFieldValidator stringFieldValidator = new StringFieldValidator(fieldName, value, note);
        return new StringPreCondition(stringFieldValidator);
    }

    public static StringPreCondition field(String value) {
        StringFieldValidator stringFieldValidator = new StringFieldValidator(value);
        return new StringPreCondition(stringFieldValidator);
    }

    public static IntegerFieldValidator field(String fieldName, Integer value, Map note) {
        return new IntegerFieldValidator(fieldName, value, note);
    }

    public FV must(Predicate<T> predicate, String identifier) {
        if (!ignore && !validationFailed && !valueIsNullAndItsValid() && !predicate.test(value)) {
            if (note != null) {
                note.put(fieldName, identifier);
            } else {
                throw new ValidationErrorException("Validation failure: " + identifier);
            }
            validationFailed = true;
        }
        return (FV) this;
    }

    public FV must(Predicate<T> predicate) {
            return must(predicate, Conditions.unknown);
    }

    void setNullIsValid(boolean nullIsValid) {
        this.nullIsValid = nullIsValid;
    }

    void setIgnore(boolean ignore) {
        this.ignore = ignore;
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
                        .forEach(p -> must(p.getPredicate(), p.getMessage()));
            }
            return (FV) FieldValidator.this;
        }

    }


}

