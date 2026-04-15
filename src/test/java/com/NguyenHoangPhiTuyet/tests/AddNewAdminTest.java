package com.NguyenHoangPhiTuyet.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.NguyenHoangPhiTuyet.pages.AdminPage;
import com.NguyenHoangPhiTuyet.pages.LoginAdminPage;
import utils.BaseTest;

public class AddNewAdminTest extends BaseTest {
    private static final String VALID_EMAIL = "admin-test@gmail.com";
    private static final String VALID_PASSWORD = "123456";
    @Test
    public void testAddNewAdmin() throws Exception {

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

        Assert.assertTrue(
                driver.getCurrentUrl().contains("admin"),
                "Login khong thanh cong!"
        );

        // Vào Manage User
        AdminPage adminPage = new AdminPage(driver);
        adminPage.clickManageUser();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Click Add New Admin
        adminPage.clickAddNewAdmin();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Fill form
        adminPage.fillAdminForm(
                "auto" + System.currentTimeMillis() + "@gmail.com",
                "Automation Test",
                "123456",
                "0909999999"
        );

        // Save
        adminPage.clickSave();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Verify hiển thị thông báo thành công
        Assert.assertTrue(
                adminPage.isAddAdminSuccess(),
                "Không xuất hiện thông báo thêm quản trị thành công!"
        );
    }
}
