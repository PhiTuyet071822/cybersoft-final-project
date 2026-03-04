package com.NguyenHoangPhiTuyet.pages;

import com.NguyenHoangPhiTuyet.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.cssSelector("button.btn.btn-success[type='submit']");

    
    private final By signInLink = By.cssSelector("a[href='login']");
    private final By registerNowLink = By.cssSelector("a.text_register[href='/register']");

    
    private final By errorMessages = By.cssSelector(".text-danger");

    
    private final By toastError = By.cssSelector(".Toastify__toast--error");

    
    private final By fieldErrors = By.cssSelector(".ant-form-item-explain-error, .text-danger, .invalid-feedback");

    
    private final By logoutBtn = By.xpath("//a[contains(normalize-space(),'Log out') or contains(normalize-space(),'Logout')]");

    public LoginPage open() {
        driver().get("https://demo4.cybersoft.edu.vn/login");
        return this;
    }

    public LoginPage enterEmail(String email) {
        type(emailInput, email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public LoginPage clickLogin() {
        click(loginButton);
        return this;
    }

    public boolean isSignInLinkDisplayed() {
        return !driver().findElements(signInLink).isEmpty();
    }

    public boolean isRegisterNowDisplayed() {
        return !driver().findElements(registerNowLink).isEmpty();
    }

    public boolean isErrorDisplayed() {
        return !driver().findElements(errorMessages).isEmpty();
    }

    /** Core action: chỉ nhập + click. KHÔNG wait redirect ở đây. */
    public LoginPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        return this;
    }

    /** Success flow: login xong phải rời /login */
    public boolean loginExpectSuccess(String email, String password) {
        login(email, password);
        return WaitUtils.waitUrlNotContains(driver(), "/login");
    }

    /**
     * Fail flow: login sai -> vẫn ở /login và có message lỗi (toast hoặc field error).
     * Không chờ toast cứng vì toast có thể không hiện hoặc biến mất nhanh.
     */
    public boolean loginExpectFail(String email, String password) {
        login(email, password);

        boolean stillOnLogin = WaitUtils.waitUrlContains(driver(), "/login");

        
        String toast = getToastErrorTextSafe();
        String field = getFieldErrorTextSafe();

        return stillOnLogin && (!toast.isBlank() || !field.isBlank());
    }

    /**
     * Nếu Tét còn dùng method này ở test cũ: giữ lại nhưng làm cho ổn hơn.
     * Không open() lại bên trong để tránh double navigation trong suite.
     */
    public boolean loginAndVerify(String email, String password) {
        login(email, password);
        
        driver().get("https://demo4.cybersoft.edu.vn/profile");
        return driver().getCurrentUrl().contains("/profile") && !driver().getCurrentUrl().contains("/login");
    }

    /** Lấy error dưới input (nếu có) */
    public String getAnyErrorText() {
        return driver().findElements(errorMessages)
                .stream()
                .map(e -> e.getText().trim())
                .filter(s -> !s.isEmpty())
                .reduce("", (a, b) -> a + (a.isEmpty() ? "" : " | ") + b);
    }

    /** Toast text - SAFE: không throw TimeoutException làm fail test */
    public String getToastErrorTextSafe() {
        try {
            
            if (driver().findElements(toastError).isEmpty()) return "";
            WebElement el = driver().findElement(toastError);
            String t = el.getText();
            return t == null ? "" : t.trim();
        } catch (Exception e) {
            return "";
        }
    }

    /** Field error text - SAFE */
    public String getFieldErrorTextSafe() {
        try {
            if (driver().findElements(fieldErrors).isEmpty()) return "";
            return driver().findElements(fieldErrors).stream()
                    .map(e -> e.getText().trim())
                    .filter(s -> !s.isEmpty())
                    .reduce("", (a, b) -> a + (a.isEmpty() ? "" : " | ") + b);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Nếu test TC06/TC08 đang gọi hàm này: trả về message tốt nhất có thể.
     * Ưu tiên toast, fallback field error.
     */
    public String getToastErrorText() {
        String toast = getToastErrorTextSafe();
        if (!toast.isBlank()) return toast;

        String field = getFieldErrorTextSafe();
        return field == null ? "" : field.trim();
    }

    public boolean isLogoutPresent() {
        return !driver().findElements(logoutBtn).isEmpty();
    }

    public void clickLogout() {
        
        if (!isLogoutPresent()) {
            throw new AssertionError("Logout button not found. Current URL: " + driver().getCurrentUrl());
        }
        click(logoutBtn);
    }
}