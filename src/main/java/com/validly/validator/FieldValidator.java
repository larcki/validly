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

    public static IntegerPreCondition field(String fieldName, Integer value, Map note) {
        IntegerFieldValidator integerFieldValidator = new IntegerFieldValidator(fieldName, value, note);
        return new IntegerPreCondition(integerFieldValidator);
    }

    public FV must(Predicate<T> predicate, String identifier) {
        if (!ignore && !validationFailed && !valueIsNullAndItsValid() && !predicate.test(value)) {
            if (note != null) {
                note.put(fieldName, identifier);
            } else {
                throw new ValidationFailureException("Validation failure: " + identifier);
            }
            validationFailed = true;
        }
        return (FV) this;
    }

    public FV must(Predicate<T> predicate) {
            return must(predicate, "customMustCondition");
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

    @SafeVarargs
    public final FV when(Predicate<T> predicate, ValidationPredicate<T>... thenPredicates) {
        return thenValidation(predicate.test(value), thenPredicates);
    }

    @SafeVarargs
    public final FV when(boolean value, ValidationPredicate<T>... thenPredicates) {
        return thenValidation(value, thenPredicates);
    }

    @SafeVarargs
    private final FV thenValidation(boolean whenConditionResult, ValidationPredicate<T>... thenPredicates) {
        if (whenConditionResult) {
            Arrays.stream(thenPredicates)
                    .forEach(p -> must(p.getPredicate(), p.getMessage()));
        }
        return (FV) FieldValidator.this;
    }

}

