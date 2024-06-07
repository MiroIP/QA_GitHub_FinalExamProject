package tests.suite2;

import tests.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.time.Duration;

public class GitHubLoginAndRenameRepositoryTest extends BaseTest {

    @Test
    public void testRenameRepository() {
        driver.get("https://github.com/");

        // Find and click "Sign in" button
        WebElement signInButton = driver.findElement(By.linkText("Sign in"));
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

        // Find and click on the existing repository "Test"
        WebElement repoLink = driver.findElement(By.xpath("//a[@href='/QAutoMiro/TestForRename']"));
        repoLink.click();

        // Find and click "Settings"
        WebElement settingsLink = driver.findElement(By.xpath("//span[text()='Settings']/parent::a"));
        settingsLink.click();

        // Find the repository name field, change the name to "Test1" and click "Rename"
        WebElement repoNameField = driver.findElement(By.id("rename-field"));
        repoNameField.clear();
        repoNameField.sendKeys("Test1");

        WebElement renameButton = driver.findElement(By.xpath("//button[contains(text(), 'Rename')]"));
        renameButton.click();
    }
}