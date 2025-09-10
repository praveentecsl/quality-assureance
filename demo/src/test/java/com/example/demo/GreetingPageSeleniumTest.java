package com.example.demo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GreetingPageSeleniumTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Setup ChromeDriver automatically using WebDriverManager
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // REMOVE headless mode to see the browser
        // options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Start maximized for better visibility
        options.addArguments("--start-maximized");
        // Keep browser open for longer (will be closed by tearDown)
        options.setExperimentalOption("detach", true);

        driver = new ChromeDriver(options);
        // Add explicit wait with longer timeout
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("Browser opened and will stay visible");
    }

    @AfterEach
    public void tearDown() {
        // Add delay before closing browser
        try {
            System.out.println("Waiting 5 seconds before closing browser...");
            Thread.sleep(5000); // 5 second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed");
        }
    }

    // Utility method to add delays
    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ... your existing test methods ...

    @Test
    @Order(5)
    public void testMultipleGreetingsWithDelays() {
        System.out.println("Starting multiple greetings test with delays...");

        // Navigate to the application with delay
        driver.get("http://localhost:" + port);
        delay(2000); // 2 second delay

        // Test multiple names with delays between each
        String[] testNames = {"Alice", "Bob", "Charlie"};

        for (String name : testNames) {
            System.out.println("Testing with name: " + name);

            // Clear and enter name
            WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
            nameInput.clear();
            nameInput.sendKeys(name);
            delay(1000); // 1 second delay

            // Click submit button
            WebElement submitButton = driver.findElement(By.tagName("button"));
            submitButton.click();
            delay(2000); // 2 second delay after click

            // Verify greeting message
            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("p")));
            String messageText = message.getText();
            assertTrue(messageText.contains(name),
                    "Expected message to contain '" + name + "', but got: " + messageText);

            System.out.println("✓ Greeting for " + name + ": " + messageText);
            delay(1500); // 1.5 second delay between tests
        }
    }

    @Test
    @Order(6)
    public void testFormInteractionWithExplicitWaits() {
        System.out.println("Starting form interaction test with explicit waits...");

        driver.get("http://localhost:" + port);
        delay(3000); // 3 second initial delay

        // Test with explicit waits for each element
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        nameInput.sendKeys("Test User");
        delay(1000);

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("button")));
        submitButton.click();
        delay(3000); // 3 second delay after submission

        // Verify result with wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("p")));
        String messageText = message.getText();
        assertNotNull(messageText, "Message should not be null");
        assertFalse(messageText.isEmpty(), "Message should not be empty");

        System.out.println("✓ Form interaction test passed. Final message: " + messageText);

        // Additional delay to see the result
        delay(4000); // 4 second final delay
    }

    @Test
    @Order(7)
    public void testBrowserNavigationWithDelays() {
        System.out.println("Starting browser navigation test...");

        // First visit
        driver.get("http://localhost:" + port);
        delay(3000);
        System.out.println("First page load complete");

        // Interact with form
        WebElement nameInput = driver.findElement(By.name("name"));
        nameInput.sendKeys("Navigation Test");
        delay(1500);

        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();
        delay(3000);

        // Refresh page
        driver.navigate().refresh();
        delay(3000);
        System.out.println("Page refreshed");

        // Go back to form (if applicable)
        driver.navigate().back();
        delay(3000);
        System.out.println("Navigated back");

        // Go forward
        driver.navigate().forward();
        delay(3000);
        System.out.println("Navigated forward");

        System.out.println("✓ Browser navigation test completed successfully");
    }
}