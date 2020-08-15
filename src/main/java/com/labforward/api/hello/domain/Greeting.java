package com.labforward.api.hello.domain;

import com.labforward.api.core.validation.Entity;
import com.labforward.api.core.validation.EntityUpdateValidatorGroup;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * Simple greeting message for dev purposes
 */
public class Greeting implements Entity {

    @NotEmpty(groups = {EntityUpdateValidatorGroup.class})
    private String id;

    @NotEmpty
    private String message;

    public Greeting() {
        // needed for JSON deserialization
    }

    public Greeting(String id, String message) {
        this.message = message;
        this.id = id;
    }

    public Greeting(String message) {
        this.message = message;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Greeting greeting = (Greeting) o;
        return Objects.equals(id, greeting.id) &&
                Objects.equals(message, greeting.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message);
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
