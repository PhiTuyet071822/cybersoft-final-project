package com.NguyenHoangPhiTuyet.tests.flows;

import com.NguyenHoangPhiTuyet.pages.JobDetailPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.UUID;

public class ReviewFlow {

    private ReviewFlow() {}

    /** Tạo review (star + comment) và return comment để testcase khác reuse */
    public static String createReview(int jobId, int star) {
        String comment = "AUTO_REVIEW_" + UUID.randomUUID().toString().substring(0, 8);

        JobDetailPage job = new JobDetailPage()
                .openById(jobId)
                .scrollToComments();

        job.selectStar(star)
                .submitComment(comment);

        
        Assert.assertTrue(job.isCommentDisplayed(comment),
                "Create review failed: comment not displayed after submit. Comment=" + comment);

        return comment;
    }

    /** Verify review hiển thị đúng theo DOM TC50 */
    public static void assertReviewDisplayed(int jobId, String expectedComment, int expectedStar) {
        JobDetailPage job = new JobDetailPage()
                .openById(jobId)
                .scrollToComments();

        WebElement item = job.findReviewItemByComment(expectedComment);

        Assert.assertEquals(job.getCommentText(item), expectedComment, "Comment text mismatch");
        Assert.assertEquals(job.getStarScore(item), String.valueOf(expectedStar), "Star score mismatch");

        String name = job.getReviewerName(item);
        Assert.assertFalse(name.isBlank(), "Reviewer name should not be blank");

        Assert.assertTrue(job.isHelpfulDisplayed(item), "Helpful section not displayed");
    }
}