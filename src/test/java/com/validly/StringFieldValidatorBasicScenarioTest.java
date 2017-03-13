package com.validly;

import com.validly.validator.HashMapValidlyNote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static com.validly.validator.FieldValidator.field;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class StringFieldValidatorBasicScenarioTest {

    private static final String NOT_NULL_FAIL = "mustNotBeNull failed";
    private static final String NOT_EMPTY_FAIL = "must not be empty failed";
    private static final String NOT_BLANK_FAIL = "must not be blank failed";
    private static final String LENGTH_MIN_FAIL = "length must be at least failed";
    private static final String LENGTH_MAX_FAIL = "length must not exceed failed";

    @Parameter(0)
    public String fieldValue;

    @Parameter(1)
    public String expectedError;

    @Parameter(2)
    public boolean expectFailure;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                validationFailure(null, NOT_NULL_FAIL),
                validationFailure("", NOT_EMPTY_FAIL),
                validationFailure("     ", NOT_BLANK_FAIL),
                validationFailure("a", LENGTH_MIN_FAIL),
                validationFailure("more than twenty characters", LENGTH_MAX_FAIL),
                validationSuccess("valid"));
    }

    @Test
    public void stringFieldValidation() throws Exception {
        HashMapValidlyNote note = new HashMapValidlyNote();

        field("fieldName", fieldValue, note)
                .mustNotBeNull(NOT_NULL_FAIL)
                .mustNotBeEmpty(NOT_EMPTY_FAIL)
                .mustNotBeBlank(NOT_BLANK_FAIL)
                .lengthMustBeAtLeast(2, LENGTH_MIN_FAIL)
                .lengthMustNotExceed(20, LENGTH_MAX_FAIL);

        if (expectFailure) {
            assertEquals(expectedError, note.getValidationNotes().get("fieldName"));
        } else {
            assertTrue(note.getValidationNotes().isEmpty());
        }

    }

    private static Object[] validationFailure(Object value, String expectedMessage) {
        return new Object[]{value, expectedMessage, true};
    }

    private static Object[] validationSuccess(Object value) {
        return new Object[]{value, "", false};
    }

}
