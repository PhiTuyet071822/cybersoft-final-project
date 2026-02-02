package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Auth_Login_TC08_Test extends BaseTest {

    @Test
    public void TC08_validateErrorMessage_invalidCredentials_toastTextCorrect() {

        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);

        LoginPage loginPage = new LoginPage().open();
        loginPage.login(data.validLoginEmail, data.invalidPassword);

        // Validate toast message (case này chắc chắn có)
        String toast = loginPage.getToastErrorText();
        System.out.println("[TC08] Toast error: " + toast);

        Assert.assertTrue(toast.contains("không đúng") || toast.toLowerCase().contains("invalid"),
                "Expected invalid credentials message. Actual: " + toast);
    }
}