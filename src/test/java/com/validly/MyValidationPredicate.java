package com.validly;

import com.validly.validator.ValidationPredicate;

import java.util.function.Predicate;

public class MyValidationPredicate extends ValidationPredicate<String> {

    public MyValidationPredicate(Predicate<String> predicate, String message) {
        super(predicate, message);
    }

    public static ValidationPredicate<String> mustStartWithS() {
        return new ValidationPredicate<>(s -> s.startsWith("S"), "mustStartWithS");
    }

}
