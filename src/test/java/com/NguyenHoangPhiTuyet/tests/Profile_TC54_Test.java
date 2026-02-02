package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.pages.ProfilePage;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Profile_TC54_Test extends BaseTest {

    @Test
    public void TC54_editProfileInformation_updateSuccessfully() {

        // 1) Read login data from JSON
        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);

        // 2) Login
        new LoginPage()
                .open()
                .login(data.validLoginEmail, data.validPassword);

        boolean ok = new LoginPage().loginAndVerify(data.validLoginEmail, data.validPassword);
        Assert.assertTrue(ok, "Login failed - cannot access /profile. Current URL: " + driver().getCurrentUrl());

        ProfilePage profile = new ProfilePage().open();

        // 3) Update data (unique để verify chắc)
        String newName = "Tuyet Update " + System.currentTimeMillis();
        String newPhone = "09" + (System.currentTimeMillis() % 100000000); // tạo số bắt đầu 09
        String newBirthday = "1999-07-30"; // giữ format hệ thống đang hiển thị

        // 4) Edit & Save
        profile.clickEditDescription()
                .updateName(newName)
                .updatePhone(newPhone)
                .updateBirthday(newBirthday)
                .clickSave();

        // 5) Verify (reload lại để view mode ổn định)
        profile.open();

        String descBlock = profile.getDescriptionBlockText();
        System.out.println("[TC54] Description block after save: " + descBlock);

        Assert.assertTrue(descBlock.contains("Tuyet Update"),
                "Expected updated name visible in Description block.");
        Assert.assertTrue(descBlock.contains(newPhone),
                "Expected updated phone visible in Description block.");
        Assert.assertTrue(descBlock.contains(newBirthday),
                "Expected updated birthday visible in Description block.");
    }
}