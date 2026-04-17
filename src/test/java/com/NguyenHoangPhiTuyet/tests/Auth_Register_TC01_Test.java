package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.constants.TestData;
import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.pages.RegisterPage;
import com.NguyenHoangPhiTuyet.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Auth_Register_TC01_Test extends BaseTest {

    @Test
    public void TC01_registerSuccess_buyer() {
        String email = TestData.uniqueEmail();

        new RegisterPage()
                .open()
                .enterName(TestData.VALID_NAME)
                .enterEmail(email)
                .enterPassword(TestData.VALID_PASSWORD)
                .enterConfirmPassword(TestData.VALID_PASSWORD)
                .enterPhone(TestData.VALID_PHONE)
                .enterBirthday(TestData.VALID_BIRTHDAY)
                .agreeTerms();

        new RegisterPage().submit();

        
        LoginPage loginPage = new LoginPage();
        boolean isAtLoginUrl = driver().getCurrentUrl().contains("/login");
        boolean loginUiVisible = loginPage.isRegisterNowDisplayed(); 

        Assert.assertTrue(isAtLoginUrl || loginUiVisible,
                "Expected redirect to Login page (URL contains /login) or Login UI visible after successful registration.");
    }
}
