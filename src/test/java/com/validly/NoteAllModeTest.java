package com.validly;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static com.validly.NoteTestUtil.*;
import static com.validly.NoteAllValidator.field;

public class NoteAllModeTest {

    @Test
    public void testWhen() throws Exception {
        BiConsumer<String, List<String>> rule = (value, note) -> field(value, note)
                .mustNotBeNull()
                .when(s -> s.startsWith("123"), Then.must(s -> s.endsWith("789"), "must end 789"))
                .when(s -> s.startsWith("987"), Then.must(s -> s.endsWith("321"), "must end 321"));

        success("123 789", rule);
        success("987 321", rule);
        success("111 111", rule);
        failure("123 123", rule, "must end 789");
        failure("987 123", rule, "must end 321");
        failure(null, rule, "mustNotBeNull");
    }

    @Test
    public void testMust() throws Exception {
        BiConsumer<String, List<String>> rule = (value, note) -> field(value, note)
                .mustNotBeNull()
                .must(s -> s.startsWith("123"), "must start 123")
                .must(s -> s.endsWith("999"), "must end 999");

        success("123999", rule);
        success("123 999", rule);
        failure("123", rule, "must end 999");
        failure("999", rule, "must start 123");
        failure("111", rule, "must start 123", "must end 999");
        failure(null, rule, "mustNotBeNull");
    }

    @Test
    public void testStringBasics() throws Exception {
        BiConsumer<String, List<String>> rule = (value, note) -> field(value, note)
                .mustNotBeBlank()
                .lengthMustBeAtLeast(2)
                .lengthMustNotExceed(3);

        success("22", rule);
        success("333", rule);
        failure("1", rule, "lengthMustBeAtLeast");
        failure("4444", rule, "lengthMustNotExceed");
        failure("", rule, "mustNotBeBlank");
    }

    @Test
    public void testStringBasics_usingNoteObject() throws Exception {
        BiConsumer<String, ValidlyNote> rule = (value, note) -> field("identifier", value, note)
                .mustNotBeBlank()
                .lengthMustBeAtLeast(2)
                .lengthMustNotExceed(3);

        successWithNote("22", rule);
        successWithNote("333", rule);

        failureWithNote("1", rule, note("identifier", "lengthMustBeAtLeast"));
        failureWithNote("4444", rule, note("identifier", "lengthMustNotExceed"));
        failureWithNote("", rule, note("identifier", "mustNotBeBlank"));
    }

    private ValidlyNote note(String identifier, String... messages) {
        ValidlyNote validlyNote = new Notification();
        Arrays.asList(messages).forEach(s -> validlyNote.addMessage(identifier, s));
        return validlyNote;
    }

    @Test
    public void testStringAdvanced() throws Exception {
        BiConsumer<String, List<String>> rule = (value, note) -> field(value, note)
                .mustNotBeBlank()
                .lengthMustBeWithin(4, 7)
                .mustContain("-")
                .mustStartWith("abc");

        success("abc-x", rule);
        failure("abc", rule, "lengthMustBeWithin", "mustContain");
        failure("add", rule, "lengthMustBeWithin", "mustContain", "mustStartWith");
        failure("abcx", rule, "mustContain");
        failure("", rule, "mustNotBeBlank");
        failure(null, rule, "mustNotBeBlank");
    }

    @Test
    public void testIntegerBasics() throws Exception {
        BiConsumer<Integer, List<String>> rule = (value, note) -> field(value, note)
                .mustNotBeNull()
                .valueMustBeAtLeast(1)
                .valueMustNotExceed(10);

        success(1, rule);
        success(3, rule);
        success(10, rule);
        failure(0, rule, "valueMustBeAtLeast");
        failure(15, rule, "valueMustNotExceed");
        failure(null, rule, "mustNotBeNull");
    }

}
