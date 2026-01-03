package com.codewithyong.seleniumtestng;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SeleniumTestngApplicationTests {

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

        driver = new ChromeDriver(options);
        // maximized the browser window
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/automation-practice-form");
    }

    @Test
    public void verifyFormSubmission() throws InterruptedException {
        // Fill the submission form
        driver.findElement(By.id("firstName")).sendKeys("Automation");
        driver.findElement(By.id("lastName")).sendKeys("Test");
        driver.findElement(By.id("userEmail")).sendKeys("iloveautomation@qa.com");

        WebElement maleRadio = driver.findElement(By.xpath("//label[text()='Male']"));
        //javascript scroll to the view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", maleRadio);
        maleRadio.click();
        driver.findElement(By.id("userNumber")).sendKeys("0123456789");

        WebElement dob = driver.findElement(By.id("dateOfBirthInput"));
        dob.sendKeys(Keys.CONTROL + "a");
        dob.sendKeys("30 Oct 1993");
        dob.sendKeys(Keys.ENTER);

        WebElement subjectsContainer = driver.findElement(By.cssSelector("#subjectsContainer .subjects-auto-complete__control"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subjectsContainer);
        subjectsContainer.click();
        WebElement subjectsInput = driver.findElement(By.cssSelector("#subjectsContainer input"));
        subjectsInput.sendKeys("Maths");
        subjectsInput.sendKeys(Keys.ENTER);

        driver.findElement(By.xpath("//label[text()='Sports']")).click();
        driver.findElement(By.xpath("//label[text()='Reading']")).click();
        driver.findElement(By.xpath("//label[text()='Music']")).click();

        driver.findElement(By.id("uploadPicture"))
                .sendKeys(System.getProperty("user.dir") + "/test.png");

        driver.findElement(By.id("currentAddress"))
                .sendKeys("78, Shenton Way 000000 Singapore.");

        driver.findElement(By.id("state")).click();
        driver.findElement(By.xpath("//div[text()='Haryana']")).click();

        driver.findElement(By.id("city")).click();
        driver.findElement(By.xpath("//div[text()='Karnal']")).click();

        WebElement submitBtn = driver.findElement(By.id("submit"));
        submitBtn.click();

        // ===== VERIFICATION =====
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example-modal-sizes-title-lg")));


        Assert.assertEquals(driver.findElement(By.cssSelector("tbody tr:nth-child(1) td:nth-child(2)")).getText(), "Automation Test");
        Assert.assertEquals(driver.findElement(By.cssSelector("tbody tr:nth-child(2) td:nth-child(2)")).getText(), "iloveautomation@qa.com");
        Assert.assertEquals(driver.findElement(By.cssSelector("tbody tr:nth-child(3) td:nth-child(2)")).getText(), "Male");
        Assert.assertEquals(driver.findElement(By.cssSelector("tbody tr:nth-child(4) td:nth-child(2)")).getText(), "0123456789");
        Assert.assertEquals(driver.findElement(By.cssSelector("tbody tr:nth-child(5) td:nth-child(2)")).getText(), "30 October,1993");
        Assert.assertEquals(driver.findElement(By.cssSelector("tbody tr:nth-child(6) td:nth-child(2)")).getText(), "Maths");
        Assert.assertEquals(driver.findElement(By.cssSelector("tbody tr:nth-child(7) td:nth-child(2)")).getText(), "Sports, Reading, Music");
        Assert.assertEquals(driver.findElement(By.cssSelector("tbody tr:nth-child(8) td:nth-child(2)")).getText(), "test.png");
        Assert.assertEquals(driver.findElement(By.cssSelector("tbody tr:nth-child(9) td:nth-child(2)")).getText(), "78, Shenton Way 000000 Singapore.");
        Assert.assertEquals(driver.findElement(By.cssSelector("tbody tr:nth-child(10) td:nth-child(2)")).getText(), "Haryana Karnal");

    }

    @AfterTest
    public void closeBrowser()
    {
        driver.close();
    }

}
