package com.validly;

import org.junit.Test;

import java.util.Arrays;

import static com.validly.NoteFirstValidator.*;

public class CustomerScenarioTest2 {

    public static final String BLANK_NOT_ALLOWED = "Can't be null or empty value";
    public static final String TOO_LONG_20 = "Can't be longer than 20";
    public static final String NULL_NOT_ALLOWED = "Can't be null value";
    public static final String NEGATIVE_NOT_ALLOWED = "Can't be negative value";
    public static final String REQUIRED_FOR_ADULTS = "Required for adults";
    public static final String INVALID_FORMAT = "Invalid format";


    public Notification validate(Customer customer) {
        Notification note = new Notification();

        valid(customer.getName(), "name", note)
                .mustNotBeBlank("Can't be blank")
                .lengthMustNotExceed(20, "Too long");

        valid(customer.getAge(), "age", note)
                .mustNotBeNull("Can't be null")
                .valueMustBeAtLeast(0, "Can't be negative");

        valid(customer.getSsn(), "ssn", note)
                .mustNotBeNullWhen(customer.getAge() >= 18, "Required for adults")
                .must(this::validSsn, "Invalid format");

        return note;
    }

    private boolean validSsn(String s) {
        return s.matches("//your.regex+");
    }

    @Test
    public void testBasicCustomerScenario() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setTitle("Mr");
        customer.setAge(17);
        customer.setSsn("123-ABC");
        customer.setReferralCode("REF-11102022");
        customer.setPhoneNumbers(Arrays.asList("074011112223", "02045110000"));

        Notification note = new Notification();

        valid(customer.getName(), "firstName", note)
                .mustNotBeBlank(BLANK_NOT_ALLOWED)
                .lengthMustNotExceed(20, TOO_LONG_20);

        valid(customer.getLastName(), "lastName", note)
                .mustNotBeBlank(BLANK_NOT_ALLOWED)
                .lengthMustNotExceed(20, TOO_LONG_20);

        valid(customer.getAge(), "age", note)
                .mustNotBeNull(NULL_NOT_ALLOWED)
                .valueMustBeAtLeast(0, NEGATIVE_NOT_ALLOWED);

        valid(customer.getSsn(), "ssn", note)
                .mustNotBeNullWhen(customer.getAge() >= 18, REQUIRED_FOR_ADULTS)
                .must(s -> s.matches("//your.regex+"), INVALID_FORMAT);


    }
}
