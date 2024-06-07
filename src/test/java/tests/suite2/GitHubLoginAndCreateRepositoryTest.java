package tests.suite2;

import tests.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.time.Duration;

public class GitHubLoginAndCreateRepositoryTest extends BaseTest {

    @Test
    public void testCreateRepository() {
        driver.get("https://github.com/");

        // Find and click "Sign in" button
        WebElement signInButton = driver.findElement(By.xpath("//*[contains(@class, 'HeaderMenu-link--sign-in')]"));
        signInButton.click();

        // Enter username and password
        WebElement usernameField = driver.findElement(By.id("login_field"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.name("commit"));

        usernameField.sendKeys("QAutoMiro");
        passwordField.sendKeys("QAuto123");
        loginButton.click();

        // Wait for the page to load and ensure login is successful by checking for the profile icon
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Find and click "New" button to create a new repository
        WebElement newRepoButton = driver.findElement(By.xpath("//*[contains(@class, 'Button--primary') and @href='/new']"));
        newRepoButton.click();

        // Enter repository name
        WebElement repoNameField = driver.findElement(By.xpath("//input[contains(@id, ':r4:')]"));
        repoNameField.sendKeys("Test");

        // Click the "Create repository" button
        WebElement createRepoButton = driver.findElement(By.xpath("//button[contains(@type, 'submit')]/span"));
        createRepoButton.click();
    }
}
