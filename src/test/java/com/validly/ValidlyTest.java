package com.validly;


import com.validly.validator.ValidlyNote;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.validly.validator.FieldValidator.field;
import static com.validly.validator.ValidationPredicate.maxValue;
import static com.validly.validator.ValidationPredicate.notEmpty;
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
                .when(notNull())
                    .thenCheck(notEmpty("CANT_BE_EMPTY"));

        field("lastName", customer.getLastName(), note)
                .checkNotNull("CANT_BE_NULL")
                .checkMaxLength(5, "TOO_LONG");

        field("age", customer.getAge(), note)
                .checkNotNull("CANT_BE_NULL")
                .when(customer.getFirstName().isEmpty())
                    .thenCheck(maxValue(10, "TOO_BIG_NUMBER"));

        assertEquals("CANT_BE_EMPTY", note.messages.get("firstName"));
        assertEquals("TOO_LONG", note.messages.get("lastName"));
        assertEquals("TOO_BIG_NUMBER", note.messages.get("age"));

    }

    private Predicate<String> notNull() {
        return s -> s != null;
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