package com.validly;

import java.util.Objects;
import java.util.function.Predicate;

public class Then<T> {

    protected final String message;
    protected final Predicate<T> predicate;

    public static <T> Then<T> must(Predicate<T> predicate, String message) {
        return new Then<>(predicate, message);
    }

    public static <T> Then<T> mustNotBeNull(String message) {
        return new Then<>(Objects::nonNull, message);
    }

    protected Then(Predicate<T> predicate, String message) {
        this.predicate = predicate;
        this.message = message;
    }

    Predicate<T> getPredicate() {
        return predicate;
    }

    String getMessage() {
        return message;
    }
}
