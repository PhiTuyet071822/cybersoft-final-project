package com.NguyenHoangPhiTuyet.pages;

import com.NguyenHoangPhiTuyet.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class JobDetailMessagePage extends BasePage {

    private static final String BASE_URL = "https://demo4.cybersoft.edu.vn";

    
    private final By contactBtn = By.cssSelector("button.contact");

    
    private final By anyToast = By.cssSelector(".Toastify__toast");
    private final By modal = By.cssSelector(".ant-modal, .modal, [role='dialog']");
    private final By chatBox = By.cssSelector(".chat, .message, .messages, .conversation, .inbox");

    
    private final By messageInput = By.cssSelector("textarea, input[type='text']");
    private final By sendBtn = By.cssSelector("button[type='submit'], button.send, button.ant-btn-primary");

    public JobDetailMessagePage openById(int jobId) {
        driver().get(BASE_URL + "/jobDetail/" + jobId);
        return this;
    }

    public JobDetailMessagePage clickContact() {
        WebElement btn = WaitUtils.waitVisible(driver(), contactBtn);
        try {
            WaitUtils.waitClickable(driver(), btn).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", btn);
        }
        return this;
    }

    /** “Có phản hồi” = url đổi OR modal/chat/toast xuất hiện */
    public boolean waitAnyMessageUIResponse(int seconds) {
        String beforeUrl = driver().getCurrentUrl();
        WebDriverWait w = new WebDriverWait(driver(), Duration.ofSeconds(seconds));

        try {
            return w.until((ExpectedCondition<Boolean>) d -> {
                if (d == null) return false;

                
                if (!d.getCurrentUrl().equals(beforeUrl)) return true;

                
                if (!d.findElements(modal).isEmpty()) return true;

                
                if (!d.findElements(anyToast).isEmpty()) return true;

                
                if (!d.findElements(chatBox).isEmpty()) return true;

                return false;
            });
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Dùng cho TC63: check xem có UI message để reply không */
    public boolean isMessageUIOpened() {
        return !driver().findElements(modal).isEmpty()
                || !driver().findElements(chatBox).isEmpty()
                || !driver().findElements(anyToast).isEmpty();
    }

    /** Try reply (best-effort). Nếu UI không có input thì return false */
    public boolean replyMessageBestEffort(String text) {
        try {
            if (driver().findElements(messageInput).isEmpty()) return false;

            WebElement input = driver().findElements(messageInput).get(0);
            input.clear();
            input.sendKeys(text);

            if (!driver().findElements(sendBtn).isEmpty()) {
                WebElement send = driver().findElements(sendBtn).get(0);
                try {
                    send.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", send);
                }
                return true;
            }
            return true; 
        } catch (Exception e) {
            return false;
        }
    }
}