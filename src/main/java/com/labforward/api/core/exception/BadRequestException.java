package com.labforward.api.core.exception;

public class BadRequestException extends RuntimeException {

    private static final String MESSAGE = "A required parameter is missing";

    public BadRequestException() {
        super(MESSAGE);
    }

    public BadRequestException(String message) {
        super(message);
    }
}
