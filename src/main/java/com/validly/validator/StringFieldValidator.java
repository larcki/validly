package com.validly.validator;

import java.util.Map;

public final class StringFieldValidator extends FieldValidator<String, StringFieldValidator> {

    protected StringFieldValidator(String value) {
        super(value);
    }

    protected StringFieldValidator(String fieldName, String value, Map note) {
        super(fieldName, value, note);
    }

    public StringFieldValidator mustNotBeBlank() {
        return must(ValidationRules.isNotBlank(), Conditions.mustNotBeBlank);
    }

    public StringFieldValidator mustNotBeEmpty() {
        return must(ValidationRules.isNotEmpty(), Conditions.mustNotBeEmpty);
    }

    public StringFieldValidator lengthMustNotExceed(int max) {
        return must(ValidationRules.isWithinMax(max), Conditions.lengthMustNotExceed);
    }

    public StringFieldValidator lengthMustBeAtLeast(int min) {
        return must(ValidationRules.isWithinMin(min), Conditions.lengthMustBeAtLeast);
    }

    public StringFieldValidator lengthMustBeWithin(int min, int max) {
        return must(ValidationRules.isWithinMin(min)
                .and(ValidationRules.isWithinMax(max)), Conditions.lengthMustBeWithin);
    }


}


