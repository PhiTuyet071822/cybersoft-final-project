package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.pages.ProfilePage;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class Profile_TC53_Test extends BaseTest {

    @Test
    public void TC53_viewPublicProfile_profileInfoDisplayedCorrectly() {

        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);

        
        LoginPage lp = new LoginPage();
        lp.open();
        lp.login(data.validLoginEmail, data.validPassword);

        new WebDriverWait(driver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));

        
        ProfilePage profile = new ProfilePage().open();

        
        Assert.assertFalse(driver().getCurrentUrl().toLowerCase().contains("/login"),
                "Login failed - still on /login. Current URL: " + driver().getCurrentUrl());

        
        String email = profile.getDisplayedEmail();
        System.out.println("[TC53] Email: " + email);

        Assert.assertEquals(email.trim().toLowerCase(),
                data.validLoginEmail.trim().toLowerCase(),
                "Expected profile email displayed correctly.");

        
        String desc = profile.getDescriptionBlockText();
        System.out.println("[TC53] Description block:\n" + desc);

        Assert.assertTrue(desc.contains("Name"), "Expected Name section visible.");
        Assert.assertTrue(desc.contains("Phone"), "Expected Phone section visible.");
        Assert.assertTrue(desc.contains("Birthday"), "Expected Birthday section visible.");
    }
}