package com.validly.validator;

import java.util.List;
import java.util.Map;

public interface Notification {

    void addMessage(String fieldName, String message);

    Map<String, List<String>> getMessages();

}
