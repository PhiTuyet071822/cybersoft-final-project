package com.NguyenHoangPhiTuyet.pages;

import com.NguyenHoangPhiTuyet.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JobDetailOrderPage extends BasePage {

    private static final String BASE_URL = "https://demo4.cybersoft.edu.vn";

    
    private final By jobDetailInfo = By.cssSelector(".job-detail-info");
    private final By checkoutFooter = By.cssSelector(".check-out-footer, .check-out, .checkout, .check-out-wrapper");
    private final By submitOrderBtn = By.cssSelector(
            ".check-out-footer button.submit, " +
                    ".check-out-footer button, " +
                    ".check-out-footer .submit, " +
                    "button.submit"
    );

    
    private final By toastAny = By.cssSelector(".Toastify__toast");
    private final By toastSuccess = By.cssSelector(".Toastify__toast--success");
    private final By loginWrapper = By.cssSelector(".login-wrapper");

    private final By profileLink = By.cssSelector("a[href='/profile']");

    public JobDetailOrderPage openById(int jobId) {
        driver().get(BASE_URL + "/jobDetail/" + jobId);
        return this;
    }

    public boolean isJobDetailVisible() {
        try {
            return WaitUtils.waitVisible(driver(), jobDetailInfo).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public JobDetailOrderPage clickSubmitOrder() {

        System.out.println("=== DEBUG clickSubmitOrder ===");
        System.out.println("URL = " + driver().getCurrentUrl());
        System.out.println("Footer count = " + driver().findElements(By.cssSelector(".check-out-footer")).size());
        System.out.println("Submit count = " + driver().findElements(By.cssSelector("button.submit")).size());

        WaitUtils.waitUrlContains(driver(), "/jobDetail/");

        
        java.util.List<WebElement> buttons =
                driver().findElements(By.cssSelector(".check-out-footer button.submit"));

        if (buttons.isEmpty()) {
            throw new RuntimeException("No submit button found.");
        }

        
        for (WebElement btn : buttons) {
            if (btn.isDisplayed() && btn.isEnabled()) {
                try {
                    btn.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver())
                            .executeScript("arguments[0].click();", btn);
                }
                return this;
            }
        }

        throw new RuntimeException("Submit button found but none clickable.");
    }


    /** TC34 expectation: redirect login OR login form OR toast appears */
    public boolean waitRedirectToLoginOrToast() {
        try {
            
            if (WaitUtils.waitUrlContains(driver(), "login")) return true;
        } catch (Exception ignored) {}

        
        try {
            if (!driver().findElements(loginWrapper).isEmpty()) return true;
        } catch (Exception ignored) {}

        
        try {
            return WaitUtils.waitVisible(driver(), toastAny).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String waitAndGetToastText() {
        WebElement toast = WaitUtils.waitVisible(driver(), toastAny);
        String t = toast.getText();
        return t == null ? "" : t.trim();
    }

    public boolean isToastSuccessPresent() {
        return !driver().findElements(toastSuccess).isEmpty();
    }

    public JobDetailOrderPage waitToastDisappear() {
        try {
            WaitUtils.waitInvisible(driver(), toastAny);
        } catch (Exception ignored) {}
        return this;
    }

    public JobDetailOrderPage goToProfile() {
        WebElement link = WaitUtils.waitVisible(driver(), profileLink);
        ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", link);
        return this;
    }
}