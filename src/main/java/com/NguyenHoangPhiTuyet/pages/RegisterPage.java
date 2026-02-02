package com.NguyenHoangPhiTuyet.pages;

import org.openqa.selenium.By;

public class RegisterPage extends BasePage {

    // ===== Locators (theo DOM Tét inspect) =====
    private final By nameInput = By.id("name");
    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");
    private final By confirmPasswordInput = By.id("passwordConfirm");
    private final By phoneInput = By.id("phone");
    private final By birthdayInput = By.id("birthday");

    private final By maleRadio = By.cssSelector("input[value='Male']");
    private final By femaleRadio = By.cssSelector("input[value='Female']");

    private final By agreeCheckbox = By.id("agree-term");
    private final By submitButton = By.cssSelector("button.btn_register");
    private final By toastOrAlert = By.cssSelector(".toast, .alert, .swal2-popup, .Toastify, .Toastify__toast, .notification");

    // ===== Error messages =====
    private final By errorMessages = By.cssSelector(".text-danger");

    // ===== Actions =====
    public RegisterPage open() {
        driver().get("https://demo4.cybersoft.edu.vn/register");
        return this;
    }

    public RegisterPage enterName(String name) {
        type(nameInput, name);
        return this;
    }

    public RegisterPage enterEmail(String email) {
        type(emailInput, email);
        return this;
    }

    public RegisterPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public RegisterPage enterConfirmPassword(String password) {
        type(confirmPasswordInput, password);
        return this;
    }

    public RegisterPage enterPhone(String phone) {
        type(phoneInput, phone);
        return this;
    }

    public RegisterPage enterBirthday(String yyyy_mm_dd) {
        type(birthdayInput, yyyy_mm_dd);
        return this;
    }

    public RegisterPage selectMale() {
        click(maleRadio);
        return this;
    }


    public RegisterPage selectFemale() {
        click(femaleRadio);
        return this;
    }


    public RegisterPage agreeTerms() {
        click(agreeCheckbox);
        return this;
    }

    public void submit() {
        click(submitButton);
    }

    // ===== Validations =====
    public boolean isErrorDisplayed() {
        return driver().findElements(errorMessages).size() > 0;
    }

    public String getAllErrorText() {
        return driver().findElements(errorMessages)
                .stream()
                .map(e -> e.getText().trim())
                .filter(s -> !s.isEmpty())
                .reduce("", (a, b) -> a + (a.isEmpty() ? "" : " | ") + b);
    }

    public String getToastOrAlertText() {
        return driver().findElements(toastOrAlert)
                .stream()
                .map(e -> e.getText().trim())
                .filter(s -> !s.isEmpty())
                .findFirst()
                .orElse("");
    }

    public String getAnyErrorText() {
        String fieldErrors = getAllErrorText();
        if (fieldErrors != null && !fieldErrors.isBlank()) return fieldErrors;

        String toast = getToastOrAlertText();
        if (toast != null && !toast.isBlank()) return toast;

        return "";
    }
}
