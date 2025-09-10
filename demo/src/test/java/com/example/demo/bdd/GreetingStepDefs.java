package com.example.demo.bdd;

import com.example.demo.service.GreetingService;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class GreetingStepDefs {

    private GreetingService greetingService;
    private String message;

    @Given("I have a greeting service")
    public void i_have_a_greeting_service() {
        greetingService = new GreetingService();
    }

    @When("I greet {string}")
    public void i_greet(String name) {
        message = greetingService.greet(name);
    }

    @Then("the message should be {string}")
    public void the_message_should_be(String expected) {
        assertEquals(expected, message);
    }
}
