package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.constants.TestData;
import com.NguyenHoangPhiTuyet.pages.RegisterPage;
import com.NguyenHoangPhiTuyet.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Auth_Register_TC03_Test extends BaseTest {

    @Test
    public void TC03_registerFail_existingEmail() {

        RegisterPage registerPage = new RegisterPage()
                .open()
                .enterName(TestData.VALID_NAME)
                .enterEmail(TestData.EXISTING_EMAIL)
                .enterPassword(TestData.VALID_PASSWORD)
                .enterConfirmPassword(TestData.VALID_PASSWORD)
                .enterPhone(TestData.VALID_PHONE)
                .enterBirthday(TestData.VALID_BIRTHDAY)
                .agreeTerms();

        registerPage.submit();

        
        String currentUrl = driver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/register"),
                "Expected stay on /register when registering with existing email. Current URL: " + currentUrl);


        String errorText = registerPage.getAnyErrorText();
        System.out.println("[TC03] Error message: " + errorText);
    }
}
