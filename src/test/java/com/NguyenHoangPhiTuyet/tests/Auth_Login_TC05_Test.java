package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.pages.ProfilePage;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Auth_Login_TC05_Test extends BaseTest {

    @Test
    public void TC05_loginSuccess_redirectToHome() {

        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);

        LoginPage loginPage = new LoginPage()
                .open();

        loginPage.login(data.validLoginEmail, data.validPassword);

// Đợi tối đa 5s xem có redirect sang /profile không (tránh flaky)
        long end = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < end) {
            if (driver().getCurrentUrl().contains("/profile")) break;
        }

        String currentUrl = driver().getCurrentUrl();

        if (!currentUrl.contains("/profile")) {
            String err = loginPage.getAnyErrorText();
            Assert.fail("Login did not redirect to /profile. Current URL: " + currentUrl + ". Error: " + err);
        }

        Assert.assertTrue(currentUrl.contains("/profile"),
                "Expected redirect to profile page after login. Current URL: " + currentUrl);

    }
}