package com.NguyenHoangPhiTuyet.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;


    //    Locator menu
    private final By searchInput = By.name("searchInputCarousel");
    private final By searchButton = By.xpath("//button[@type='submit']");
    private By gigCardList = By.cssSelector(".card");

    // endpoint cua home page
    private String homeUrl = "https://demo4.cybersoft.edu.vn/";

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Ham mo trang home
    public void openHomePage() {
        driver.get(homeUrl);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void enterKeyword(String keyword) {
        WebElement keywordElement = driver.findElement(searchInput);
        keywordElement.clear();
        keywordElement.sendKeys(keyword);
    }

    public void clickSearchButton() {
        WebElement searchButtonElement = driver.findElement(searchButton);
        searchButtonElement.click();
    }

    // Ham tong hop cac buoc search
    public void searchKeyword(String keyword) {
        enterKeyword(keyword);
        clickSearchButton();
    }

    // Ham kiem tra co ket qua tra ve hay khong
    public boolean isSearchResultDisplayed() {
        List<WebElement> results = driver.findElements(gigCardList);
        return results.size() > 0;
    }

}
