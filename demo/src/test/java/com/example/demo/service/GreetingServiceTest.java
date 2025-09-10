package com.example.demo.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GreetingServiceTest {

    private final GreetingService greetingService = new GreetingService();

    @Test
    void testGreet() {
        String result = greetingService.greet("Praveen");
        assertEquals("Hello, Praveen!", result);
    }
}