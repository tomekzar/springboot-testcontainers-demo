package com.cogrammer.testcontainers.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class GreetingService {

    private final RestTemplate restTemplate;

    public GreetingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, String> greet(String name) {
        final String message = restTemplate.getForObject(name, String.class);
        return Collections.singletonMap("message", message);
    }
}
