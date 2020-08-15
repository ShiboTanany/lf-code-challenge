package com.labforward.api.core.enums;

public enum Delimiters {
    HASH("#");

    private final String delimiter;

    Delimiters(String delimiter) {
        this.delimiter =delimiter;
    }

    public String getDelimiter() {
        return delimiter;
    }
}
