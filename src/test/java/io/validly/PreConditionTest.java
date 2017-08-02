package io.validly;

import org.junit.Test;

import java.util.List;
import java.util.function.BiConsumer;

import static io.validly.NoteTestUtil.failure;
import static io.validly.NoteTestUtil.success;

public class PreConditionTest {

    private final BiConsumer<Object, List<String>> mustNotBeNull = (value, note) ->
            NoteFirstValidator.valid(value, note)
                    .mustNotBeNull("value is null");

    private final BiConsumer<Object, List<String>> mustNotBeNull_withFailingSubsequentPredicate = (value, note) ->
            NoteAllValidator.valid(value, note)
                    .mustNotBeNull("value is null")
                    .must(s -> false, "subsequent predicate failed");

    private final BiConsumer<String, List<String>> mustNotBeBlank = (value, note) ->
            NoteFirstValidator.valid(value, note)
                    .mustNotBeBlank("value is blank");

    private final BiConsumer<String, List<String>> mustNotBeBlank_withFailingSubsequentPredicate = (value, note) ->
            NoteAllValidator.valid(value, note)
                    .mustNotBeBlank("value is blank")
                    .must(s -> false, "subsequent predicate failed");

    private final BiConsumer<String, List<String>> canBeNull = (value, note) ->
            NoteFirstValidator.valid(value, note)
                    .canBeNull();

    private static BiConsumer<String, List<String>> mustNotBeNullWhen(boolean conditionResult) {
        return (value, note) ->
                NoteFirstValidator.valid(value, note)
                        .mustNotBeNullWhen(conditionResult, "value is null");
    }

    private static BiConsumer<String, List<String>> validateWhen(boolean conditionResult) {
        return (value, note) ->
                NoteFirstValidator.valid(value, note)
                        .validateWhen(conditionResult)
                        .must(s -> false, "predicate evaluated");
    }

    @Test
    public void mustNotBeNull_shouldFail_whenNullValue() throws Exception {
        failure(null, mustNotBeNull, "value is null");
    }

    @Test
    public void mustNotBeNull_shouldPass_whenNonNullValue() throws Exception {
        success(new Object(), mustNotBeNull);
    }

    @Test
    public void mustNotBeNull_noteAllMode_shouldNotEvaluateSubsequentPredicate_whenValueNull() throws Exception {
        failure(null, mustNotBeNull_withFailingSubsequentPredicate, "value is null");
    }

    @Test
    public void mustNotBeBlank_shouldFail_whenEmptyValue() throws Exception {
        failure("", mustNotBeBlank, "value is blank");
    }

    @Test
    public void mustNotBeBlank_shouldFail_whenValueOnlyHasSpaces() throws Exception {
        failure("    ", mustNotBeBlank, "value is blank");
    }

    @Test
    public void mustNotBeBlank_shouldPass_whenNonBlankValue() throws Exception {
        success("not blank", mustNotBeBlank);
    }

    @Test
    public void mustNotBeBlank_noteAllMode_shouldNotEvaluateSubsequentPredicate_whenValueNull() throws Exception {
        failure(null, mustNotBeBlank_withFailingSubsequentPredicate, "value is blank");
    }

    @Test
    public void canBeNull_shouldPass_whenNullValue() throws Exception {
        success(null, canBeNull);
    }

    @Test
    public void canBeNull_shouldPass_whenNonNullValue() throws Exception {
        success("not null", canBeNull);
    }

    @Test
    public void mustNotBeNullWhen_shouldFailOnNull_whenConditionIsTrue() throws Exception {
        BiConsumer<String, List<String>> rule = mustNotBeNullWhen(true);
        failure(null, rule, "value is null");
    }

    @Test
    public void mustNotBeNullWhen_shouldPassNonNullValue_whenConditionIsTrue() throws Exception {
        BiConsumer<String, List<String>> rule = mustNotBeNullWhen(true);
        success("any value", rule);
    }

    @Test
    public void mustNotBeNullWhen_shouldPassOnNull_whenConditionIsFalse() throws Exception {
        BiConsumer<String, List<String>> rule = mustNotBeNullWhen(false);
        success(null, rule);
    }

    @Test
    public void validateWhen_shouldNotEvaluateSubsequentPredicates_whenConditionIsFalse() throws Exception {
        success("value", validateWhen(false));
    }

    @Test
    public void validateWhen_shouldEvaluateSubsequentPredicates_whenConditionIsTrue() throws Exception {
        failure("value", validateWhen(true), "predicate evaluated");
    }

}
