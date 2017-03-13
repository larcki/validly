package com.validly;

import com.validly.validator.HashMapValidlyNote;
import com.validly.validator.ValidationPredicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static com.validly.validator.FieldValidator.field;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class StringFieldValidatorConditionalScenarioTest {

    private static final String NOT_NULL_FAIL = "mustNotBeNull failed";
    public static final String LENGTH_RANGE_NOT_MET = "Length range not met";
    public static final String FIRST_THEN_FAILED = "first then failed";
    public static final String SECOND_THEN_FAILED = "second then failed";

    @Parameterized.Parameter(0)
    public String fieldValue;

    @Parameterized.Parameter(1)
    public boolean whenCondition;

    @Parameterized.Parameter(2)
    public String expectedError;

    @Parameterized.Parameter(3)
    public boolean expectFailure;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                validationFailure(null, true, NOT_NULL_FAIL),
                validationSuccess(null, false),
                validationFailure("evaluate-failure", false, FIRST_THEN_FAILED),
                validationFailure("evaluate-first-valid", false, SECOND_THEN_FAILED),
                validationSuccess("evaluate-first-valid-second-valid", false),
                validationFailure("", false, LENGTH_RANGE_NOT_MET)
        );
    }

    @Test
    public void basicStringValidation() throws Exception {
        HashMapValidlyNote note = new HashMapValidlyNote();

        field("fieldName", fieldValue, note)
                .mustNotBeNullWhen(whenCondition, NOT_NULL_FAIL)
                .when(s -> s != null && s.startsWith("evaluate"))
                .then(ValidationPredicate.must(s -> s.contains("first-valid"), FIRST_THEN_FAILED),
                        ValidationPredicate.must(s -> s.contains("second-valid"), SECOND_THEN_FAILED))
                .lengthMustBeWithin(1, 50, LENGTH_RANGE_NOT_MET);

        if (expectFailure) {
            assertEquals(expectedError, note.getValidationNotes().get("fieldName"));
        } else {
            assertTrue(note.getValidationNotes().isEmpty());
        }
    }

    private static Object[] validationFailure(Object value, boolean whenCondition, String expectedMessage) {
        return new Object[]{value, whenCondition, expectedMessage, true};
    }

    private static Object[] validationSuccess(Object value, boolean whenCondition) {
        return new Object[]{value, whenCondition, "", false};
    }

}
