package com.NguyenHoangPhiTuyet.tests;

import com.NguyenHoangPhiTuyet.utils.ExtentManager;
import com.NguyenHoangPhiTuyet.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final ExtentReports EXTENT = ExtentManager.getExtentReports();
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = EXTENT.createTest(result.getMethod().getMethodName());
        TEST.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        TEST.get().pass("Test PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String path = ScreenshotUtils.takeScreenshot(testName);

        TEST.get().fail(result.getThrowable());

        if (path != null) {
            try {
                TEST.get().addScreenCaptureFromPath(path);
            } catch (Exception e) {
                TEST.get().info("Could not attach screenshot: " + path);
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TEST.get().skip("Test SKIPPED");
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        EXTENT.flush();
        TEST.remove();
    }
}
