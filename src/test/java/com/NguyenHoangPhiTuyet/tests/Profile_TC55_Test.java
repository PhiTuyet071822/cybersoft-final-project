package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.pages.ProfilePage;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Profile_TC55_Test extends BaseTest {

    @Test
    public void TC55_uploadAvatar_shouldUpdateImmediately_expectedButNot() {

        
        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);

        LoginPage lp = new LoginPage();
        lp.open();
        Assert.assertTrue(lp.loginExpectSuccess(data.validLoginEmail, data.validPassword),
                "Precondition failed: login success expected. URL=" + driver().getCurrentUrl());

        
        ProfilePage profile = new ProfilePage().open();

        
        String avatarPath = Paths.get(System.getProperty("user.dir"),
                        "src", "test", "resources", "testdata", "avatar.png")
                .toAbsolutePath()
                .toString();
        Assert.assertTrue(Files.exists(Path.of(avatarPath)), "Avatar file not found: " + avatarPath);

        
        String before = profile.getAvatarSignature();
        System.out.println("[TC55] before=" + before);

        
        profile.uploadAvatar(avatarPath);

        
        long end = System.currentTimeMillis() + 3000;
        String afterUpload = before;
        while (System.currentTimeMillis() < end) {
            afterUpload = profile.getAvatarSignature();
            if (!afterUpload.equals(before)) break;
            try { Thread.sleep(250); } catch (InterruptedException ignored) {}
        }
        System.out.println("[TC55] afterUpload=" + afterUpload);

        
        
        Assert.assertNotEquals(afterUpload, before,
                "BUG: Avatar did NOT update after upload (manual also shows no change)." +
                        "\nBefore: " + before +
                        "\nAfterUpload: " + afterUpload +
                        "\nURL: " + driver().getCurrentUrl());
    }
}