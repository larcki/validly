package com.validly.validator;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
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

    public static PreCondition<LocalDate, FieldValidator> field(String fieldName, LocalDate value, Map note) {
        FieldValidator<LocalDate, FieldValidator> fieldValidator = new FieldValidator<>(fieldName, value, note);
        return new PreCondition<>(fieldValidator);
    }

    public FV must(Predicate<T> predicate, String identifier) {
        if (!ignore && !validationFailed && !valueIsNullAndItsValid() && !predicate.test(value)) {
            markAsFailed(identifier);
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

    public <NEW_TYPE> FieldValidator<NEW_TYPE, FieldValidator> mustConvert(Function<T, NEW_TYPE> conversionFunction) {
        return mustConvert(conversionFunction, "mustConvert");
    }

    public <NEW_TYPE> FieldValidator<NEW_TYPE, FieldValidator> mustConvert(Function<T, NEW_TYPE> conversionFunction, String message) {
        NEW_TYPE convertedValue = null;
        try {
            convertedValue = conversionFunction.apply(value);
            if (convertedValue == null) {
                markAsFailed(message);
            }
        } catch (Exception e) {
            markAsFailed(message);
        }
        return copyValidator(convertedValue);
    }

    private void markAsFailed(String identifier) {
        if (note != null) {
            note.put(fieldName, identifier);
        } else {
            throw new ValidationFailureException("Validation failure: " + identifier);
        }
        validationFailed = true;
    }

    private <NEW_TYPE> FieldValidator<NEW_TYPE, FieldValidator> copyValidator(NEW_TYPE value) {
        FieldValidator<NEW_TYPE, FieldValidator> newValidator = new FieldValidator<>(fieldName, value, note);
        newValidator.setIgnore(ignore);
        newValidator.setNullIsValid(nullIsValid);
        newValidator.setValidationFailed(validationFailed);
        return newValidator;
    }

    private void setValidationFailed(boolean validationFailed) {
        this.validationFailed = validationFailed;
    }


}

