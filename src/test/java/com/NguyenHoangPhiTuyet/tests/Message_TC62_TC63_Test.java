package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.JobDetailMessagePage;
import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.tests.base.BaseTest;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Message_TC62_TC63_Test extends BaseTest {

    private static final int JOB_ID = 2;

    @Test(description = "TC62: Send message to seller - Tin nhắn gửi thành công")
    public void TC62_SendMessageToSeller_ShouldSucceed() {

        
        AuthData auth = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);
        new LoginPage().open().loginExpectSuccess(auth.validLoginEmail, auth.validPassword);

        JobDetailMessagePage msg = new JobDetailMessagePage().openById(JOB_ID);

        msg.clickContact();

        
        boolean responded = msg.waitAnyMessageUIResponse(8);

        Assert.assertTrue(responded,
                "TC62 FAIL: Click 'Contact Me' but no UI response (no modal/chat/toast, no redirect).");
    }

    @Test(description = "TC63: Reply to message - Tin nhắn trả lời hiển thị đúng")
    public void TC63_ReplyToMessage_ShouldDisplayCorrectly() {
        
        AuthData auth = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);
        new LoginPage().open().loginExpectSuccess(auth.validLoginEmail, auth.validPassword);

        JobDetailMessagePage msg = new JobDetailMessagePage().openById(JOB_ID);

        
        msg.clickContact();
        boolean responded = msg.waitAnyMessageUIResponse(8);

        
        Assert.assertTrue(responded,
                "TC63 FAIL: Cannot reply because message UI does not open after clicking 'Contact Me'.");

        
        String reply = "AUTO_REPLY_TC63";
        boolean canReply = msg.replyMessageBestEffort(reply);

        Assert.assertTrue(canReply,
                "TC63 FAIL: Message UI opened but cannot type/send reply.");
    }
}