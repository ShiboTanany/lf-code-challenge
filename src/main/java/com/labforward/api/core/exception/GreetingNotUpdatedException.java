package com.labforward.api.core.exception;

/**
 * @author Shaaban Ebrahim
 */
public class GreetingNotUpdatedException extends RuntimeException {

    private static final String MESSAGE = "Greeting Not updated";

    public GreetingNotUpdatedException() {
        super(MESSAGE);
    }

    public GreetingNotUpdatedException(String message) {
        super(message);
    }
}
