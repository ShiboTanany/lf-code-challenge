package com.labforward.api.core.enums;

/**
 * @author Shaaban Ebrahim
 */
public enum Greetings {
    GREETING_NOT_FOUND("Greeting Not Found"),

    DEFAULT_ID("default");


    private final String greeting;

    Greetings(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }
}
