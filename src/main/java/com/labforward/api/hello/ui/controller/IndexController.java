package com.labforward.api.hello.ui.controller;


import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.service.HelloWorldService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class IndexController {

    private final HelloWorldService helloWorldService;

    public IndexController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @RequestMapping(value = {"/", "index"})
    public ModelAndView index() {
        ModelAndView indexModelAndView = new ModelAndView("index");
        indexModelAndView.addObject("greetings", this.helloWorldService.getGreetings());
        indexModelAndView.addObject("greeting", new Greeting());
        return indexModelAndView;
    }

    @PostMapping("/addGreeting")
    public ModelAndView addGreeting(@ModelAttribute Greeting greeting, Model model) {
        this.helloWorldService.createGreeting(greeting);
        return index();
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") String id, Model model) {
        Optional<Greeting> greeting = this.helloWorldService.getGreeting(id);
        if (!greeting.isPresent()) {
            return index();
        }
        ModelAndView modelAndView = new ModelAndView("update-greeting");
        modelAndView.addObject("greeting", greeting.get());
        return modelAndView;
    }


    @PostMapping("/update/{id}")
    public ModelAndView updateUser(@PathVariable("id") String id, Greeting greeting, BindingResult result, Model
            model) {
        if (result.hasErrors()) {
            greeting.setId(id);
            return new ModelAndView("update-greeting");
        }
        this.helloWorldService.updateGreeting(greeting);
        return index();
    }

    @GetMapping("/deleteGreeting/{id}")
    public ModelAndView deleteGreeting(@PathVariable("id") String id, Model model) {
        Optional<Greeting> greeting = this.helloWorldService.getGreeting(id);
        if (!greeting.isPresent()) {
            return index();
        }
        this.helloWorldService.deleteGreeting(greeting.get());
        return index();
    }

}
