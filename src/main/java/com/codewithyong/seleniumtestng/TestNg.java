package com.codewithyong.seleniumtestng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNg {
    public String baseurl="http://www.demo.guru99.com/V4/";
    public WebDriver driver;
    String driverpath="/home/chali/Downloads/geckodriver";
    @BeforeTest
    public void LaunchBrowser()
    {
        driver=new FirefoxDriver();
        driver.get(baseurl);
    }
    @BeforeMethod
    public void loginHomepage()
    {
        driver.findElement(By.name("uid")).sendKeys("****");
        driver.findElement(By.name("password")).sendKeys("****");
        driver.findElement(By.name("btnLogin")).click();
    }
    @Test
    public void verifyHomepage()
    {
        String ActualURL=driver.getCurrentUrl();
        String ExpectedURL="http://www.demo.guru99.com/V4/manager/Managerhomepage.php";
        Assert.assertEquals(ActualURL,ExpectedURL);
    }

    @AfterTest
    public void closeBrowser()
    {
        driver.close();
    }
}
