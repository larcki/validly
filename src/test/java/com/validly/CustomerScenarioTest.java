package com.validly;

import com.validly.excpetion.ValidationFailureException;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.validly.NoteFirstValidator.valid;
import static java.time.format.DateTimeFormatter.ofPattern;
import static junit.framework.Assert.assertTrue;

public class CustomerScenarioTest {

    @Test
    public void testBasicCustomerValidation() {
        Customer customer = new Customer();
        customer.setFirstName("J");
        customer.setAge(0);
        customer.setReferralCode("REF.111122223333");
        customer.setSsn("tooLongValue");
        List<String> merits = Arrays.asList("one", "two");
        customer.setPhoneNumbers(merits);

        Notification note = new Notification();

        valid(customer.getName(), "firstName", note)
                .mustNotBeBlank("mustNotBeBlank")
                .lengthMustBeAtLeast(2, "lengthMustBeAtLeast")
                .lengthMustNotExceed(100, "lengthMustNotExceed");

        valid(customer.getAge(), "age", note)
                .mustNotBeNull("mustNotBeNull")
                .valueMustBeAtLeast(1, "valueMustBeAtLeast")
                .valueMustNotExceed(130, "valueMustNotExceed");

        valid(customer.getReferralCode(), "referralCode", note)
                .canBeNull()
                .mustStartWith("REX", "mustStartWith")
                .mustContain("-", "mustContain")
                .lengthMustBeWithin(10, 20, "lengthMustBeWithin");

        valid(customer.getSsn(), "ssn", note)
                .mustNotBeNullWhen(customer.getAge() > 18, "mustNotBeNull")
                .lengthMustNotExceed(10, "lengthMustNotExceed");

        valid(customer.getPhoneNumbers(), "merits", note)
                .mustNotBeNull("must not be null")
                .must(strings -> strings.size() > 2, "should have more than two merits");

        print(note);

    }

    @Test
    public void testDateString() throws Exception {
        String date = "12.12.2014";

        Notification note = new Notification();

        valid(date, "date", note)
                .mustNotBeNull("mustNotBeNull")
                .mustConvert(s -> LocalDate.parse(s, ofPattern("dd.MM.yyyy")), "mustConvert")
                .must(d -> d.isAfter(LocalDate.now()), "date must be in the future");

        print(note);
    }

    @Test
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
    public void customValidationAndConditions() throws Exception {
        Address address = new Address();
        address.setPostCode("");

        Notification note = new Notification();

        valid(address.getPostCode(), "postCode", note)
                .mustNotBeNull("mustNotBeNull")
                .must(s -> s.matches("//your.regex+"), "customMustCondition");

        //TODO: more cases
    }

    @Test
    public void testListNote() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("J");
        customer.setAge(0);
        customer.setReferralCode("RSEF.111122223333");
        customer.setSsn("tooLongValue");

        List<String> note = new ArrayList<>();

        valid(customer.getName(), note)
                .mustNotBeBlank("mustNotBeBlank")
                .must(s -> s.startsWith("K"), "start with fails")
                .lengthMustNotExceed(100, "lengthMustNotExceed");

        valid(customer.getAge(), note)
                .mustNotBeNull("mustNotBeNull")
                .valueMustBeAtLeast(1, "valueMustBeAtLeast")
                .valueMustNotExceed(130, "valueMustNotExceed");

        valid(customer.getReferralCode(), note)
                .canBeNull()
                .mustStartWith("REF", "mustStartWith")
                .mustContain("-", "mustContain")
                .lengthMustBeWithin(10, 20, "lengthMustBeWithin");

        valid(customer.getSsn(), note)
                .mustNotBeNullWhen(customer.getAge() > 18, "mustNotBeNull")
                .lengthMustNotExceed(10, "lengthMustNotExceed");


        print(note);
    }


    @Test(expected = ValidationFailureException.class)
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
    public void testCustomClass() throws Exception {
        Instant instant = Instant.now().plus(5, ChronoUnit.DAYS);

        List<String> note = new ArrayList<>();

        valid(instant, note)
                .mustNotBeNull("mustNotBeNull")
                .must(i -> i.isAfter(Instant.now()), "customMustCondition")
                .must(i -> i.isBefore(Instant.now().plus(3, ChronoUnit.DAYS)), "AAA");

        print(note);
    }

    @Test
    public void testWhenWithString() throws Exception {
        String value = "salu";

        Notification note = new Notification();

        valid(value, "field", note)
                .mustNotBeNull("mustNotBeNull")
                .when(true, Then.must(s -> s.startsWith("v"), "must start with"))
                .must(s1 -> s1.length() > 5, "customMustCondition");

        print(note);

    }

    @Test(expected = ValidationFailureException.class)
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

        valid(value, note)
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
