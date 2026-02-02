package com.NguyenHoangPhiTuyet.pages;

import com.NguyenHoangPhiTuyet.utils.DriverManager;
import com.NguyenHoangPhiTuyet.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {

    protected WebDriver driver() {
        return DriverManager.getDriver();
    }

    protected void click(By locator) {
        WaitUtils.waitClickable(driver(), locator).click();
    }

    protected void type(By locator, String text) {
        WaitUtils.waitVisible(driver(), locator).clear();
        WaitUtils.waitVisible(driver(), locator).sendKeys(text);
    }

    protected String getText(By locator) {
        return WaitUtils.waitVisible(driver(), locator).getText();
    }

    protected boolean urlContains(String text) {
        return WaitUtils.waitUrlContains(driver(), text);
    }

    protected WebElement find(By locator) {
        return driver().findElement(locator);
    }

    protected void clearAndType(By locator, String text) {
        WaitUtils.waitVisible(driver(), locator);

        WebElement el = driver().findElement(locator);
        el.click();

        // Xóa chắc chắn cho MUI: Ctrl+A rồi Delete
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        el.sendKeys(Keys.DELETE);

        // Một số input vẫn giữ value -> clear thêm lần nữa
        el.clear();

        el.sendKeys(text);
    }
}
