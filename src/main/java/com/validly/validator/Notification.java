package com.validly.validator;

import java.util.*;

public class Notification {

    protected final Map<String, List<String>> fieldMessages;

    public Notification() {
        this(new HashMap<>());
    }

    protected Notification(Map<String, List<String>> fieldMessages) {
        this.fieldMessages = fieldMessages;
    }

    public void addMessage(String field, String message) {
        fieldMessages.putIfAbsent(field, new ArrayList<>());
        fieldMessages.get(field).add(message);
    }

    public List<String> getMessages(String field) {
        return fieldMessages.get(field);
    }

    public Map<String, List<String>> getMessages() {
        return fieldMessages;
    }

    public boolean isNotEmpty() {
        return !fieldMessages.isEmpty();
    }

}
