package com.NguyenHoangPhiTuyet.pages;

import com.NguyenHoangPhiTuyet.utils.WaitUtils;
import org.openqa.selenium.*;
import java.util.List;

public class JobDetailPage extends BasePage {

    private static final String BASE_URL = "https://demo4.cybersoft.edu.vn";

    
    private final By commentSection = By.cssSelector("div.add-comment.py-4, div.add-comment");
    private final By commentTextarea = By.cssSelector("textarea[name='noiDung']");
    private final By commentSubmitBtn = By.cssSelector("button.comment-submit");
    private final By ratingRow = By.cssSelector(".comment-rating .d-flex.align-items-center.gap-1");

    
    private final By reviewItems = By.cssSelector("li.row.py-4");
    private final By reviewName = By.cssSelector(".reviewer-name h3");
    private final By reviewStarScore = By.cssSelector(".reviewer-name .star-score");
    private final By reviewCountry = By.cssSelector(".reviewer-country");
    private final By reviewComment = By.cssSelector(".comment p");
    private final By reviewHelpful = By.cssSelector(".reviewer-helpful");

    public JobDetailPage openById(int id) {
        driver().get(BASE_URL + "/jobDetail/" + id);
        return this;
    }

    public JobDetailPage scrollToComments() {
        WebElement section = WaitUtils.waitVisible(driver(), commentSection);
        ((JavascriptExecutor) driver()).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", section
        );
        
        WaitUtils.waitVisible(driver(), commentTextarea);
        return this;
    }

    /** “Tinh thần TC”: nếu UI chặn review khi chưa order => gated=true */
    public boolean isReviewGatedByOrder() {
        try {
            WebElement txt = driver().findElement(commentTextarea);
            WebElement btn = driver().findElement(commentSubmitBtn);

            boolean txtEnabled = txt.isEnabled() && txt.getAttribute("disabled") == null;
            boolean btnEnabled = btn.isEnabled() && btn.getAttribute("disabled") == null;

            return !(txtEnabled && btnEnabled);
        } catch (NoSuchElementException e) {
            return true; 
        }
    }

    public JobDetailPage selectStar(int star1to5) {
        By starRadio = By.xpath(
                "//ul[contains(@class,'ant-rate')]//div[@role='radio' and @aria-posinset='" + star1to5 + "']"
        );
        WebElement radio = WaitUtils.waitClickable(driver(), starRadio);
        try {
            radio.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", radio);
        }
        return this;
    }

    public JobDetailPage submitComment(String content) {
        WebElement txt = WaitUtils.waitVisible(driver(), commentTextarea);
        txt.clear();
        txt.sendKeys(content);

        WebElement btn = WaitUtils.waitVisible(driver(), commentSubmitBtn);
        ((JavascriptExecutor) driver()).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

        try {
            WaitUtils.waitClickable(driver(), btn).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", btn);
        }
        return this;
    }
    public boolean isCommentDisplayed(String content) {
        By commentText = By.xpath("//*[contains(normalize-space(.),'" + content + "')]");
        try {
            return WaitUtils.waitVisible(driver(), commentText).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement findReviewItemByComment(String expectedComment) {
        WaitUtils.waitPresent(driver(), reviewItems);

        List<WebElement> items = driver().findElements(reviewItems);
        for (WebElement item : items) {
            try {
                List<WebElement> ps = item.findElements(reviewComment);
                if (ps.isEmpty()) continue;
                String actual = ps.get(0).getText().trim();
                if (actual.equals(expectedComment)) {
                    return item;
                }
            } catch (Exception ignored) {}
        }
        throw new NoSuchElementException("Review with comment not found: " + expectedComment);
    }

    public String getReviewerName(WebElement reviewItem) {
        return reviewItem.findElement(reviewName).getText().trim();
    }

    public String getStarScore(WebElement reviewItem) {
        return reviewItem.findElement(reviewStarScore).getText().trim();
    }

    public String getCountryText(WebElement reviewItem) {
        
        String t = reviewItem.findElement(reviewCountry).getText();
        return t == null ? "" : t.trim();
    }

    public String getCommentText(WebElement reviewItem) {
        return reviewItem.findElement(reviewComment).getText().trim();
    }

    public boolean isHelpfulDisplayed(WebElement reviewItem) {
        return !reviewItem.findElements(reviewHelpful).isEmpty()
                && reviewItem.findElement(reviewHelpful).isDisplayed();
    }
}