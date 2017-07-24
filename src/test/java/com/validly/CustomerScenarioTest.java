package com.validly;

import com.validly.validator.*;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.validly.NoteFirstValidator.field;
import static java.time.format.DateTimeFormatter.ofPattern;

public class CustomerScenarioTest {

    @Test
    public void basicCustomerValidation() {

        Customer customer = new Customer();
        customer.setFirstName("J");
        customer.setAge(0);
        customer.setReferralCode("REF.111122223333");
        customer.setSsn("tooLongValue");

        Notification note = new Notification();

        field("firstName", customer.getFirstName(), note)
                .mustNotBeBlank()
                .lengthMustBeAtLeast(2)
                .lengthMustNotExceed(100);

        field("age", customer.getAge(), note)
                .mustNotBeNull()
                .valueMustBeAtLeast(1)
                .valueMustNotExceed(130);

        field("referralCode", customer.getReferralCode(), note)
                .canBeNull()
                .mustStartWith("REX")
                .mustContain("-")
                .lengthMustBeWithin(10, 20);

        field("ssn", customer.getSsn(), note)
                .mustNotBeNullWhen(customer.getAge() > 18)
                .lengthMustNotExceed(10);

        print(note);

        //TODO: makes sense to rename to isRequired etc.
        // examples about the result and that custom messages can be provided, e.g
    }

    @Test
    public void testDateString() throws Exception {
        String date = "12.12.2014";

        Notification note = new Notification();

        field("date", date, note)
                .mustNotBeNull()
                .mustConvert(s -> LocalDate.parse(s, ofPattern("dd.MM.yyyy")))
                .must(d -> d.isAfter(LocalDate.now()), "date must be in the future");

        print(note);
    }

    @Test
    public void testDate() throws Exception {
        LocalDate date = LocalDate.of(2016, 12, 3);

        Notification note = new Notification();

        field("", date, note)
                .mustNotBeNull()
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

        field("postCode", address.getPostCode(), note)
                .mustNotBeNull()
                .must(s -> s.matches("//your.regex+"));

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

        field(customer.getFirstName(), note)
                .mustNotBeBlank()
                .must(s -> s.startsWith("K"), "start with fails")
                .lengthMustNotExceed(100);

        field(customer.getAge(), note)
                .mustNotBeNull()
                .valueMustBeAtLeast(1)
                .valueMustNotExceed(130);

        field(customer.getReferralCode(), note)
                .canBeNull()
                .mustStartWith("REF")
                .mustContain("-")
                .lengthMustBeWithin(10, 20);

        field(customer.getSsn(), note)
                .mustNotBeNullWhen(customer.getAge() > 18)
                .lengthMustNotExceed(10);


        print(note);
    }


    @Test(expected = ValidationFailureException.class)
    public void testFailFast() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("J");
        customer.setAge(0);
        customer.setReferralCode("REF.111122223333");
        customer.setSsn("tooLongValue");

        FailFastValidator.field(customer.getFirstName())
                .mustNotBeBlank()
                .lengthMustBeAtLeast(2)
                .lengthMustNotExceed(100);

        FailFastValidator.field(customer.getAge())
                .mustNotBeNull()
                .valueMustBeAtLeast(1)
                .valueMustNotExceed(130);

        FailFastValidator.field(customer.getReferralCode())
                .canBeNull()
                .mustStartWith("REF")
                .mustContain("-")
                .lengthMustBeWithin(10, 20);

        FailFastValidator.field(customer.getSsn())
                .mustNotBeNullWhen(customer.getAge() > 18)
                .lengthMustNotExceed(10);

    }

    @Test
    public void testCustomClass() throws Exception {
        Instant instant = Instant.now().plus(5, ChronoUnit.DAYS);

        List<String> note = new ArrayList<>();

        field(instant, note)
                .mustNotBeNull()
                .must(i -> i.isAfter(Instant.now()))
                .must(i -> i.isBefore(Instant.now().plus(3, ChronoUnit.DAYS)), "AAA");

        print(note);
    }

    @Test
    public void testWhenWithString() throws Exception {
        String value = "salu";

        Notification note = new Notification();

        field("field", value, note)
                .mustNotBeNull()
                .when(true, Then.must(s -> s.startsWith("v"), "must start with"))
                .must(s1 -> s1.length() > 5);

        print(note);

    }

    @Test(expected = ValidationFailureException.class)
    public void testWhenWithStringFailFast() throws Exception {
        String value = "valu";

        FailFastValidator.field(value)
                .mustNotBeNull()
                .when(true, Then.must(s -> s.startsWith("v"), "must start with"))
                .must(s1 -> s1.length() > 5);
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
