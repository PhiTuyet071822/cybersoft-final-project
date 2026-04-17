package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.tests.base.BaseTest;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Auth_Login_TC08_Test extends BaseTest {

    @Test
    public void TC08_validateErrorMessage_invalidCredentials_toastTextCorrect() {

        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);

        LoginPage loginPage = new LoginPage().open();
        loginPage.login(data.invalidLoginEmail, data.invalidPassword);

        Assert.assertTrue(driver().getCurrentUrl().toLowerCase().contains("/login"),
                "Expected still on /login after invalid login. Current URL: " + driver().getCurrentUrl());

        String toast = loginPage.getToastErrorTextSafe();
        String field = loginPage.getFieldErrorTextSafe();

        String combined = (toast + " " + field).trim();
        System.out.println("[TC08] Error message: " + combined);

        Assert.assertFalse(combined.isBlank(),
                "Expected error message (toast or field error) but got empty.");

        String t = combined.toLowerCase();
        Assert.assertTrue(
                t.contains("không đúng") || t.contains("invalid") || t.contains("sai") || t.contains("error"),
                "Expected invalid credentials message. Actual: " + combined
        );
    }
}