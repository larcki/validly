package io.validly;

import io.validly.excpetion.ValidationErrorException;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.validly.NoteFirstValidator.valid;
import static io.validly.NoteTestUtil.failure;
import static io.validly.NoteTestUtil.success;
import static java.time.format.DateTimeFormatter.ofPattern;
import static junit.framework.Assert.assertTrue;

public class ValidationEngineTest {

    private final BiConsumer<String, List<String>> mustConvert_date = (value, note) ->
            NoteFirstValidator.valid(value, note)
                    .canBeNull()
                    .mustConvert(s -> LocalDate.parse(s, ofPattern("dd.MM.yyyy")), "convert failed");

    private static BiConsumer<String, List<String>> must(Predicate<String> predicate) {
        return (value, note) ->
                NoteFirstValidator.valid(value, note)
                        .canBeNull()
                        .must(predicate, "predicate failed");
    }

    private static BiConsumer<String, List<String>> mustFatally(Predicate<String> predicate) {
        return (value, note) ->
                NoteAllValidator.valid(value, note)
                        .canBeNull()
                        .mustFatally(predicate, "predicate failed")
                        .must(s -> false, "subsequent predicate failed");
    }

    private static BiConsumer<String, List<String>> mustConvert(Function<String, LocalDate> function) {
        return (value, note) ->
                NoteAllValidator.valid(value, note)
                        .canBeNull()
                        .mustConvert(function, "must convert failed")
                        .must(s -> false, "subsequent predicate failed");
    }

    private static BiConsumer<String, List<String>> when(boolean whenCondition) {
        return (value, note) -> {
            NoteFirstValidator.valid(value, note)
                    .canBeNull()
                    .when(whenCondition, Then.must(s -> false, "then failed"));
        };
    }

    private static BiConsumer<String, List<String>> when_predicate(Predicate<String> predicate) {
        return (value, note) -> {
            NoteFirstValidator.valid(value, note)
                    .canBeNull()
                    .when(predicate, Then.must(s -> false, "then failed"));
        };
    }

    @Test
    public void must_shouldFail_whenPredicateFalse() throws Exception {
        failure("value", must(s -> false), "predicate failed");
    }

    @Test
    public void must_shouldPass_whenPredicateTrue() throws Exception {
        success("value", must(s -> true));
    }

    @Test
    public void mustFatally_shouldFailAndNotEvaluateSubsequentPredicates_whenPredicateFalse() throws Exception {
        failure("value", mustFatally(s -> false), "predicate failed");
    }

    @Test
    public void mustFatally_shouldFailOnSubsequentPredicate_whenPredicateTrue() throws Exception {
        failure("value", mustFatally(s -> true), "subsequent predicate failed");
    }

    @Test
    public void mustConvert_shouldPass_whenValidValue() throws Exception {
        success("12.12.2017", mustConvert_date);
    }

    @Test
    public void mustConvert_shouldFail_whenInvalidValue() throws Exception {
        failure("invalid", mustConvert_date, "convert failed");
    }

    @Test
    public void mustConvert_shouldFailAndNotEvaluateSubsequentPredicates_whenFunctionThrowsException() throws Exception {
        BiConsumer<String, List<String>> rule = mustConvert(s -> {
            throw new RuntimeException("test");
        });
        failure("value", rule, "must convert failed");
    }

    @Test
    public void mustConvert_shouldFailAndNotEvaluateSubsequentPredicates_whenFunctionReturnsNull() throws Exception {
        BiConsumer<String, List<String>> rule = mustConvert(s -> null);
        failure("value", rule, "must convert failed");
    }

    @Test
    public void when_shouldEvaluateFailingThenPredicate_whenWhenConditionTrue() throws Exception {
        BiConsumer<String, List<String>> rule = when(true);
        failure("value", rule, "then failed");
    }

    @Test
    public void when_shouldNotEvaluateFailingThenPredicate_whenWhenConditionFalse() throws Exception {
        BiConsumer<String, List<String>> rule = when(false);
        success("value", rule);
    }

    @Test
    public void whenWithPredicate_shouldEvaluateFailingThenPredicate_whenWhenPredicateTrue() throws Exception {
        BiConsumer<String, List<String>> rule = when_predicate(s -> true);
        failure("value", rule, "then failed");
    }

    @Test
    public void whenWithPredicate_shouldNotEvaluateFailingThenPredicate_whenWhenPredicateFalse() throws Exception {
        BiConsumer<String, List<String>> rule = when_predicate(s -> false);
        success("value", rule);
    }

    @Test
    @Ignore
    public void testDate() throws Exception {
        LocalDate date = LocalDate.of(2016, 12, 3);

        Notification note = new Notification();

        valid(date, "", note)
                .mustNotBeNull("mustNotBeNull")
                .when(localDate -> localDate.isBefore(LocalDate.now()),
                        Then.must(LocalDate::isLeapYear, "must be leap year when in the past"))
                .must(localDate -> localDate.getDayOfMonth() == 3, "must be 3 day of month");

        print(note);
    }

