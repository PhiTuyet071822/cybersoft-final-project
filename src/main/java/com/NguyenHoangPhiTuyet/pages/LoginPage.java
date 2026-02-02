package com.NguyenHoangPhiTuyet.pages;

import org.openqa.selenium.By;

import static com.NguyenHoangPhiTuyet.utils.WaitUtils.waitVisible;

public class LoginPage extends BasePage {

    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.cssSelector("button.btn.btn-success[type='submit']");

    // Link điều hướng (theo ảnh)
    private final By signInLink = By.cssSelector("a[href='login']");
    private final By registerNowLink = By.cssSelector("a.text_register[href='/register']");

    // message error đỏ dưới input
    private final By errorMessages = By.cssSelector(".text-danger");

    // Toast error (Login fail)
    private final By toastError =
            By.cssSelector(".Toastify__toast--error");

    private final By fieldErrors = By.cssSelector(".text-danger");

    private final By logoutBtn = By.xpath("//a[contains(text(),'Log out')]");


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

    public void clickLogin() {
        click(loginButton);
    }

    public boolean isSignInLinkDisplayed() {
        return driver().findElements(signInLink).size() > 0;
    }

    public boolean isRegisterNowDisplayed() {
        return driver().findElements(registerNowLink).size() > 0;
    }

    public boolean isErrorDisplayed() {
        return driver().findElements(errorMessages).size() > 0;
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    public boolean loginAndVerify(String email, String password) {
        open();
        enterEmail(email);
        enterPassword(password);
        clickLogin();

        // Sau login: luôn ép vào /profile để confirm session
        driver().get("https://demo4.cybersoft.edu.vn/profile");

        return driver().getCurrentUrl().contains("/profile");
    }

    public String getAnyErrorText() {
        return driver().findElements(errorMessages)
                .stream()
                .map(e -> e.getText().trim())
                .filter(s -> !s.isEmpty())
                .reduce("", (a, b) -> a + (a.isEmpty() ? "" : " | ") + b);
    }

    public String getToastErrorText() {
        waitVisible(toastError);          // wait toast xuất hiện
        return getText(toastError).trim();
    }

    public String getFieldErrorText() {
        return driver().findElements(fieldErrors)
                .stream()
                .map(e -> e.getText().trim())
                .filter(s -> !s.isEmpty())
                .reduce("", (a, b) -> a + (a.isEmpty() ? "" : " | ") + b);
    }



    public String tryGetToastErrorText(int seconds) {
        try {
            com.NguyenHoangPhiTuyet.utils.WaitUtils.waitVisible(
                    com.NguyenHoangPhiTuyet.utils.DriverManager.getDriver(),
                    toastError
            );
            return getText(toastError).trim();
        } catch (Exception e) {
            return "";
        }
    }

    public void clickLogout() {
        click(logoutBtn);
    }


}
