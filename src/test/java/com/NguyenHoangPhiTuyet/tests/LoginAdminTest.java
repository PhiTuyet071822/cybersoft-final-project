package com.NguyenHoangPhiTuyet.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.NguyenHoangPhiTuyet.pages.LoginAdminPage;
import utils.BaseTest;

public class LoginAdminTest extends BaseTest {
//    static final: tao hang so, dung chung cho cac test case
    private static final String VALID_EMAIL = "admin-test@gmail.com";
    private static final String VALID_PASSWORD = "123456";
    private static final String INVALID_EMAIL = "abc";
    private static final String INVALID_PASSWORD = "abc123";

//    Viet testcase
    @Test
    public void testLoginAdminSuccess(){
//        B1: tao doi tuong LoginPage
        LoginAdminPage loginAdminPage = new LoginAdminPage(driver);
//        B2: goi ham login de thuc hien cac step login
        loginAdminPage.login(VALID_EMAIL, VALID_PASSWORD);
//        B3: doi cho server kiem tra thong tin user
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        B4: kiem tra ket qua voi expect
//        chien luoc kiem tra
//        kiem tra endpoint url
//        neu endpoint moi != endpointUrl logic => pass
        String currentUrl = loginAdminPage.getCurrentUrl();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(currentUrl.contains("admin"));
    }

    @Test
    public void testLoginAdminFail(){
//        B1: tao doi tuong LoginPage
        LoginAdminPage loginAdminPage = new LoginAdminPage(driver);
//        B2: goi ham login de thuc hien cac step login
        loginAdminPage.login(VALID_EMAIL, INVALID_PASSWORD);
//        B3: doi cho server kiem tra thong tin user
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        B4: kiem tra ket qua voi expect
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Verify error message hiển thị
        Assert.assertTrue(
                loginAdminPage.isErrorMessageDisplayed(),
                "Expected error message but not displayed!"
        );
    }
}
