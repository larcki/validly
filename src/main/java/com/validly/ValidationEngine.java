package com.validly;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ValidationEngine<T, FV extends ValidationEngine> {

    private final String fieldName;
    private final T value;
    private final ValidlyNote note;
    private boolean stopValidation;
    private boolean nullIsValid;
    private boolean ignore;
    private boolean failOnFirst;

    protected ValidationEngine(String fieldName, T value, ValidlyNote note) {
        this.fieldName = fieldName;
        this.value = value;
        this.note = note;
    }

    protected ValidationEngine(T value, List<String> note) {
        this(null, value, new FlatNotification(note));
    }

    protected ValidationEngine(T value) {
        this.fieldName = null;
        this.value = value;
        this.note = null;
    }

    public ValidationEngine<T, FV> must(Predicate<T> predicate) {
        return must(predicate, "customMustCondition");
    }

    public ValidationEngine<T, FV> must(Predicate<T> predicate, String message) {
        if (checkFailure(predicate)) {
            markAsFailed(message);
        }
        return this;
    }

    public ValidationEngine<T, FV> mustFatally(Predicate<T> predicate, String message) {
        if (checkFailure(predicate)) {
            stopValidation = true;
            markAsFailed(message);
        }
        return this;
    }

    private boolean checkFailure(Predicate<T> predicate) {
        return !ignore && !stopValidation && !valueIsNullAndItsValid() && !predicate.test(value);
    }

    private boolean valueIsNullAndItsValid() {
        return value == null && nullIsValid;
    }


    // When-Then Construct
    //
    // FIXME: these will not return the TypedValidator (e.g. StringValidator) and cannot be overriden
    // workaraund: If only provide on predicate then no need to declare final (with safeVarargs) and override them


    @SafeVarargs
    public final ValidationEngine<T, FV> when(Predicate<T> predicate, Then<T>... thenPredicates) {
        return thenValidation(!stopValidation && predicate.test(value), thenPredicates);
    }

    @SafeVarargs
    public final ValidationEngine<T, FV> when(boolean value, Then<T>... thenPredicates) {
        return thenValidation(value, thenPredicates);
    }

    @SafeVarargs
    private final ValidationEngine<T, FV> thenValidation(boolean whenConditionResult, Then<T>... thenPredicates) {
        if (whenConditionResult) {
            Arrays.stream(thenPredicates)
                    .forEach(p -> must(p.getPredicate(), p.getMessage()));
        }
        return ValidationEngine.this;
    }

    public <NEW_TYPE> ValidationEngine<NEW_TYPE, ValidationEngine> mustConvert(Function<T, NEW_TYPE> conversionFunction) {
        return mustConvert(conversionFunction, "mustConvert");
    }

    public <NEW_TYPE> ValidationEngine<NEW_TYPE, ValidationEngine> mustConvert(Function<T, NEW_TYPE> conversionFunction, String message) {
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
        if (failOnFirst) {
            stopValidation = true;
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

    private <NEW_TYPE> ValidationEngine<NEW_TYPE, ValidationEngine> copyValidator(NEW_TYPE value) {
        ValidationEngine<NEW_TYPE, ValidationEngine> newValidator = new ValidationEngine<>(fieldName, value, note);
        newValidator.setIgnore(ignore);
        newValidator.setNullIsValid(nullIsValid);
        newValidator.setStopValidation(stopValidation);
        newValidator.setFailOnFirst(failOnFirst);
        return newValidator;
    }

    protected void setFailOnFirst(boolean failOnFirst) {
        this.failOnFirst = failOnFirst;
    }

    protected void setStopValidation(boolean stopValidation) {
        this.stopValidation = stopValidation;
    }

    void setNullIsValid(boolean nullIsValid) {
        this.nullIsValid = nullIsValid;
    }

    void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }


}
