package tests.suite1;

import tests.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class GitHubSignUpEmailNegativeTest extends BaseTest {

    @Test
    public void testGitHubSignUpEmailNegative() {
        driver.get("https://github.com/");

        // Find and click "Sign up" button
        WebElement signUpButton = driver.findElement(By.xpath("//a[contains(@class, 'HeaderMenu-link')][contains(text(), 'Sign up')]"));
        signUpButton.click();

        // Wait until the welcome message appears
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'px-sm-0')]//br")));

        // Perform negative tests with invalid emails
        String[] invalidEmails = {"invalid-email", "invalid@.com", "invalid@domain", "invalid@domain."};
        for (String email : invalidEmails) {
            WebElement emailField = driver.findElement(By.id("email"));
            emailField.clear();
            emailField.sendKeys(email);
            emailField.submit();

            WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("email-err")));
            System.out.println("Error message for " + email + ": " + errorMessage.getText());
        }
    }
}
