package com.validly.validator;

import java.util.HashMap;
import java.util.Map;

public class HashMapValidlyNote implements ValidlyNote {

    Map<String, String> validationNotes = new HashMap<>();

    @Override
    public void add(String fieldName, String message) {
        validationNotes.put(fieldName, message);
    }

    public Map<String, String> getValidationNotes() {
        return validationNotes;
    }
}
