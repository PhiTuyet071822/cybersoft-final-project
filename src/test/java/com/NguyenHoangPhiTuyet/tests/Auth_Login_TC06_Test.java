package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Auth_Login_TC06_Test extends BaseTest {

    @Test
    public void TC06_loginFail_invalidCredentials() {

        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);

        LoginPage loginPage = new LoginPage().open();
        loginPage.login(data.validLoginEmail, data.invalidPassword);

        // 1️⃣ Assert vẫn ở login
        String currentUrl = driver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/login"),
                "Expected stay on /login when credentials invalid. Current URL: " + currentUrl);

        // 2️⃣ Assert toast error
        String toastMsg = loginPage.getToastErrorText();
        System.out.println("[TC06] Toast message: " + toastMsg);

        Assert.assertFalse(toastMsg.isBlank(),
                "Expected toast error message displayed.");

        // OPTIONAL – nếu muốn assert content
        Assert.assertTrue(
                toastMsg.toLowerCase().contains("không đúng")
                        || toastMsg.toLowerCase().contains("invalid"),
                "Expected invalid credentials message.");
    }
}
