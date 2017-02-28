package com.validly;


import com.validly.validator.ValidlyNote;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.validly.validator.FieldValidator.field;
import static com.validly.validator.ValidationPredicate.maxValue;
import static com.validly.validator.ValidationPredicate.mustNotBeEmpty;
import static org.junit.Assert.assertEquals;

public class ValidlyTest {

    @Test
    public void testOnGoingCondition() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("");
        customer.setLastName("thisIsTooLongValue");
        customer.setAge(100000);
        MyNote note = new MyNote();

        field("firstName", customer.getFirstName(), note)
                .canBeNull()
                .when(true)
                    .then(mustNotBeEmpty("CANT_BE_EMPTY"));

        field("lastName", customer.getLastName(), note)
                .required("CANT_BE_NULL")
                .lengthMustNotExceed(5, "TOO_LONG")
                .lengthMustBeAtLeast(2, "TOO_LONG")
                .lengthMustBeWithin(2, 10, "TOO_LONG");

//        field("age", customer.getAge(), note)
//                .isRequired("CANT_BE_NULL")
//                .when(customer.getFirstName().isEmpty())
//                .then(maxValue(10, "TOO_BIG_NUMBER"));

        assertEquals("CANT_BE_EMPTY", note.messages.get("firstName"));
        assertEquals("TOO_LONG", note.messages.get("lastName"));
        assertEquals("TOO_BIG_NUMBER", note.messages.get("age"));

    }

    @Test
    public void testWhenNotNull() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("");
        customer.setLastName("thisIsTooLongValue");
        customer.setAge(100000);
        MyNote note = new MyNote();

//        field("firstName", customer.getFirstName(), note)
//                .whenNotNull()
//                .then(mustNotBeEmpty("CANT_BE_EMPTY"));

        field("firstName", customer.getFirstName(), note)
                .required("Can not be null")
                .mustNotBeBlank("can not be null");

        field("firstName", customer.getFirstName(), note)
                .requiredWhen(true, "can not be null")
                .mustNotBeBlank("can not be null");

        field("firstName", customer.getFirstName(), note)
                .canBeNull()
                .mustNotBeBlank("can not be null")
                .lengthMustNotExceed(10, "is too long");

        field("firstName", customer.getFirstName(), note)
                .canBeNull()
                .must(contain("s"), "Must contain letter S")
                .when(contain("a"))
                    .then(mustNotBeEmpty(""))
                .lengthMustBeWithin(10, 100, "Not within range");

        field("firstName", customer.getFirstName(), note)
                .required("Is required")
                .must(s -> s.contains("s"), "Must contain letter S");


        field("firstName", customer.getFirstName(), note)
                .required("Is required")
                .when(customer.getAge() > 18)
                    .then(mustNotBeEmpty("can not be empty if S is defined"));

        field("firstName", customer.getFirstName(), note)
                .requiredWhen(customer.getAge() > 18, "Name needed for adults")
                .lengthMustBeAtLeast(1, "is too short")
                .lengthMustNotExceed(20, "is too long");


//        field("firstName", customer.getFirstName(), note)
//                .required("Can not be null")
//                .mustNotBeBlank("Can not be blank value")
//                .shouldNotContain()
//                .lengthShouldBeWithin(


        assertEquals("CANT_BE_EMPTY", note.messages.get("firstName"));

    }

    private Predicate<String> contain(String letter) {
        return s -> s.contains(letter);
    }

    class MyNote implements ValidlyNote {

        Map<String, String> messages = new HashMap<>();

        @Override
        public void add(String fieldName, String message) {
            messages.put(fieldName, message);
        }


    }


    class Customer {

        private String firstName;
        private String lastName;
        private Integer age;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

}