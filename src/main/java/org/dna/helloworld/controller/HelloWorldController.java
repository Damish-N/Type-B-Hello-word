package org.dna.helloworld.controller;


import org.dna.helloworld.dto.GreetingResponse;
import org.dna.helloworld.service.GreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    private final GreetingService greetingService;

    public HelloWorldController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/hello-world")
    public GreetingResponse helloWorld(@RequestParam(name = "name", required = false) String name) {
        return new GreetingResponse(greetingService.greet(name));
    }
}
