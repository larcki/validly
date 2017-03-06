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
public class StringBasicValidationTest {

    @Parameter(0)
    public String fieldValue;

    @Parameter(1)
    public String expectedError;

    @Parameter(2)
    public boolean expectFailure;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {null, "required failed", true},
                {"", "must not be empty failed", true},
                {"     ", "must not be blank failed", true},
                {"a", "length must be at least failed", true},
                {"more than twenty characters", "length must not exceed failed", true},
                {"valid", "", false}
        });
    }

    @Test
    public void basicStringValidation() throws Exception {
        HashMapValidlyNote note = new HashMapValidlyNote();

        field("fieldName", fieldValue, note)
                .required("required failed")
                .mustNotBeEmpty("must not be empty failed")
                .mustNotBeBlank("must not be blank failed")
                .lengthMustBeAtLeast(2, "length must be at least failed")
                .lengthMustNotExceed(20, "length must not exceed failed");

        if (expectFailure) {
            assertEquals(expectedError, note.getValidationNotes().get("fieldName"));
        } else {
            assertTrue(note.getValidationNotes().isEmpty());
        }

//        field("age", customer.getAge(), note)
//                .req
    }

}
