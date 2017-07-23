package com.validly.validator;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FieldValidator<T, FV extends FieldValidator> {

    private final String fieldName;
    private final T value;
    private final Notification note;
    private boolean stopValidation;
    private boolean nullIsValid;
    private boolean ignore;

    protected FieldValidator(String fieldName, T value, Notification note) {
        this.fieldName = fieldName;
        this.value = value;
        this.note = note;
    }

    protected FieldValidator(String fieldName, T value, List<String> note) {
        this(fieldName, value, new FlatNotification(note));
    }

    protected FieldValidator(T value) {
        this.fieldName = null;
        this.value = value;
        this.note = null;
    }

    public static StringPreCondition field(String fieldName, String value, Notification note) {
        StringFieldValidator stringFieldValidator = new StringFieldValidator(fieldName, value, note);
        return new StringPreCondition(stringFieldValidator);
    }

    public static StringPreCondition field(String fieldName, String value, List<String> note) {
        StringFieldValidator stringFieldValidator = new StringFieldValidator(fieldName, value, note);
        return new StringPreCondition(stringFieldValidator);
    }

    public static IntegerPreCondition field(String fieldName, Integer value, Notification note) {
        IntegerFieldValidator integerFieldValidator = new IntegerFieldValidator(fieldName, value, note);
        return new IntegerPreCondition(integerFieldValidator);
    }

    public static IntegerPreCondition field(String fieldName, Integer value, List<String> note) {
        IntegerFieldValidator integerFieldValidator = new IntegerFieldValidator(fieldName, value, note);
        return new IntegerPreCondition(integerFieldValidator);
    }

    public static PreCondition<LocalDate, FieldValidator> field(String fieldName, LocalDate value, Notification note) {
        FieldValidator<LocalDate, FieldValidator> fieldValidator = new FieldValidator<>(fieldName, value, note);
        return new PreCondition<>(fieldValidator);
    }

    public FV must(Predicate<T> predicate, String message) {
        if (!ignore && !stopValidation && !valueIsNullAndItsValid() && !predicate.test(value)) {
            markAsFailed(message);
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
            if (!stopValidation) {
                convertedValue = conversionFunction.apply(value);
            }
            if (convertedValue == null) {
                markAsFailed(message);
                stopValidation = true;
            }
        } catch (Exception e) {
            markAsFailed(message);
            stopValidation = true;
        }
        return copyValidator(convertedValue);
    }

    private void markAsFailed(String message) {
        if (note != null) {
            note.addMessage(fieldName, message);
        } else {
            throw new ValidationFailureException("Validation failure: " + message);
        }
    }

    //TODO implement for others than fail-fast as well
//    private <NEW_TYPE> FieldValidator<NEW_TYPE, FieldValidator> copyValidator(NEW_TYPE value) {
//        FieldValidator<NEW_TYPE, FieldValidator> newValidator = new FieldValidator<>(value);
//        newValidator.setIgnore(ignore);
//        newValidator.setNullIsValid(nullIsValid);
//        newValidator.setStopValidation(stopValidation);
//        return newValidator;
//    }

    private <NEW_TYPE> FieldValidator<NEW_TYPE, FieldValidator> copyValidator(NEW_TYPE value) {
        FieldValidator<NEW_TYPE, FieldValidator> newValidator = new FieldValidator<>(fieldName, value, note);
        newValidator.setIgnore(ignore);
        newValidator.setNullIsValid(nullIsValid);
        newValidator.setStopValidation(stopValidation);
        return newValidator;
    }


    private void setStopValidation(boolean stopValidation) {
        this.stopValidation = stopValidation;
    }

}

