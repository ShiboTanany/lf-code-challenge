package com.labforward.api.core.exception;

public class GreetingNotUpdatedException extends RuntimeException {

	public static String MESSAGE = "Greeting Not updated";

	public GreetingNotUpdatedException() {
		super(MESSAGE);
	}

	public GreetingNotUpdatedException(String message) {
		super(message);
	}
}
