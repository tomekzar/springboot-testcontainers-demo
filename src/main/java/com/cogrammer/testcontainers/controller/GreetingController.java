package com.cogrammer.testcontainers.controller;

import com.cogrammer.testcontainers.service.GreetingService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/greetings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@SuppressWarnings("unused")
class GreetingController {

    private final GreetingService service;

    GreetingController(GreetingService service) {
        this.service = service;
    }

    @GetMapping
    Map<String, String> greet(@RequestParam("name") String name) {
        return service.greet(name);
    }
}
