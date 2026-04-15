package com.NguyenHoangPhiTuyet.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SellerPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private JavascriptExecutor js;

    private String homeUrl = "https://demo4.cybersoft.edu.vn/";
    private final By becomeSellerBtn =
            By.xpath("//li[text()='Become a Seller']");

    // Locator form
    private final By nameJobInput = By.name("tenCongViec");
    private final By descriptionInput = By.name("moTa");
    private final By shortDescriptionInput = By.name("moTaNgan");
    private final By priceInput = By.name("giaTien");
    private final By rateInput = By.name("danhGia");
    private final By detailCodeInput = By.name("maChiTietCongViec");
    private final By starRatingInput = By.name("saoCongViec");

    private final By saveBtn = By.xpath("//button[contains(text(),'Save')]");

    private final By createNewGigBtn =
            By.xpath("//button[contains(text(),'Create')]");
    private final By gigListBtn =
            By.xpath("//a[contains(text(),'My Gigs')]");
    private final By editGigBtn =
            By.xpath("(//button[contains(text(),'Edit')])[1]");
    private final By deleteGigBtn =
            By.xpath("(//button[contains(text(),'Delete')])[1]");
    private final By confirmDeleteBtn =
            By.xpath("//button[contains(text(),'Confirm')]");
    private final By firstGigTitle =
            By.xpath("(//h3[@class='gig-title'])[1]");
    private final By requiredErrorMessage =
            By.xpath("//span[contains(text(),'required')]");

    public SellerPage(WebDriver driver) {
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

    public void openHomePage() {
        driver.get(homeUrl);
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    // ===== Bước bắt buộc trước khi fill form =====
    public void clickBecomeSeller() throws Exception {
        WebElement clickbecomeseller = driver.findElement(becomeSellerBtn);
        captureHighlight(clickbecomeseller,"Click_Become_A_Seller");
        clickbecomeseller.click();
        Thread.sleep(2000);
    }

    public void clickCreateNewGig() throws Exception{
        WebElement clickcreatenewgig = driver.findElement(createNewGigBtn);
        captureHighlight(clickcreatenewgig,"Click_Create_New_Gig");
        clickcreatenewgig.click();
        Thread.sleep(2000);
    }

    // ====== Nhập từng field ======

    public void enterNameJob(String name){
        WebElement element = driver.findElement(nameJobInput);
        element.clear();
        element.sendKeys(name);
    }

    public void enterDescription(String description){
        WebElement element = driver.findElement(descriptionInput);
        element.clear();
        element.sendKeys(description);
    }

    public void enterShortDescription(String shortDes){
        WebElement element = driver.findElement(shortDescriptionInput);
        element.clear();
        element.sendKeys(shortDes);
    }

    public void enterPrice(String price){
        WebElement element = driver.findElement(priceInput);
        element.clear();
        element.sendKeys(price);
    }

    public void enterRate(String rate){
        WebElement element = driver.findElement(rateInput);
        element.clear();
        element.sendKeys(rate);
    }

    public void enterDetailCode(String detailCode){
        WebElement element = driver.findElement(detailCodeInput);
        element.clear();
        element.sendKeys(detailCode);
    }

    public void enterStarRating(String star){
        WebElement element = driver.findElement(starRatingInput);
        element.clear();
        element.sendKeys(star);
    }

    public void clickSave(){
        driver.findElement(saveBtn).click();
    }

    // ====== Hàm tổng hợp ======
    public void fillCreateGigForm(String name, String des, String shortDes,
                                  String price, String rate,
                                  String detailCode, String star){

        enterNameJob(name);
        enterDescription(des);
        enterShortDescription(shortDes);
        enterPrice(price);
        enterRate(rate);
        enterDetailCode(detailCode);
        enterStarRating(star);
    }

    public void goToGigList(){
        driver.findElement(gigListBtn).click();
    }

    public void clickEditGig(){
        driver.findElement(editGigBtn).click();
    }

    public void updateNameJob(String newName){
        WebElement element = driver.findElement(nameJobInput);
        element.clear();
        element.sendKeys(newName);
    }

    public void clickDeleteGig(){
        driver.findElement(deleteGigBtn).click();
    }

    public void confirmDelete(){
        driver.findElement(confirmDeleteBtn).click();
    }
    public boolean isGigStillDisplayed(){
        try{
            return driver.findElement(firstGigTitle).isDisplayed();
        }catch(Exception e){
            return false;
        }
    }
    public boolean isRequiredErrorDisplayed(){
        try{
            return driver.findElement(requiredErrorMessage).isDisplayed();
        }catch(Exception e){
            return false;
        }
    }
}
