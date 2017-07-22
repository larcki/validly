package com.validly;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.validly.validator.FieldValidator.field;

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

        if (!notifications.isEmpty()) {
            notifications.forEach((field, reason) -> System.out.println(field + ": " + reason));
        }

        //TODO: makes sense to rename to isRequired etc.
        // examples about the result and that custom messages can be provided, e.g
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
}
