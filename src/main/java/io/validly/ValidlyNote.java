package io.validly;

import java.util.List;
import java.util.Map;

/**
 * Interface fof a Notification object which is used to collect information about the errors during the validation.
 * <p>
 * The idea of the owner-message structure is that the owner can have multiple messages associated to it.
 * This allows grouping the messages (validation errors) per owner (value field) when notification object is shared
 * between several validations (e.g. validating a domain object).
 */
public interface ValidlyNote {

    /**
     * Adds the message with an owner into this notification.
     *
     * @param owner   owner of the message
     * @param message message
     */
    void addMessage(String owner, String message);

    /**
     * @return the list of messages associated with the owner.
     */
    Map<String, List<String>> getMessages();

}
