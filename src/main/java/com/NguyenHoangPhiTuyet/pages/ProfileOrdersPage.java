package com.NguyenHoangPhiTuyet.pages;

import com.NguyenHoangPhiTuyet.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProfileOrdersPage extends BasePage {

    private final By gigsContainer = By.cssSelector("div.main_row .gigs");

    public boolean isOrdersSectionVisible() {
        try {
            return WaitUtils.waitVisible(driver(), gigsContainer).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getOrderedItemsCount() {
        WebElement container = WaitUtils.waitVisible(driver(), gigsContainer);
        List<WebElement> items = container.findElements(By.xpath("./div"));
        return items.size();
    }
}