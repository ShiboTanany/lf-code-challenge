package com.labforward.api.core.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Greeting Not updated";

    public ResourceNotFoundException() {
        super(MESSAGE);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
