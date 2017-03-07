package com.validly;

import com.validly.validator.HashMapValidlyNote;
import com.validly.validator.ValidlyNote;
import org.junit.Test;

import java.util.function.Consumer;

import static com.validly.validator.FieldValidator.field;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringFieldValidatorTest {

    @Test
    public void canBeNull() {
        assertTrue(assertCanBeNullValid(null));
        assertTrue(assertCanBeNullValid(""));
        assertTrue(assertCanBeNullValid("  "));
        assertTrue(assertCanBeNullValid("value"));
    }

    @Test
    public void mustNotBeNull() {
        assertFalse(assertMustNotBeNullValid(null));
        assertTrue(assertMustNotBeNullValid(""));
        assertTrue(assertMustNotBeNullValid("   "));
        assertTrue(assertMustNotBeNullValid("value"));
    }

    @Test
    public void lengthMustNotExceed() {
        assertTrue(LengthMustNotExceedValid(5, ""));
        assertTrue(LengthMustNotExceedValid(5, " "));
        assertTrue(LengthMustNotExceedValid(5, "1234"));
        assertTrue(LengthMustNotExceedValid(5, "12345"));
        assertTrue(LengthMustNotExceedValid(0, ""));
        assertFalse(LengthMustNotExceedValid(5, "123456"));
        assertFalse(LengthMustNotExceedValid(5, "      "));
        assertFalse(LengthMustNotExceedValid(0, "1"));
    }

    @Test
    public void lengthMustBeAtLeast() {
        assertFalse(LengthMustBeAtLeastValid(5, ""));
        assertFalse(LengthMustBeAtLeastValid(5, " "));
        assertFalse(LengthMustBeAtLeastValid(5, "1234"));
        assertTrue(LengthMustBeAtLeastValid(5, "12345"));
        assertTrue(LengthMustBeAtLeastValid(0, ""));
        assertTrue(LengthMustBeAtLeastValid(5, "123456"));
        assertTrue(LengthMustBeAtLeastValid(5, "      "));
        assertTrue(LengthMustBeAtLeastValid(0, "1"));
    }

    @Test
    public void lengthMustBeWithin() {
        assertTrue(LengthMustBeWithinValid(1, 3, "1"));
        assertTrue(LengthMustBeWithinValid(1, 3, "12"));
        assertTrue(LengthMustBeWithinValid(1, 3, "123"));
        assertFalse(LengthMustBeWithinValid(1, 3, "1234"));
        assertFalse(LengthMustBeWithinValid(1, 3, ""));
        assertTrue(LengthMustBeWithinValid(1, 3, " "));
        assertTrue(LengthMustBeWithinValid(0, 3, ""));
    }

    private boolean LengthMustBeWithinValid(int min, int max, String value) {
        return isValid(note -> field("", value, note)
                .mustNotBeNull("")
                .lengthMustBeWithin(min, max, ""));
    }

    private boolean LengthMustNotExceedValid(int limit, String value) {
        return isValid(note -> field("", value, note)
                .mustNotBeNull("")
                .lengthMustNotExceed(limit, ""));
    }

    private boolean LengthMustBeAtLeastValid(int limit, String value) {
        return isValid(note -> field("", value, note)
                .mustNotBeNull("")
                .lengthMustBeAtLeast(limit, ""));
    }

    private boolean assertMustNotBeNullValid(String value) {
        return isValid(note -> field("", value, note)
                .mustNotBeNull(""));
    }

    private boolean assertCanBeNullValid(String value) {
        return isValid(note -> field("", value, note)
                .canBeNull());
    }

    private boolean isValid(Consumer<ValidlyNote> validationMethod) {
        HashMapValidlyNote note = new HashMapValidlyNote();
        validationMethod.accept(note);
        return note.getValidationNotes().isEmpty();
    }

}
