package com.NguyenHoangPhiTuyet.utils;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

    private DriverManager() {}

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    static void unload() {
        DRIVER.remove();
    }
}
