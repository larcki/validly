package io.validly;

import io.validly.excpetion.ValidationFailureException;

import static io.validly.NoteFirstValidator.*;

public class Validator {

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

    public void validate(String input) throws ValidationFailureException {

    }

    private boolean validSsn(String s) {
        return s.matches("//your.regex+");
    }

}
