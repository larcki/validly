package com.validly;

import com.validly.validator.HashMapValidlyNote;
import com.validly.validator.ValidlyNote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static com.validly.validator.FieldValidator.*;

@RunWith(Parameterized.class)
public class Test2 {


    @Parameterized.Parameter
    public String fieldValue;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {null, "required failed"},
                {"", "must not be blank failed"},
                {"     ", "must not be blank failed"},
                {"some text", false},
                {"a"},
                {"more than twenty characters"},
        });
    }

    @Test
    public void createCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName(fieldValue);
        ValidlyNote note = new HashMapValidlyNote();

        field("firstName", customer.getFirstName(), note)
                .required("required failed")
                .mustNotBeBlank("must not be blank failed")
                .lengthMustBeAtLeast(2, "length must be at least failed")
                .lengthMustNotExceed(20, "length must not exceed failed");

//        field("lastName", customer.getLastName(), note)
//                .requiredWhen(customer.getAge() >= 18, "Is required for adults")
//                .mustNotBeBlank("Can not be empty")
//                .when(s -> s.startsWith("Ö"))
//                .then()
//                .lengthMustBeWithin(1, 50, "Must be 1 to 50 characters long");

//        field("age", customer.getAge(), note)
//                .req


    }
}
