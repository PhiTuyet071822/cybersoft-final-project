package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.pages.ProfilePage;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Profile_TC55_Test extends BaseTest {

    @Test
    public void TC55_uploadChangeProfilePicture_shouldPersist_afterRefresh() {

        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);
        boolean ok = false;
        for (int i = 0; i < 5; i++) {
            ok = new LoginPage().loginAndVerify(data.validLoginEmail, data.validPassword);
            if (!ok) {
                throw new SkipException("Precondition failed: cannot access /profile after login. URL: " + driver().getCurrentUrl());
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }
        Assert.assertTrue(ok,
                "Precondition failed: cannot access /profile after login. URL: " + driver().getCurrentUrl());
        Assert.assertTrue(ok, "Precondition failed: cannot access /profile after login. URL: " + driver().getCurrentUrl());
        Assert.assertTrue(ok, "Login failed - cannot access /profile. Current URL: " + driver().getCurrentUrl());

        String avatarPath = Paths.get(System.getProperty("user.dir"),
                "src", "test", "resources", "testdata", "avatar.png").toAbsolutePath().toString();
        Assert.assertTrue(Files.exists(Path.of(avatarPath)), "Avatar file not found: " + avatarPath);

        ProfilePage profile = new ProfilePage();

        String before = profile.getAvatarSignature();
        profile.uploadAvatar(avatarPath);

        // refresh để check persistence
        driver().navigate().refresh();
        String afterRefresh = profile.getAvatarSignature();

        Assert.assertNotEquals(afterRefresh, before,
                "Avatar should persist after refresh\nexpected [true] but found [false]");

        // wait 3s for UI update attempt
        long end = System.currentTimeMillis() + 3000;
        String afterUpload = before;
        while (System.currentTimeMillis() < end) {
            afterUpload = profile.getAvatarSignature();
            if (!afterUpload.equals(before)) break;
            try { Thread.sleep(250); } catch (InterruptedException ignored) {}
        }

        // Refresh -> persistence check
        driver().navigate().refresh();
        afterRefresh = profile.getAvatarSignature();

        // Expected: afterRefresh != before (persist)
        // Actual (bug): afterRefresh == before
        Assert.assertNotEquals(afterRefresh, before,
                "BUG: Avatar is not persisted after refresh. " +
                        "\nBefore: " + before +
                        "\nAfterUpload: " + afterUpload +
                        "\nAfterRefresh: " + afterRefresh);
    }
}