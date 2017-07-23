package com.validly.validator;

import java.util.Collections;
import java.util.List;

public class FlatNotification extends Notification {

    public static final String NAME = FlatNotification.class.getName();

    public FlatNotification(List<String> messages) {
        super(Collections.singletonMap(NAME, messages));
    }

    @Override
    public void addMessage(String field, String message) {
        fieldMessages.get(NAME).add(message);
    }

}
