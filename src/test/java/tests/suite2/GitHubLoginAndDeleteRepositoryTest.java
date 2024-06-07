package tests.suite2;

import tests.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class GitHubLoginAndDeleteRepositoryTest extends BaseTest {

    @Test
    public void testDeleteRepository() {
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
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'AppHeader-context-item')]")));

        // Find and click on the existing repository "Test1"
        WebElement repoLink = driver.findElement(By.xpath("//a[@href='/QAutoMiro/TestForDelete']"));
        repoLink.click();

        // Find and click "Settings"
        WebElement settingsLink = driver.findElement(By.xpath("//span[text()='Settings']/parent::a"));
        settingsLink.click();

        // Scroll to the "Danger Zone" section and find the "Delete this repository" button
        WebElement deleteButton = driver.findElement(By.xpath("//button/span/span[contains(text(), 'Delete this repository')]"));
        deleteButton.click();

        // Wait for the confirmation modal to appear and click "I want to delete this repository"
        WebElement confirmDeleteButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/span/span[contains(text(), 'I want to delete this repository')]")));
        confirmDeleteButton.click();

        // Click "I have read and understand these effects" button
        WebElement readUnderstandButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button/span/span[contains(text(), 'I have read and understand these effects')]")));
        readUnderstandButton.click();

        // Enter repository name to confirm deletion
        WebElement confirmField = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("verification_field")));
        confirmField.sendKeys("QAutoMiro/Test1");

        // Click "Delete this repository" button
        WebElement finalDeleteButton = driver.findElement(By.id("repo-delete-proceed-button"));
        finalDeleteButton.click();
    }
}