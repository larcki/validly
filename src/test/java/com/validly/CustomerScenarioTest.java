package com.validly;

import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.validly.validator.FieldValidator.field;
import static java.time.format.DateTimeFormatter.ofPattern;

public class CustomerScenarioTest {

    @Test
    public void basicCustomerValidation() {

        Customer customer = new Customer();
        customer.setFirstName("J");
        customer.setAge(0);
        customer.setReferralCode("REF.111122223333");
        customer.setSsn("tooLongValue");

        Map<String, String> notifications = new HashMap<>();

        field("firstName", customer.getFirstName(), notifications)
                .mustNotBeBlank()
                .lengthMustBeAtLeast(2)
                .lengthMustNotExceed(100);

        field("age", customer.getAge(), notifications)
                .mustNotBeNull()
                .valueMustBeAtLeast(1)
                .valueMustNotExceed(130);

        field("referralCode", customer.getReferralCode(), notifications)
                .canBeNull()
                .mustStartWith("REF")
                .mustContain("-")
                .lengthMustBeWithin(10, 20);

        field("ssn", customer.getSsn(), notifications)
                .mustNotBeNullWhen(customer.getAge() > 18)
                .lengthMustNotExceed(10);

        print(notifications);

        //TODO: makes sense to rename to isRequired etc.
        // examples about the result and that custom messages can be provided, e.g
    }

    @Test
    public void testDateString() throws Exception {
        String date = "12.12.2015";

        HashMap<String, String> note = new HashMap<>();

        field("date", date, note)
                .mustNotBeNull()
                .mustConvert(s -> LocalDate.parse(s, ofPattern("dd.MM.yyyy")))
                .must(d -> d.isAfter(LocalDate.now()), "date must be in the future");

        print(note);
    }

    @Test
    public void testDate() throws Exception {
        LocalDate date = LocalDate.of(2017, 12, 12);

        Map<String, String> note = new HashMap<>();

        field("", date, note)
                .mustNotBeNull()
                .must(localDate -> localDate.isAfter(LocalDate.now()));

    }

    @Test
    public void customValidationAndConditions() throws Exception {
        Address address = new Address();
        address.setPostCode("");

        Map<String, String> notifications = new HashMap<>();

        field("postCode", address.getPostCode(), notifications)
                .mustNotBeNull()
                .must(s -> s.matches("//your.regex+"));

        //TODO: more cases
    }

    private void print(Map<String, String> note) {
        if (!note.isEmpty()) {
            note.forEach((field, reason) -> System.out.println(field + ": " + reason));
        }
    }

}
