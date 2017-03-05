package com.validly.validator;

public final class StringFieldValidator extends FieldValidator<String, StringFieldValidator> {

    protected StringFieldValidator(String fieldName, String value, ValidlyNote note) {
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


