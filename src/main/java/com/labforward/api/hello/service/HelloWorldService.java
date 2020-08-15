package com.labforward.api.hello.service;

import com.labforward.api.hello.domain.Greeting;

import java.util.Map;
import java.util.Optional;

/**
 * @author Shaaban Ebrahim
 */
public interface HelloWorldService {

    Optional<Greeting> getGreeting(String id);

    Greeting createGreeting(Greeting request);

    Optional<Greeting> getDefaultGreeting();

    Optional<Greeting> updateGreeting(Greeting request);

    Map<String, Greeting> getGreetings();

    boolean deleteGreeting(Greeting greeting);
}
