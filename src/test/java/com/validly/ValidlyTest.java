package com.validly;


import com.validly.validator.ValidationErrorException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.validly.validator.FieldValidator.field;
import static com.validly.validator.ValidationPredicate.mustNotBeEmpty;
import static org.junit.Assert.assertEquals;

public class ValidlyTest {

    @Test(expected = ValidationErrorException.class)
    public void testNoNotification() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName(null);
        customer.setLastName("thisIsTooLongValue");
        customer.setAge(100000);
        customer.setSsn("21390rjeiwf");

        try {

            field(customer.getFirstName())
                    .validateWhenNotNull()
                    .mustNotBeEmpty()
                    .lengthMustNotExceed(5)
                    .lengthMustBeAtLeast(2)
                    .lengthMustBeWithin(2, 10);

            field(customer.getLastName())
                    .mustNotBeNull()
                    .mustNotBeBlank()
                    .lengthMustNotExceed(5)
                    .lengthMustBeAtLeast(2)
                    .lengthMustBeWithin(2, 10);

            field(customer.getSsn())
                    .validateWhen(customer.getAge() >= 18)
                    .mustNotBeNull()
                    .mustNotBeBlank()
                    .must(s -> !s.startsWith("123123"));

            field(customer.getSsn())
                    .validateWhen(customer.getAge() >= 18)
                    .mustNotBeNull()
                    .mustNotBeBlank()
                    .must(s -> !s.startsWith("123123"));


            if (customer.getAge() > 18) {
                String ssn = customer.getSsn();
                if (ssn == null || ssn.trim().equals("") || !ssn.startsWith("123123")) {
                    throw new IllegalArgumentException();
                }
            }



        } catch (ValidationErrorException e) {
            System.out.println("Invalid input, " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testOnGoingCondition() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("");
        customer.setLastName("thisIsTooLongValue");
        customer.setAge(100000);
        Map note = new HashMap<>();

        field("firstName", customer.getFirstName(), note)
                .validateWhenNotNull()
                .when(true)
                    .then(mustNotBeEmpty("CANT_BE_EMPTY"));

        field("lastName", customer.getLastName(), note)
                .mustNotBeNull()
                .mustNotBeBlank()
                .lengthMustNotExceed(5)
                .lengthMustBeAtLeast(2)
                .lengthMustBeWithin(2, 10);


        field("lastName", customer.getLastName(), note)
                .mustNotBeNull()
                .mustNotBeBlank()
                .lengthMustNotExceed(5)
                .lengthMustBeAtLeast(2)
                .lengthMustBeWithin(2, 10);

//        field("age", customer.getAge(), note)
//                .isRequired("CANT_BE_NULL")
//                .when(customer.getFirstName().isEmpty())
//                .then(maxValue(10, "TOO_BIG_NUMBER"));

//        assertEquals("CANT_BE_EMPTY", note.get("firstName"));
//        assertEquals("TOO_LONG", note.get("lastName"));
//        assertEquals("TOO_BIG_NUMBER", note.get("age"));

        note.forEach((o, o2) -> {
            System.out.println(o + " : " + o2);

        });


    }

    @Test
    public void testWhenNotNull() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("");
        customer.setLastName("thisIsTooLongValue");
        customer.setAge(100000);
        Map note = new HashMap<>();

//        field("firstName", customer.getFirstName(), note)
//                .validateWhenNotNull()
//                .then(mustNotBeEmpty("CANT_BE_EMPTY"));

        field("firstName", customer.getFirstName(), note)
                .mustNotBeNull()
                .mustNotBeBlank();

        field("firstName", customer.getFirstName(), note)
                .mustNotBeNullWhen(true)
                .mustNotBeBlank();

        field("firstName", customer.getFirstName(), note)
                .validateWhenNotNull()
                .must(contain("s"), "CustomMessage")
                .mustNotBeBlank()
                .lengthMustNotExceed(10);

        field("firstName", customer.getFirstName(), note)
                .validateWhenNotNull()
                .must(contain("s"), "Must contain letter S")
                .when(contain("a"))
                    .then(mustNotBeEmpty(""))
                .lengthMustBeWithin(10, 100);

        field("firstName", customer.getFirstName(), note)
                .mustNotBeNull()
                .must(s -> s.contains("s"), "Must contain letter S");


        field("firstName", customer.getFirstName(), note)
                .mustNotBeNull()
                .when(customer.getAge() > 18)
                    .then(mustNotBeEmpty("can not be empty if S is defined"));

        field("firstName", customer.getFirstName(), note)
                .mustNotBeNullWhen(customer.getAge() > 18)
                .lengthMustBeAtLeast(1)
                .lengthMustNotExceed(20);


//        field("firstName", customer.getFirstName(), note)
//                .mustNotBeNull("Can not be null")
//                .mustNotBeBlank("Can not be blank value")
//                .shouldNotContain()
//                .lengthShouldBeWithin(


        assertEquals("CANT_BE_EMPTY", note.get("firstName"));

    }

    private Predicate<String> contain(String letter) {
        return s -> s.contains(letter);
    }

}