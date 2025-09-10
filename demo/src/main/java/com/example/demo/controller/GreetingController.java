package com.example.demo.controller;

import com.example.demo.service.GreetingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/greet")
    public String greet(@RequestParam String name, Model model) {
        String message = greetingService.greet(name);
        model.addAttribute("message", message);
        return "index";
    }
}