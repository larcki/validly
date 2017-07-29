package io.validly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class FlatNotification implements ValidlyNote {

    private List<String> messages;

    FlatNotification(List<String> messages) {
        this.messages = messages;
        if (messages == null) {
            this.messages = new ArrayList<>();
        }
    }

    @Override
    public void addMessage(String owner, String message) {
        messages.add(message);
    }

    @Override
    public Map<String, List<String>> getMessages() {
        return null;
    }

}
