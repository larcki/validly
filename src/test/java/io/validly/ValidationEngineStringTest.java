package io.validly;

import org.junit.Test;

import java.util.List;
import java.util.function.BiConsumer;

import static io.validly.NoteTestUtil.*;

public class ValidationEngineStringTest {

    private final BiConsumer<String, List<String>> lengthMustNotExceed_5 = (value, note) ->
            NoteFirstValidator.valid(value, note)
                    .canBeNull()
                    .lengthMustNotExceed(5, "too long");

    private final BiConsumer<String, List<String>> lengthMustBeAtLeast_3 = (value, note) ->
            NoteFirstValidator.valid(value, note)
                    .canBeNull()
                    .lengthMustBeAtLeast(3, "too short");

    private final BiConsumer<String, List<String>> lengthMustBeWithin_5_7 = (value, note) ->
            NoteFirstValidator.valid(value, note)
                    .canBeNull()
                    .lengthMustBeWithin(5, 7, "not in range");

    private final BiConsumer<String, List<String>> mustContain_a = (value, note) ->
            NoteFirstValidator.valid(value, note)
                    .canBeNull()
                    .mustContain("a", "does not contain char");

    private final BiConsumer<String, List<String>> mustStartWith_a = (value, note) ->
            NoteFirstValidator.valid(value, note)
                    .canBeNull()
                    .mustStartWith("a", "does not start with");

    @Test
    public void lengthMustNotExceed_shouldFail_whenValueTooLong() throws Exception {
        failure("more than 5 chars", lengthMustNotExceed_5, "too long");
    }

    @Test
    public void lengthMustBeAtLeast_shouldPass_whenValueShortEnough() throws Exception {
        success("short", lengthMustNotExceed_5);
    }

    @Test
    public void lengthMustBeAtLeast_shouldFail_whenValueTooShort() throws Exception {
        failure("ab", lengthMustBeAtLeast_3, "too short");
    }

    @Test
    public void lengthMustBeAtLeast_shouldPass_whenValueLongEnough() throws Exception {
        success("long enough", lengthMustBeAtLeast_3);
    }

    @Test
    public void lengthMustBeWithin_shouldFail_whenValueTooShort() throws Exception {
        failure("abc", lengthMustBeWithin_5_7, "not in range");
    }

    @Test
    public void lengthMustBeWithin_shouldFail_whenValueTooLong() throws Exception {
        failure("1234566789", lengthMustBeWithin_5_7, "not in range");
    }

    @Test
    public void lengthMustBeWithin_shouldPass_whenValueWithinRange() throws Exception {
        success("123456", lengthMustBeWithin_5_7);
    }

    @Test
    public void mustContain_shouldFail_whenNotContainChar() throws Exception {
        failure("bcd", mustContain_a, "does not contain char");
    }

    @Test
    public void mustContain_shouldPass_whenContainsChar() throws Exception {
        success("bcad", mustContain_a);
    }

    @Test
    public void mustStartWith_shouldFail_whenNotStartWith() throws Exception {
        failure("bcd", mustStartWith_a, "does not start with");
    }

    @Test
    public void mustStartWith_shouldPass_whenStartsWith() throws Exception {
        success("abc", mustStartWith_a);
    }

}
