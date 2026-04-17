package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.tests.base.BaseTest;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Auth_Login_TC06_Test extends BaseTest {

    @Test
    public void TC06_loginFail_invalidCredentials() {

        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);

        LoginPage loginPage = new LoginPage().open();
        loginPage.login(data.invalidLoginEmail, data.invalidPassword);

        String currentUrl = driver().getCurrentUrl().toLowerCase();
        Assert.assertTrue(currentUrl.contains("/login"),
                "Expected stay on /login when credentials invalid. Current URL: " + currentUrl);

        String toastMsg = loginPage.getToastErrorTextSafe();
        String fieldMsg = loginPage.getFieldErrorTextSafe();

        System.out.println("[TC06] Toast: " + toastMsg);
        System.out.println("[TC06] FieldErr: " + fieldMsg);

        
        Assert.assertTrue(
                !toastMsg.isBlank() || !fieldMsg.isBlank(),
                "Expected some error message (toast or field error) but both are empty."
        );

        String msg = (toastMsg + " " + fieldMsg).toLowerCase();
        Assert.assertTrue(
                msg.contains("không đúng") || msg.contains("invalid") || msg.contains("sai") || msg.contains("error"),
                "Expected invalid credentials message. Actual: " + msg
        );
    }
}