    @Test
    @Ignore
    public void customValidationAndConditions() throws Exception {
        Address address = new Address();
        address.setPostCode("");

        Notification note = new Notification();

        valid(address.getPostCode(), "postCode", note)
                .mustNotBeNull("mustNotBeNull")
                .must(s -> s.matches("//your.regex+"), "customMustCondition");

    }

    @Test
    @Ignore
    public void testListNote() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("J");
        customer.setAge(0);
        customer.setReferralCode("RSEF.111122223333");
        customer.setSsn("tooLongValue");

        List<String> note = new ArrayList<>();

        NoteFirstValidator.valid(customer.getName(), note)
                .mustNotBeBlank("mustNotBeBlank")
                .must(s -> s.startsWith("K"), "start with fails")
                .lengthMustNotExceed(100, "lengthMustNotExceed");

        NoteFirstValidator.valid(customer.getAge(), note)
                .mustNotBeNull("mustNotBeNull")
                .valueMustBeAtLeast(1, "valueMustBeAtLeast")
                .valueMustNotExceed(130, "valueMustNotExceed");

        NoteFirstValidator.valid(customer.getReferralCode(), note)
                .canBeNull()
                .mustStartWith("REF", "mustStartWith")
                .mustContain("-", "mustContain")
                .lengthMustBeWithin(10, 20, "lengthMustBeWithin");

        NoteFirstValidator.valid(customer.getSsn(), note)
                .mustNotBeNullWhen(customer.getAge() > 18, "mustNotBeNull")
                .lengthMustNotExceed(10, "lengthMustNotExceed");


        print(note);
    }


    @Test(expected = ValidationErrorException.class)
    public void testFailFast() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("J");
        customer.setAge(0);
        customer.setReferralCode("REF.111122223333");
        customer.setSsn("tooLongValue");

        FailFastValidator.valid(customer.getName())
                .mustNotBeBlank("mustNotBeBlank")
                .lengthMustBeAtLeast(2, "lengthMustBeAtLeast")
                .lengthMustNotExceed(100, "lengthMustNotExceed");

        FailFastValidator.valid(customer.getAge())
                .mustNotBeNull("mustNotBeNull")
                .valueMustBeAtLeast(1, "valueMustBeAtLeast")
                .valueMustNotExceed(130, "valueMustNotExceed");

        FailFastValidator.valid(customer.getReferralCode())
                .canBeNull()
                .mustStartWith("REF", "mustStartWith")
                .mustContain("-", "mustContain")
                .lengthMustBeWithin(10, 20, "lengthMustBeWithin");

        FailFastValidator.valid(customer.getSsn())
                .mustNotBeNullWhen(customer.getAge() > 18, "mustNotBeNull")
                .lengthMustNotExceed(10, "lengthMustNotExceed");

    }

    @Test
    @Ignore
    public void testCustomClass() throws Exception {
        Instant instant = Instant.now().plus(5, ChronoUnit.DAYS);

        List<String> note = new ArrayList<>();

        NoteFirstValidator.valid(instant, note)
                .mustNotBeNull("mustNotBeNull")
                .must(i -> i.isAfter(Instant.now()), "customMustCondition")
                .must(i -> i.isBefore(Instant.now().plus(3, ChronoUnit.DAYS)), "AAA");

        print(note);
    }

    @Test
    @Ignore
    public void testWhenWithString() throws Exception {
        String value = "salu";

        Notification note = new Notification();

        valid(value, "field", note)
                .mustNotBeNull("mustNotBeNull")
                .when(true, Then.must(s -> s.startsWith("v"), "must start with"))
                .must(s1 -> s1.length() > 5, "customMustCondition");

        print(note);

    }

    @Test(expected = ValidationErrorException.class)
    public void testWhenWithStringFailFast() throws Exception {
        String value = "valu";

        FailFastValidator.valid(value)
                .mustNotBeNull("mustNotBeNull")
                .when(true, Then.must(s -> s.startsWith("v"), "must start with"))
                .must(s1 -> s1.length() > 5, "customMustCondition");
    }

    @Test
    public void testNullIsValidWithWhen() throws Exception {
        String value = null;
        List<String> note = new ArrayList<>();

        NoteFirstValidator.valid(value, note)
                .canBeNull()
                .when(s -> s.contains("something"));

        assertTrue(note.isEmpty());
    }

    @Test
    public void testValidateOnlyWhen() throws Exception {
        String value = "vaa";
        Notification note = new Notification();

        NoteAllValidator.valid(value, "", note)
                .when(true,
                        Then.mustNotBeNull("can't be null"),
                        Then.must(s -> s.startsWith("v"), "must start with v"))
                .must(s1 -> s1.length() > 2, "too short");

        assertTrue(note.getMessages().isEmpty());
    }

    private void print(Notification note) {
        if (note.isNotEmpty()) {
            note.getMessages().forEach((field, reason) -> System.out.println(field + ": " + reason));
        }
    }

    private void print(List<String> note) {
        if (!note.isEmpty()) {
            note.forEach(System.out::println);
        }
    }


}
