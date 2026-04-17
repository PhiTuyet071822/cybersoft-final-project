package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.tests.base.BaseTest;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.tests.flows.OrderFlow;
import com.NguyenHoangPhiTuyet.tests.flows.ReviewFlow;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class Review_TC47_Test extends BaseTest {

    private static final int JOB_ID = 2;
    private static String createdComment; 

    @Test(description = "TC47 Positive: Buyer review AFTER completed order -> review saved")
    public void TC47_Positive_CreateReview_AfterCompletedOrder() {
        AuthData auth = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);

        LoginPage login = new LoginPage().open();
        Assert.assertTrue(login.loginExpectSuccess(auth.validLoginEmail, auth.validPassword), "Login failed");

        boolean ordered = OrderFlow.completeOrderForJob(JOB_ID);
        if (!ordered) throw new SkipException("Precondition failed: cannot complete order for jobId=" + JOB_ID);

        createdComment = ReviewFlow.createReview(JOB_ID, 5);
    }

    @Test(description = "TC47 Negative: Buyer review WITHOUT completed order -> should be blocked (BUG if allowed)")
    public void TC47_Negative_ReviewWithoutCompletedOrder_ShouldBeBlocked() {
        AuthData auth = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);


        LoginPage login = new LoginPage().open();
        Assert.assertTrue(
                login.loginExpectSuccess(auth.validLoginEmail, auth.validPassword),
                "Login failed"
        );


        String bugComment = ReviewFlow.createReview(JOB_ID, 5);


        ReviewFlow.assertReviewDisplayed(JOB_ID, bugComment, 5);


        Assert.fail(
                "BUG FOUND (TC47 Negative): System allowed buyer to create review WITHOUT completed order."
                        + " jobId=" + JOB_ID
                        + ", stars=5"
                        + ", comment=" + bugComment
        );
    }

    @Test(
            description = "TC50: Display reviews on gig page - Review hiển thị đúng",
            dependsOnMethods = "TC47_Positive_CreateReview_AfterCompletedOrder"
    )
    public void TC50_DisplayReviewsOnGigPage_ShouldShowCorrectInfo() {
        
        ReviewFlow.assertReviewDisplayed(JOB_ID, createdComment, 5);
    }
}