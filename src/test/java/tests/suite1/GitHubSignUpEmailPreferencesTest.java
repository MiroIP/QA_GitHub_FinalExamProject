package tests.suite1;

import tests.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class GitHubSignUpEmailPreferencesTest extends BaseTest {

    @Test
    public void testGitHubSignUp() {
        driver.get("https://github.com/");

        // Find and click "Sign up" button
        WebElement signUpButton = driver.findElement(By.xpath("//a[contains(@class, 'HeaderMenu-link')][contains(text(), 'Sign up')]"));
        signUpButton.click();

        // Wait until the welcome message appears
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'px-sm-0')]//br")));

        // Find the "Enter your email" field and enter a valid email
        WebElement emailField = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailField.sendKeys("valid-email@example.com");

        WebElement continueButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-continue-to=password-container]")));
        continueButton.click();

        // Wait for the "Create a password" field to be visible and enter a valid password
        WebElement passwordField = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordField.sendKeys("ValidPassword123!");

        continueButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-continue-to=username-container]")));
        continueButton.click();

        // Find the "Enter a username" field and enter a valid username
        WebElement usernameField = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
        usernameField.sendKeys("validusername987");

        continueButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-continue-to=opt-in-container]")));
        continueButton.click();

        // Wait for the "Email preferences" area to be visible
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("opt-in-container")));

        WebElement checkbox = driver.findElement(By.xpath("//input[@name='opt_in']"));
        checkbox.click(); // Click to check
        checkbox.click(); // Click to uncheck

        continueButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-continue-to=captcha-and-submit-container]")));
        continueButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("captcha-and-submit-container")));
    }
}
