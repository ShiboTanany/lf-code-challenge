package com.labforward.api.core.enums;

/**
 * @author Shaaban Ebrahim
 */
public enum Messages {

    MESSAGE_UNRECOGNIZED_PROPERTY("Unrecognized property: "),
    MESSAGE_BAD_REQUEST("Client error: server will not process request"),
    BAD_REQUEST("Bad Request"),
    DEFAULT_MESSAGE("Hello World!");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
