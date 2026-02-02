package com.NguyenHoangPhiTuyet.utils;

import com.NguyenHoangPhiTuyet.constants.FrameworkConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {

    private WaitUtils() {}

    private static WebDriverWait waitDriver(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.EXPLICIT_WAIT));
    }

    public static WebElement waitClickable(WebDriver driver, By locator) {
        return waitDriver(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitUrlContains(WebDriver driver, String text) {
        return waitDriver(driver).until(ExpectedConditions.urlContains(text));
    }

    public static WebElement waitVisible(WebDriver driver, By locator) {
        return waitDriver(driver)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitVisible(By locator) {
        return waitVisible(DriverManager.getDriver(), locator);
    }


}
