package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.constants.TestData;
import com.NguyenHoangPhiTuyet.pages.RegisterPage;
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

        // Kỳ vọng chính: đăng ký thất bại -> vẫn ở trang /register (không redirect sang /login hoặc /profile)
        String currentUrl = driver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/register"),
                "Expected stay on /register when registering with existing email. Current URL: " + currentUrl);

// Bonus: nếu hệ thống có hiển thị message thì log ra (không ép assert)
        String errorText = registerPage.getAnyErrorText();
        System.out.println("[TC03] Error message: " + errorText);
    }
}
