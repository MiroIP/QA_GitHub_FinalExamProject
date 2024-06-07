package tests.suite1;

import tests.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class GitHubSignUpPasswordNegativeTest extends BaseTest {

    @Test
    public void testGitHubSignUpPasswordNegative() {
        driver.get("https://github.com/");

        // Find and click "Sign up" button
        WebElement signUpButton = driver.findElement(By.xpath("//a[contains(@class, 'HeaderMenu-link')][contains(text(), 'Sign up')]"));
        signUpButton.click();

        // Wait until the welcome message appears
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'px-sm-0')]//br")));

        // Find the "Enter your email*" field and enter a valid email
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("valid-email@example.com");

        // Find the "Continue" button and click it
        WebElement continueButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-continue-to=password-container]")));
        continueButton.click();

        // Test various password scenarios
        String[] passwords = {
                "a".repeat(73), // Very long password
                "short", // Short password
                "1234567890", // Only numbers, 10 characters
                "aaaaaaaaaa", // Only letters, 10 characters
                "1".repeat(15) // Password may be compromised
        };

        String[] expectedErrors = {
                "Password is too long",
                "Password is too short",
                "Password needs a number and lowercase letter",
                "Password needs a number and lowercase letter",
                "Password may be compromised"
        };

        for (int i = 0; i < passwords.length; i++) {
            // Refresh the page to reset the form
            driver.navigate().refresh();

            // Re-enter the email
            emailField = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            emailField.sendKeys("valid-email@example.com");

            continueButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-continue-to=password-container]")));
            continueButton.click();

            // Enter the password
            WebElement passwordField = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            passwordField.sendKeys(passwords[i]);

            // Wait for and read the error message for the password
            WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("password-err")));
            System.out.println("Error message for password '" + passwords[i] + "': " + errorMessage.getText());
            assert errorMessage.getText().contains(expectedErrors[i]) : "Unexpected error message";
        }
    }
}

