package com.NguyenHoangPhiTuyet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchResultPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // ===== Dropdown buttons =====
    private final By categoryDropdownBtn =
            By.xpath("//button[contains(text(),'Category')]");

    private final By priceDropdownBtn =
            By.xpath("//button[contains(text(),'Price')]");

    // ===== Clear all =====
    private final By clearAllBtn =
            By.xpath("//button[contains(text(),'Clear')]");

    // ===== Gig card =====
    private final By gigCardList =
            By.cssSelector(".card");

    // ===== Price list =====
    private final By gigPriceList =
            By.cssSelector(".gig-price");

    private final By gigTitleList =
            By.cssSelector(".service-name a");

    public SearchResultPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // ===== Select Category =====
    public void selectCategory(String categoryName){

        wait.until(ExpectedConditions
                        .elementToBeClickable(categoryDropdownBtn))
                .click();

        By categoryItem = By.xpath(
                "//a[contains(@class,'dropdown-item') and text()='"
                        + categoryName + "']"
        );
        try { Thread.sleep(2000); } catch (Exception e){}
        wait.until(ExpectedConditions
                        .elementToBeClickable(categoryItem))
                .click();

    }
    public List<String> getGigTitles(){

        List<WebElement> elements = driver.findElements(gigTitleList);
        List<String> titles = new ArrayList<>();

        for(WebElement e : elements){
            titles.add(e.getText());
        }

        return titles;
    }

    // ===== Select Price =====
    public void selectPrice(String priceRange) throws Exception {

        wait.until(ExpectedConditions
                        .elementToBeClickable(priceDropdownBtn))
                .click();
        try { Thread.sleep(500); } catch (Exception e){}
        By priceItem = By.xpath(
                "//a[contains(@class,'dropdown-item') and contains(text(),'"
                        + priceRange + "')]"
        );

        wait.until(ExpectedConditions
                        .elementToBeClickable(priceItem))
                .click();
        try { Thread.sleep(500); } catch (Exception e){}
    }

    // ===== Clear All =====
    public void clickClearAll(){
        wait.until(ExpectedConditions
                        .elementToBeClickable(clearAllBtn))
                .click();
    }

    // ===== Verify có gig hiển thị =====
    public boolean isGigDisplayed(){
        List<WebElement> gigs = driver.findElements(gigCardList);
        return gigs.size() > 0;
    }

    // ===== Verify price hợp lệ =====
    public boolean isPriceValid(int maxPrice){

        List<WebElement> prices = driver.findElements(gigPriceList);

        for(WebElement price : prices){
            String text = price.getText().replace("$","").trim();
            int value = Integer.parseInt(text);

            if(value > maxPrice){
                return false;
            }
        }
        return true;
    }
}
