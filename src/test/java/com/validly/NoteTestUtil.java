package com.validly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NoteTestUtil {

    static <T> void success(T value, BiConsumer<T, List<String>> rule) {
        List<String> note = new ArrayList<>();
        rule.accept(value, note);
        assertTrue("Expected empty note", note.isEmpty());
    }

    static <T> void failure(T value, BiConsumer<T, List<String>> rule, String... expectedMessage) {
        //TODO notification object
        List<String> note = new ArrayList<>();
        rule.accept(value, note);
        assertEquals(Arrays.asList(expectedMessage), note);
    }
}
