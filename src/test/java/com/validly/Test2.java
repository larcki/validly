package com.validly;

import com.validly.validator.FieldValidator;
import com.validly.validator.HashMapValidlyNote;
import com.validly.validator.ValidlyNote;
import org.junit.Test;

import static com.validly.validator.FieldValidator.*;

public class Test2 {


    @Test
    public void createCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Person");
        customer.setAge(20);

        ValidlyNote note = new HashMapValidlyNote();


        field("firstName", customer.getFirstName(), note)
                .required("Is required")
                .mustNotBeBlank("Can not be empty")
                .lengthMustBeWithin(1, 50, "Must be 1 to 50 characters long");

        field("lastName", customer.getLastName(), note)
                .requiredWhen(customer.getAge() >= 18, "Is required for adults")
                .mustNotBeBlank("Can not be empty")
                .when(s -> s.startsWith("Ö"))
                    .then()
                .lengthMustBeWithin(1, 50, "Must be 1 to 50 characters long");

//        field("age", customer.getAge(), note)
//                .req



    }
}
