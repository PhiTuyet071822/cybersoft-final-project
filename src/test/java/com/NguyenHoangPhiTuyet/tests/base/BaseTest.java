package com.NguyenHoangPhiTuyet.tests.base;

import com.NguyenHoangPhiTuyet.tests.listeners.TestListener;
import com.NguyenHoangPhiTuyet.utils.DriverFactory;
import com.NguyenHoangPhiTuyet.utils.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {

    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver();
        driver().manage().deleteAllCookies();
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    protected org.openqa.selenium.WebDriver driver() {
        return DriverManager.getDriver();
    }
}
