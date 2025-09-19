package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    public String greet(String name) {
        if (name == null || name.isBlank()) {
            return "Hello, Guest!";
        }
        return String.format("Hello, %s!", name);
    }
}