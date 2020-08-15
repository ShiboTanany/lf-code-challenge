package com.labforward.api.hello.service;

import com.labforward.api.core.enums.Greetings;
import com.labforward.api.core.enums.Messages;
import com.labforward.api.core.exception.EntityValidationException;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.service.HelloWorldService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldServiceTest {

	@Autowired
	private HelloWorldService helloService;

	public HelloWorldServiceTest() {
	}

	@Test
	public void getDefaultGreetingIsOK() {
		Optional<Greeting> greeting = helloService.getDefaultGreeting();
		Assert.assertTrue(greeting.isPresent());
		Assert.assertEquals(Greetings.DEFAULT_ID.getGreeting(), greeting.get().getId());
		Assert.assertEquals(Messages.DEFAULT_MESSAGE.getMessage(), greeting.get().getMessage());
	}

	@Test(expected = EntityValidationException.class)
	public void createGreetingWithEmptyMessageThrowsException() {
		final String EMPTY_MESSAGE = "";
		helloService.createGreeting(new Greeting(EMPTY_MESSAGE));
	}

	@Test(expected = EntityValidationException.class)
	public void createGreetingWithNullMessageThrowsException() {
		helloService.createGreeting(new Greeting(null));
	}

	@Test
	public void createGreetingOKWhenValidRequest() {
		final String HELLO_LUKE = "Hello Luke";
		Greeting request = new Greeting(HELLO_LUKE);

		Greeting created = helloService.createGreeting(request);
		Assert.assertEquals(HELLO_LUKE, created.getMessage());
	}

	@Test
	public void updateGreetingWhenItIsExists() {
		final String HELLO_LUKE = "Hello Luke";
		Greeting request = new Greeting(HELLO_LUKE);

		Greeting created = helloService.createGreeting(request);
		Assert.assertEquals(HELLO_LUKE, created.getMessage());
		created.setMessage(HELLO_LUKE+HELLO_LUKE);
		Optional<Greeting> edited = helloService.updateGreeting(created);
		Assert.assertTrue(edited.isPresent());
		Assert.assertEquals(HELLO_LUKE+HELLO_LUKE, created.getMessage());

	}

	@Test
	public void updateGreetingWhenItIsNotExists() {
		final String HELLO_LUKE = "Hello Luke";
		Greeting request = new Greeting(HELLO_LUKE);
		Greeting created = helloService.createGreeting(request);
		created.setId("error-id");
		Optional<Greeting> notEditedObject = helloService.updateGreeting(created);
		Assert.assertFalse(notEditedObject.isPresent());
	}

	@Test(expected = EntityValidationException.class)
	public void updateGreetingWhenMessageIsNull() {
		Greeting request = new Greeting();
		helloService.updateGreeting(request);
	}

	@Test
	public void deleteGreetingWhenItIsNotExists() {
		final String HELLO_LUKE = "Hello Luke";
		Greeting request = new Greeting(HELLO_LUKE);
		Greeting created = helloService.createGreeting(request);
		created.setId("error-id");
		boolean isDeleted = helloService.deleteGreeting(created);
		Assert.assertFalse(isDeleted);
	}

	@Test
	public void deleteeGreetingWhenItIsExists() {
		final String HELLO_LUKE = "Hello Luke";
		Greeting request = new Greeting(HELLO_LUKE);
		Greeting created = helloService.createGreeting(request);
		boolean isDeleted = helloService.deleteGreeting(created);
		Assert.assertTrue(isDeleted);
	}

	@Test(expected = EntityValidationException.class)
	public void deleteGreetingWhenMessageIsNull() {
		Greeting request = new Greeting();
		helloService.deleteGreeting(request);
	}
}
