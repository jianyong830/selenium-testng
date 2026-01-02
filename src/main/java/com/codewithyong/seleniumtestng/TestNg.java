package com.codewithyong.seleniumtestng;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TestNg {

    public WebDriver driver;

    @BeforeTest
    public void LaunchBrowser()
    {

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080"); // make sure element is visible
        options.addArguments("--disable-gpu");

        driver = new ChromeDriver(options);
        // maximized the browser window
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");
        System.out.println("Title is: " + driver.getTitle());
    }

    @BeforeMethod
    public void loginHomepage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(
                ExpectedConditions.elementToBeClickable(By.id("userEmail"))
        );
        driver.findElement(By.id("userEmail")).sendKeys("rahulshetty@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Iamking@000");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);

        // Wait a bit for overlay/animations
        Thread.sleep(500);

        try {
            button.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    @Test
    public void verifyHomepage()
    {
        // Explicit wait for logo
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".logo"))
        );

        // Assertion
        Assert.assertTrue(logo.isDisplayed(), "Logo is not displayed after login");
    }

    @AfterTest
    public void closeBrowser()
    {
        driver.close();
    }
}
