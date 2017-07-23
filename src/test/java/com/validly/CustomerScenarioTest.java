package com.validly;

import com.validly.validator.FailFastValidator;
import com.validly.validator.Notification;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                .mustStartWith("REF")
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
        String date = "12.12.2015";

        Notification note = new Notification();

        field("date", date, note)
                .mustNotBeNull()
                .mustConvert(s -> LocalDate.parse(s, ofPattern("dd.MM.yyyy")))
                .must(d -> d.isAfter(LocalDate.now()), "date must be in the future");

        print(note);
    }

    @Test
    public void testDate() throws Exception {
        LocalDate date = LocalDate.of(2017, 12, 12);

        Notification note = new Notification();

        field("", date, note)
                .mustNotBeNull()
                .must(localDate -> localDate.isAfter(LocalDate.now()));

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
        customer.setReferralCode("REF.111122223333");
        customer.setSsn("tooLongValue");

        List<String> note = new ArrayList<>();

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
                .mustStartWith("REF")
                .mustContain("-")
                .lengthMustBeWithin(10, 20);

        field("ssn", customer.getSsn(), note)
                .mustNotBeNullWhen(customer.getAge() > 18)
                .lengthMustNotExceed(10);


        print(note);
    }


    @Test
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
