package com.NguyenHoangPhiTuyet.pages;

import com.NguyenHoangPhiTuyet.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class ProfilePage extends BasePage {

    // ===== Profile view (TC53) =====
    private final By profileCard = By.cssSelector(".info_card");
    private final By emailText = By.cssSelector(".info_profile_label > p"); // <p>email</p>

    // Description block (anchor theo h3 Description) - dùng để verify TC53 & TC54
    private final By descriptionBlock =
            By.xpath("//h3[normalize-space()='Description']/ancestor::*[contains(@class,'inner_item')][1]");

    // Edit button in Description header row
    private final By descriptionEditBtn =
            By.xpath("//div[contains(@class,'inner_row')][.//h3[normalize-space()='Description']]//button[contains(@class,'edit')]");

    // ===== Edit modal/form (TC54) =====
    private final By editNameInput = By.id("name");
    private final By editPhoneInput = By.cssSelector("input[name='phone']");       // id dynamic => dùng name
    private final By editBirthdayInput = By.cssSelector("input[name='birthday']");
    private final By genderMaleRadio = By.cssSelector("input[name='gender'][value='male']");
    private final By genderFemaleRadio = By.cssSelector("input[name='gender'][value='female']");
    private final By saveBtn = By.cssSelector("button.btn_save[type='submit']");

    // ===== TC55: Avatar upload =====
    private final By avatarContainer = By.cssSelector(".info_profile_image");
    private final By avatarFileInput = By.cssSelector(".info_profile_image input[type='file']");
    private final By avatarPreview   = By.cssSelector("div.image");

    public ProfilePage open() {
        driver().get("https://demo4.cybersoft.edu.vn/profile");
        return this;
    }

    // ===== TC53 =====
    public String getDisplayedEmail() {
        WaitUtils.waitVisible(driver(), profileCard);
        return WaitUtils.waitVisible(driver(), emailText).getText().trim();
    }

    /** Lấy toàn bộ text của khối Description để assert "Name/Phone/Birthday" hiển thị */
    public String getDescriptionBlockText() {
        WaitUtils.waitVisible(driver(), descriptionBlock);
        return driver().findElement(descriptionBlock).getText().trim();
    }

    // ===== TC54 =====
    public ProfilePage clickEditDescription() {

        // scroll tới block Description để đảm bảo nút edit render trong viewport
        WaitUtils.waitVisible(driver(), descriptionBlock);
        WebElement block = driver().findElement(descriptionBlock);

        ((JavascriptExecutor) driver())
                .executeScript("arguments[0].scrollIntoView({block:'center'});", block);

        WebElement editBtn = WaitUtils.waitVisible(driver(), descriptionEditBtn);

        try {
            editBtn.click();
        } catch (Exception e) {
            // fallback nếu bị overlay
            ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", editBtn);
        }

        // đảm bảo form edit mở (phone input xuất hiện)
        WaitUtils.waitVisible(driver(), editPhoneInput);
        return this;
    }

    public ProfilePage updateName(String name) {
        clearAndType(editNameInput, name);
        return this;
    }

    public ProfilePage updatePhone(String phone) {
        clearAndType(editPhoneInput, phone);
        return this;
    }

    public ProfilePage updateBirthday(String yyyyMmDd) {
        clearAndType(editBirthdayInput, yyyyMmDd);
        return this;
    }

    public ProfilePage selectGenderMale() {
        WaitUtils.waitClickable(driver(), genderMaleRadio).click();
        return this;
    }

    public ProfilePage selectGenderFemale() {
        WaitUtils.waitClickable(driver(), genderFemaleRadio).click();
        return this;
    }

    public ProfilePage clickSave() {
        WaitUtils.waitClickable(driver(), saveBtn).click();
        return this;
    }

    public ProfilePage uploadAvatar(String absoluteFilePath) {
        WaitUtils.waitVisible(driver(), avatarFileInput);
        driver().findElement(avatarFileInput).sendKeys(absoluteFilePath);
        return this;
    }

    public boolean isAvatarUpdated() {
        WaitUtils.waitVisible(driver(), avatarPreview);

        WebElement preview = driver().findElement(avatarPreview);

        // Case 1: background-image / background style
        String style = preview.getAttribute("style");
        if (style != null) {
            String s = style.toLowerCase();
            if (s.contains("background-image") || s.contains("background:") || s.contains("url(")) {
                return true;
            }
        }

        // Case 2: <img> inside preview
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