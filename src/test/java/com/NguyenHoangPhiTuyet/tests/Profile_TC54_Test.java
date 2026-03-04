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

        
        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);

        
        new LoginPage()
                .open()
                .login(data.validLoginEmail, data.validPassword);

        
        ProfilePage profile = new ProfilePage().open();

        
        long now = System.currentTimeMillis();
        String newName = "Tuyet Update " + now;

        long tail = now % 100_000_000L; 
        String newPhone = "09" + String.format("%08d", tail); 

        String newBirthday = "1999-07-30";

        System.out.println("[TC54] newName=" + newName);
        System.out.println("[TC54] newPhone=" + newPhone);
        System.out.println("[TC54] newBirthday=" + newBirthday);

        
        profile.clickEditDescription()
                .updateName(newName)
                .updatePhone(newPhone)
                .updateBirthday(newBirthday)
                .clickSave();

        
        profile.waitUntilDescriptionContains(newName);

        
        String descBlock = profile.getDescriptionBlockText();
        System.out.println("[TC54] Description block after save:\n" + descBlock);

        Assert.assertTrue(normalize(descBlock).contains(normalize(newName)),
                "Expected updated name visible in Description block. Expected: " + newName);

        Assert.assertTrue(onlyDigits(descBlock).contains(onlyDigits(newPhone)),
                "Expected updated phone visible in Description block. Expected digits: " + onlyDigits(newPhone)
                        + " | Actual digits: " + onlyDigits(descBlock));

        Assert.assertTrue(normalize(descBlock).contains(normalize(newBirthday)),
                "Expected updated birthday visible in Description block. Expected: " + newBirthday);
    }

    private static String normalize(String s) {
        return s == null ? "" : s.replace('\u00A0', ' ')
                .replaceAll("\\s+", " ")
                .trim();
    }

    private static String onlyDigits(String s) {
        return s == null ? "" : s.replaceAll("\\D+", "");
    }
}