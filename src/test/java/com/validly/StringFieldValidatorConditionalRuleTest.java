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

@RunWith(Parameterized.class)
public class StringFieldValidatorConditionalRuleTest {

    @Parameterized.Parameter(0)
    public String fieldValue;

    @Parameterized.Parameter(1)
    public String expectedError;

    @Parameterized.Parameter(2)
    public boolean expectFailure;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"", "", true},
        });
    }

    @Test
    public void basicStringValidation() throws Exception {
        HashMapValidlyNote note = new HashMapValidlyNote();

        //TODO Scenario test for rule with conditionals


        field("fieldName", fieldValue, note)
                .mustNotBeNull("mustNotBeNull failed")
                .mustNotBeBlank("Can not be empty")
                .when(s -> s.startsWith("Ö"))
                .then()
                .lengthMustBeWithin(1, 50, "Must be 1 to 50 characters long");

        if (expectFailure) {
            assertEquals(expectedError, note.getValidationNotes().get("firstName"));
        } else {
            assertTrue(note.getValidationNotes().isEmpty());
        }
    }

}
