package com.NguyenHoangPhiTuyet.utils;

import org.openqa.selenium.JavascriptExecutor;

public final class SessionUtils {

    private SessionUtils() {}

    public static void clearSession() {
        if (DriverManager.getDriver() == null) return;

        
        DriverManager.getDriver().manage().deleteAllCookies();

        
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");
    }
}
