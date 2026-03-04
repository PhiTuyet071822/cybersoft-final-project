package com.NguyenHoangPhiTuyet.pages;

import com.NguyenHoangPhiTuyet.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage extends BasePage {

    
    private final By profileCard = By.cssSelector(".info_card");
    private final By emailText = By.cssSelector(".info_profile_label > p"); 

    
    private final By descriptionBlock =
            By.xpath("//h3[normalize-space()='Description']/ancestor::*[contains(@class,'inner_item')][1]");

    
    private final By descriptionEditBtn =
            By.xpath("//div[contains(@class,'inner_row')][.//h3[normalize-space()='Description']]//button[contains(@class,'edit')]");

    
    private final By editNameInput = By.id("name");
    private final By editPhoneInput = By.cssSelector("input[name='phone']"); 
    private final By editBirthdayInput = By.cssSelector("input[name='birthday']");
    private final By genderMaleRadio = By.cssSelector("input[name='gender'][value='male']");
    private final By genderFemaleRadio = By.cssSelector("input[name='gender'][value='female']");
    private final By saveBtn = By.cssSelector("button.btn_save[type='submit']");

    
    private final By avatarContainer = By.cssSelector(".info_profile_image");
    private final By avatarFileInput = By.cssSelector(".info_profile_image input[type='file']");
    private final By avatarPreview = By.cssSelector("div.image");

    public ProfilePage open() {
        driver().get("https://demo4.cybersoft.edu.vn/profile");

        
        if (driver().getCurrentUrl().toLowerCase().contains("/login")) {
            throw new AssertionError("Not logged in. Redirected to /login when opening /profile. URL=" + driver().getCurrentUrl());
        }

        WaitUtils.waitVisible(driver(), profileCard);
        return this;
    }

    
    public String getDisplayedEmail() {
        WaitUtils.waitVisible(driver(), profileCard);
        return WaitUtils.waitVisible(driver(), emailText).getText().trim();
    }

    /** Lấy toàn bộ text của khối Description để assert "Name/Phone/Birthday" hiển thị */
    public String getDescriptionBlockText() {
        return WaitUtils.waitVisible(driver(), descriptionBlock).getText().trim();
    }

    /** Chờ Description block chứa 1 đoạn text (để tránh đọc quá sớm khi React rerender) */
    public ProfilePage waitUntilDescriptionContains(String expected) {
        WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(10));
        wait.until(d -> {
            String text = getDescriptionBlockText();
            if (text == null) return false;
            text = text.replace('\u00A0', ' ');
            return text.contains(expected);
        });
        return this;
    }

    
    public ProfilePage clickEditDescription() {

        System.out.println("[TC54] currentUrl before clickEditDescription = " + driver().getCurrentUrl());

        
        WebElement block = WaitUtils.waitVisible(driver(), descriptionBlock);
        ((JavascriptExecutor) driver())
                .executeScript("arguments[0].scrollIntoView({block:'center'});", block);

        
        WebElement editBtn = WaitUtils.waitClickable(driver(), descriptionEditBtn);

        try {
            editBtn.click();
        } catch (Exception e) {
            
            ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", editBtn);
        }

        
        WebElement phone = WaitUtils.waitClickable(driver(), editPhoneInput);

        
        try {
            phone.click();
        } catch (Exception ignored) {
        }

        return this;
    }

    public ProfilePage updateName(String name) {
        clearAndType(editNameInput, name);
        return this;
    }

    public ProfilePage updatePhone(String phone) {
        WebElement el = WaitUtils.waitClickable(driver(), editPhoneInput);
        el.click();
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        el.sendKeys(Keys.BACK_SPACE);
        el.sendKeys(phone);

        return this;

    }

    public String getPhoneInputValue() {
        return WaitUtils.waitVisible(driver(), editPhoneInput)
                .getAttribute("value");
    }

    public ProfilePage updateBirthday(String yyyyMmDd) {
        clearAndType(editBirthdayInput, yyyyMmDd);
        return this;
    }

    public ProfilePage clickSave() {
        WaitUtils.waitClickable(driver(), saveBtn).click();

        
        WaitUtils.waitInvisible(driver(), editPhoneInput);

        
        WaitUtils.waitVisible(driver(), descriptionBlock);

        return this;
    }

    
    public ProfilePage uploadAvatar(String absoluteFilePath) {

        
        WaitUtils.waitVisible(driver(), avatarContainer);
        driver().findElement(avatarContainer).click();
        
        WebElement input = WaitUtils.waitPresent(driver(), avatarFileInput);

        input.sendKeys(absoluteFilePath);
        return this;
    }

    public boolean isAvatarUpdated() {
        WaitUtils.waitVisible(driver(), avatarPreview);
        WebElement preview = driver().findElement(avatarPreview);

        
        String style = preview.getAttribute("style");
        if (style != null) {
            String s = style.toLowerCase();
            if (s.contains("background-image") || s.contains("background:") || s.contains("url(")) {
                return true;
            }
        }

        
        if (!preview.findElements(By.cssSelector("img")).isEmpty()) {
            String src = preview.findElement(By.cssSelector("img")).getAttribute("src");
            return src != null && !src.trim().isEmpty();
        }

        return false;
    }

    public String getAvatarSignature() {
        WaitUtils.waitVisible(driver(), avatarContainer);
        WebElement container = driver().findElement(avatarContainer);

        String style = container.getAttribute("style");
        if (style != null && !style.trim().isEmpty()) return style.trim();

        if (!container.findElements(By.cssSelector("img")).isEmpty()) {
            String src = container.findElement(By.cssSelector("img")).getAttribute("src");
            if (src != null && !src.trim().isEmpty()) return src.trim();
        }

        return container.getAttribute("outerHTML");
    }
}