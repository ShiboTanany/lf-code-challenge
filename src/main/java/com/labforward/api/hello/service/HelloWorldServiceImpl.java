package com.labforward.api.hello.service;

import com.labforward.api.core.enums.Greetings;
import com.labforward.api.core.enums.Messages;
import com.labforward.api.core.validation.EntityValidator;
import com.labforward.api.hello.domain.Greeting;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Shaaban Ebrahim
 */
@Service
public class HelloWorldServiceImpl implements HelloWorldService {

    private final Map<String, Greeting> greetings;

    private final EntityValidator entityValidator;

    public HelloWorldServiceImpl(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;

        this.greetings = new HashMap<>(1);
        save(getDefault());
    }

    private static Greeting getDefault() {
        return new Greeting(Greetings.DEFAULT_ID.getGreeting(), Messages.DEFAULT_MESSAGE.getMessage());
    }

    @Override
    public Greeting createGreeting(Greeting request) {
        entityValidator.validateCreate(request);

        request.setId(UUID.randomUUID().toString());
        return save(request);
    }

    @Override
    public Optional<Greeting> getGreeting(String id) {
        Greeting greeting = greetings.get(id);
        if (greeting == null) {
            return Optional.empty();
        }
        return Optional.of(greeting);
    }

    @Override
    public Optional<Greeting> getDefaultGreeting() {
        return getGreeting(Greetings.DEFAULT_ID.getGreeting());
    }

    @Override
    public Optional<Greeting> updateGreeting(Greeting request) {
        entityValidator.validateUpdate(request);
        if (isGreetingExists(request)) {
            save(request);
            return getGreeting(request.getId());
        }
        return Optional.empty();
    }

    private boolean isGreetingExists(Greeting request) {
        return this.greetings.containsKey(request.getId());
    }

    private Greeting save(Greeting greeting) {
        this.greetings.put(greeting.getId(), greeting);
        return greeting;
    }

}
