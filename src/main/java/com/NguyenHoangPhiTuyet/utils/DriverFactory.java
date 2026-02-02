package com.NguyenHoangPhiTuyet.utils;

import com.NguyenHoangPhiTuyet.constants.FrameworkConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public final class DriverFactory {

    private DriverFactory() {}

    public static void initDriver() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(FrameworkConstants.IMPLICIT_WAIT));

        DriverManager.setDriver(driver);
    }

    public static void quitDriver() {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            driver.quit();
            DriverManager.unload();
        }
    }
}
