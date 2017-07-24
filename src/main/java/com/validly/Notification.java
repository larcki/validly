package com.validly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notification implements ValidlyNote {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        return !(fieldMessages != null ? !fieldMessages.equals(that.fieldMessages) : that.fieldMessages != null);

    }

    @Override
    public int hashCode() {
        return fieldMessages != null ? fieldMessages.hashCode() : 0;
    }
}
