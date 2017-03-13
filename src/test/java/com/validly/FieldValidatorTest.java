package com.validly;

import com.validly.validator.HashMapValidlyNote;
import com.validly.validator.ValidationPredicate;
import com.validly.validator.ValidlyNote;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.validly.validator.FieldValidator.field;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FieldValidatorTest {

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
    public void must_shouldResultInValidationFailure_whenPredicateFailure() {
        Predicate<String> failingPredicate = s -> false;
        assertFalse(isValid(note -> field("", "", note)
                .canBeNull()
                .must(failingPredicate, "")));
    }

    @Test
    public void must_shouldResultInValidationOk_whenPredicateTrue() {
        Predicate<String> truePredicate = s -> true;
        assertTrue(isValid(note -> field("", "", note)
                .canBeNull()
                .must(truePredicate, "")));
    }

    @Test
    public void mustNotBeNullWhen_shouldAcceptNull_whenConditionFalse() throws Exception {
        String value = null;
        assertTrue(isValid(note -> field("", value, note)
                .mustNotBeNullWhen(false, "")));
    }

    @Test
    public void mustNotBeNullWhen_shouldFailOnNull_whenConditionTrue() throws Exception {
        String value = null;
        assertFalse(isValid(note -> field("", value, note)
                .mustNotBeNullWhen(true, "")));
    }

    @Test
    public void when_shouldEvaluateThenBlock_whenConditionTrue() throws Exception {
        assertFalse(isValid(note -> field("", "", note)
                .canBeNull()
                .when(true)
                .then(ValidationPredicate.notBlank(""))));
    }

    @Test
    public void when_shouldNotEvaluateThenBlock_whenConditionFalse() throws Exception {
        assertTrue(isValid(note -> field("", "", note)
                .canBeNull()
                .when(false)
                .then(ValidationPredicate.notBlank(""))));
    }

    @Test
    public void when_shouldEvaluateBothThenBlocks_whenConditionTrue() throws Exception {
        assertFalse(isValid(note -> field("", "not blank", note)
                .canBeNull()
                .when(true)
                .then(ValidationPredicate.notBlank(""),
                        ValidationPredicate.must(s -> s.startsWith("a"), ""))));
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
