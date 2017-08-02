package io.validly;

import org.junit.Test;

import java.util.List;
import java.util.function.BiConsumer;

import static io.validly.NoteTestUtil.failure;
import static io.validly.NoteTestUtil.success;

public class ValidationEngineIntegerTest {

    private final BiConsumer<Integer, List<String>> valueMustBeAtLeast_5 = (value, note) ->
            NoteFirstValidator.valid(value, note)
                    .canBeNull()
                    .valueMustBeAtLeast(5, "too small");

    private final BiConsumer<Integer, List<String>> valueMustNotExceed_10 = (value, note) ->
            NoteFirstValidator.valid(value, note)
                    .canBeNull()
                    .valueMustNotExceed(10, "too big");


    private final BiConsumer<Integer, List<String>> valueMustBeWithin_100_200 = (value, note) ->
            NoteFirstValidator.valid(value, note)
                    .canBeNull()
                    .valueMustBeWithin(100, 200, "not in range");

    @Test
    public void valueMustBeAtLeast_shouldFail_whenValueUnder() throws Exception {
        failure(4, valueMustBeAtLeast_5, "too small");
    }

    @Test
    public void valueMustBeAtLeast_shouldPass_whenValueSame() throws Exception {
        success(5, valueMustBeAtLeast_5);
    }

    @Test
    public void valueMustBeAtLeast_shouldPass_whenValueOver() throws Exception {
        success(10, valueMustBeAtLeast_5);
    }

    @Test
    public void valueMustNotExceed_shouldFail_whenValueOver() throws Exception {
        failure(50, valueMustNotExceed_10, "too big");
    }

    @Test
    public void valueMustNotExceed_shouldPass_whenValueSame() throws Exception {
        success(10, valueMustNotExceed_10);
    }

    @Test
    public void valueMustNotExceed_shouldPass_whenValueUnder() throws Exception {
        success(-1, valueMustNotExceed_10);
    }

    @Test
    public void valueMustBeWithin_shouldFail_whenValueUnder() throws Exception {
        failure(95, valueMustBeWithin_100_200, "not in range");
    }

    @Test
    public void valueMustBeWithin_shouldFail_whenValueOver() throws Exception {
        failure(300, valueMustBeWithin_100_200, "not in range");
    }

    @Test
    public void valueMustBeWithin_shouldFail_whenValueInRange() throws Exception {
        success(150, valueMustBeWithin_100_200);
    }

}
