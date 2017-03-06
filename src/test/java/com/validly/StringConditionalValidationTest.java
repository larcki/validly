package com.validly;

import com.validly.validator.HashMapValidlyNote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class StringConditionalValidationTest {

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

//TODO test requiredWhen or when-then conditionals here?


//        field("fieldName", fieldValue, note)
//                .requiredWhen(customer.getAge() >= 18, "Is required for adults")
//                .mustNotBeBlank("Can not be empty")
//                .when(s -> s.startsWith("Ö"))
//                .then()
//                .lengthMustBeWithin(1, 50, "Must be 1 to 50 characters long");

        if (expectFailure) {
            assertEquals(expectedError, note.getValidationNotes().get("firstName"));
        } else {
            assertTrue(note.getValidationNotes().isEmpty());
        }
    }

}
