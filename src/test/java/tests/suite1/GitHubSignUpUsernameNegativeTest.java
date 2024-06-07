package tests.suite1;

import tests.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GitHubSignUpUsernameNegativeTest extends BaseTest {
    private static final String SCREENSHOTS_DIR = "screenshots/";

    @Test
    public void testGitHubSignUpUsernameNegative() {
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

        // Perform negative tests with invalid usernames
        String[] invalidUsernames = {"short", "123456", "user@name"};
        for (String username : invalidUsernames) {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));

            WebElement usernameField = driver.findElement(By.id("login"));
            usernameField.clear();
            usernameField.sendKeys(username);

            // Explicitly wait before submitting to allow the field validation to process
            new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.textToBePresentInElementValue(By.id("login"), username));

            try {
                WebElement usernameContinueButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-continue-to=verify-container]")));
                usernameContinueButton.click();
            } catch (Exception e) {
                System.out.println("Continue button not clickable for username " + username);
            }

            WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("login-err")));
            System.out.println("Error message for username " + username + ": " + errorMessage.getText());
        }
    }

    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (!result.isSuccess()) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String screenshotName = result.getName() + "_" + timestamp + ".png";
            File destFile = new File(SCREENSHOTS_DIR + screenshotName);

            try {
                FileHandler.createDir(new File(SCREENSHOTS_DIR));
                FileHandler.copy(srcFile, destFile);
                System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to save screenshot: " + e.getMessage());
            }
        }
    }
}
