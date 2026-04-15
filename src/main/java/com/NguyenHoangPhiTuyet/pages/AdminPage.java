package com.NguyenHoangPhiTuyet.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    // ===== Locator =====

    private By manageUserMenu =
            By.xpath("//a[@href='/admin/qlnd']");

    private By addNewAdminBtn =
            By.xpath("//button[contains(text(),'Add New Admin')]");

    private By emailInput = By.name("email");
    private By nameInput = By.name("name");
    private By passwordInput = By.name("password");
    private By phoneInput = By.name("phone");

    private By saveBtn =
            By.xpath("//button[@type='submit']");
    private By successMessage =
            By.xpath("//div[@role='alert' and contains(.,'thành công')]");

    public AdminPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    //    ham highlight
    public WebElement highlightElement (WebElement element) throws Exception {
        js.executeScript("arguments[0].style.boder='3px solid red';"+
                        "arguments[0].style.backgroundColor='yellow' ;" +
                        "arguments[0].style.boxShadow= ' 0 0 10px red' ;",
                element
        );
        Thread.sleep(500);
        return element;
    }

    //    ham remove highlight
    public void removeHighlight (WebElement element){
        js.executeScript(
                "arguments[0].removeAttribute('style');",
                element
        );
    }

    //    captureScreenshot
    public void captureHighlight (WebElement element, String stepName) throws Exception {
        highlightElement(element);
        removeHighlight(element);
    }
    public void clickManageUser(){
        wait.until(ExpectedConditions.elementToBeClickable(manageUserMenu)).click();
    }

    public void clickAddNewAdmin() throws  Exception {
        WebElement clickaddnewadmin = driver.findElement(addNewAdminBtn);
        captureHighlight(clickaddnewadmin,"Click_Add_New_Admin");
        clickaddnewadmin.click();
        Thread.sleep(2000);
    }

    public void fillAdminForm(String email, String name, String pass, String phone){
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(passwordInput).sendKeys(pass);
        driver.findElement(phoneInput).sendKeys(phone);
    }

    public void clickSave(){
        driver.findElement(saveBtn).click();
    }
    public boolean isAddAdminSuccess(){
        return wait.until(ExpectedConditions
                        .visibilityOfElementLocated(successMessage))
                .isDisplayed();
    }
}