package com.labforward.api.hello.controller;

import com.labforward.api.core.enums.Greetings;
import com.labforward.api.core.exception.GreetingNotUpdatedException;
import com.labforward.api.core.exception.ResourceNotFoundException;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.service.HelloWorldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Shaaban Ebrahim
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

	private final HelloWorldService helloWorldService;

	public HelloController(HelloWorldService helloWorldService) {
		this.helloWorldService = helloWorldService;
	}

	@GetMapping
	@ResponseBody
	public ResponseEntity<Greeting> helloWorld() {
		return getHello(Greetings.DEFAULT_ID.getGreeting());
	}

	@GetMapping(value = "{greetingId}")
	@ResponseBody
	public ResponseEntity<Greeting> getHello(@PathVariable String greetingId) {
		Optional<Greeting> greeting =  helloWorldService.getGreeting(greetingId);
		return greeting.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public Greeting createGreeting(@RequestBody Greeting request) {
		return helloWorldService.createGreeting(request);
	}

	@PutMapping
	public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting request) {
		Optional<Greeting> greeting = helloWorldService.updateGreeting(request);
		return greeting.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
	}
}
