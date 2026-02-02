package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
// import com.NguyenHoangPhiTuyet.utils.SessionUtils; // Không dùng cái này nữa
import org.testng.Assert;
import org.testng.annotations.Test;

public class Auth_Login_TC07_Test extends BaseTest {

    @Test
    public void TC07_logoutSuccess_clickLogoutButton_redirectToLogin() {

        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);
        LoginPage loginPage = new LoginPage(); // Khởi tạo object để dùng lại

        // 1) Precondition: ensure login success
        boolean loggedIn = false;
        for (int i = 0; i < 2; i++) {
            loginPage.open()
                    .login(data.validLoginEmail, data.validPassword);

            driver().get("https://demo4.cybersoft.edu.vn/profile");

            if (driver().getCurrentUrl().contains("/profile")) {
                loggedIn = true;
                break;
            }
        }

        Assert.assertTrue(loggedIn, "Precondition failed: Not logged in.");

        // 2) Logout Action (UI Interaction)
        // Dòng này sẽ FAIL -> TimeoutException hoặc NoSuchElementException
        // vì locator 'logoutBtn' không tìm thấy trên màn hình Profile hiện tại
        loginPage.clickLogout();

        // 3) Verify logout (Code này sẽ không chạy tới được vì đã fail ở trên)
        Assert.assertTrue(
                driver().getCurrentUrl().contains("/login"),
                "Expected redirect to /login after logout."
        );
    }
}