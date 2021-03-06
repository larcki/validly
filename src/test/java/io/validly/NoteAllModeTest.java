package io.validly;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class NoteAllModeTest {

    @Test
    public void testWhen() throws Exception {
        BiConsumer<String, List<String>> rule = (value, note) -> NoteAllValidator.valid(value, note)
                .mustNotBeNull("mustNotBeNull")
                .when(s -> s.startsWith("123"), Then.must(s -> s.endsWith("789"), "must end 789"))
                .when(s -> s.startsWith("987"), Then.must(s -> s.endsWith("321"), "must end 321"));

        NoteTestUtil.success("123 789", rule);
        NoteTestUtil.success("987 321", rule);
        NoteTestUtil.success("111 111", rule);
        NoteTestUtil.failure("123 123", rule, "must end 789");
        NoteTestUtil.failure("987 123", rule, "must end 321");
        NoteTestUtil.failure(null, rule, "mustNotBeNull");
    }

    @Test
    public void testMust() throws Exception {
        BiConsumer<String, List<String>> rule = (value, note) -> NoteAllValidator.valid(value, note)
                .mustNotBeNull("mustNotBeNull")
                .must(s -> s.startsWith("123"), "must start 123")
                .must(s -> s.endsWith("999"), "must end 999");

        NoteTestUtil.success("123999", rule);
        NoteTestUtil.success("123 999", rule);
        NoteTestUtil.failure("123", rule, "must end 999");
        NoteTestUtil.failure("999", rule, "must start 123");
        NoteTestUtil.failure("111", rule, "must start 123", "must end 999");
        NoteTestUtil.failure(null, rule, "mustNotBeNull");
    }

    @Test
    public void testStringBasics() throws Exception {
        BiConsumer<String, List<String>> rule = (value, note) -> NoteAllValidator.valid(value, note)
                .mustNotBeBlank("mustNotBeBlank")
                .lengthMustBeAtLeast(2, "lengthMustBeAtLeast")
                .lengthMustNotExceed(3, "lengthMustNotExceed");

        NoteTestUtil.success("22", rule);
        NoteTestUtil.success("333", rule);
        NoteTestUtil.failure("1", rule, "lengthMustBeAtLeast");
        NoteTestUtil.failure("4444", rule, "lengthMustNotExceed");
        NoteTestUtil.failure("", rule, "mustNotBeBlank");
    }

    @Test
    public void testStringBasics_usingNoteObject() throws Exception {
        BiConsumer<String, ValidlyNote> rule = (value, note) -> NoteAllValidator.valid(value, "identifier", note)
                .mustNotBeBlank("mustNotBeBlank")
                .lengthMustBeAtLeast(2, "lengthMustBeAtLeast")
                .lengthMustNotExceed(3, "lengthMustNotExceed");

        NoteTestUtil.successWithNote("22", rule);
        NoteTestUtil.successWithNote("333", rule);
        NoteTestUtil.failureWithNote("1", rule, note("identifier", "lengthMustBeAtLeast"));
        NoteTestUtil.failureWithNote("4444", rule, note("identifier", "lengthMustNotExceed"));
        NoteTestUtil.failureWithNote("", rule, note("identifier", "mustNotBeBlank"));
    }

    @Test
    public void testStringAdvanced() throws Exception {
        BiConsumer<String, List<String>> rule = (value, note) -> NoteAllValidator.valid(value, note)
                .mustNotBeBlank("mustNotBeBlank")
                .lengthMustBeWithin(4, 7, "lengthMustBeWithin")
                .mustContain("-", "mustContain")
                .mustStartWith("abc", "mustStartWith");

        NoteTestUtil.success("abc-x", rule);
        NoteTestUtil.failure("abc", rule, "lengthMustBeWithin", "mustContain");
        NoteTestUtil.failure("add", rule, "lengthMustBeWithin", "mustContain", "mustStartWith");
        NoteTestUtil.failure("abcx", rule, "mustContain");
        NoteTestUtil.failure("", rule, "mustNotBeBlank");
        NoteTestUtil.failure(null, rule, "mustNotBeBlank");
    }

    @Test
    public void testIntegerBasics() throws Exception {
        BiConsumer<Integer, List<String>> rule = (value, note) -> NoteAllValidator.valid(value, note)
                .mustNotBeNull("mustNotBeNull")
                .valueMustBeAtLeast(1, "valueMustBeAtLeast")
                .valueMustNotExceed(10, "valueMustNotExceed");

        NoteTestUtil.success(1, rule);
        NoteTestUtil.success(3, rule);
        NoteTestUtil.success(10, rule);
        NoteTestUtil.failure(0, rule, "valueMustBeAtLeast");
        NoteTestUtil.failure(15, rule, "valueMustNotExceed");
        NoteTestUtil.failure(null, rule, "mustNotBeNull");
    }

    private ValidlyNote note(String identifier, String... messages) {
        ValidlyNote validlyNote = new Notification();
        Arrays.asList(messages).forEach(s -> validlyNote.addMessage(identifier, s));
        return validlyNote;
    }

}
