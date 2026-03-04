package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.pages.JobDetailOrderPage;
import com.NguyenHoangPhiTuyet.pages.LoginPage;
import com.NguyenHoangPhiTuyet.tests.data.AuthData;
import com.NguyenHoangPhiTuyet.tests.flows.OrderFlow;
import com.NguyenHoangPhiTuyet.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Order_Purchase_Flow_Test extends BaseTest {

    private static final int JOB_ID = 2;

    @Test(priority = 1, description = "TC34: Order thất bại - Chưa đăng nhập")
    public void TC34_OrderWithoutLogin() {

        JobDetailOrderPage job = new JobDetailOrderPage().openById(JOB_ID);
        Assert.assertTrue(job.isJobDetailVisible(), "Job detail not visible");

        job.clickSubmitOrder();

        Assert.assertTrue(
                job.waitRedirectToLoginOrToast(),
                "TC34 Fail (Bug): Click Submit nhưng không redirect login và cũng không có UI/toast yêu cầu đăng nhập."
        );
    }

    @Test(priority = 2, description = "E2E: Login -> Purchase -> View order in profile")
    public void TC_Order_Purchase_E2E_Flow() {

        AuthData data = JsonUtils.readFromResource("testdata/auth.json", AuthData.class);

        
        LoginPage loginPage = new LoginPage().open();
        Assert.assertTrue(
                loginPage.loginExpectSuccess(data.validLoginEmail, data.validPassword),
                "Login failed"
        );
        Assert.assertTrue(driver().getCurrentUrl().contains("/profile"), "Not redirected to /profile");

        
        boolean ok = OrderFlow.completeOrderForJob(JOB_ID);
        Assert.assertTrue(ok, "Order flow failed: cannot complete order or cannot verify orders in profile.");
    }
}