package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.pages.ProfilePage;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Profile_TC53_Test extends BaseTest {

    @Test
    public void TC53_viewPublicProfile_profileInfoDisplayedCorrectly() {

        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);

        new LoginPage()
                .open()
                .login(data.validLoginEmail, data.validPassword);

        boolean ok = new LoginPage().loginAndVerify(data.validLoginEmail, data.validPassword);
        Assert.assertTrue(ok, "Login failed - cannot access /profile. Current URL: " + driver().getCurrentUrl());

        ProfilePage profile = new ProfilePage().open();

        // 1) Verify email hiển thị đúng (Tét đã PASS phần này)
        String email = profile.getDisplayedEmail();
        System.out.println("[TC53] Email: " + email);

        Assert.assertTrue(email.equalsIgnoreCase(data.validLoginEmail),
                "Expected profile email displayed correctly.");

        // 2) Verify khối Description hiển thị đủ thông tin
        String desc = profile.getDescriptionBlockText();
        System.out.println("[TC53] Description block: " + desc);

        Assert.assertTrue(desc.contains("Name"), "Expected Name section visible.");
        Assert.assertTrue(desc.contains("Phone"), "Expected Phone section visible.");
        Assert.assertTrue(desc.contains("Birthday"), "Expected Birthday section visible.");
    }
}