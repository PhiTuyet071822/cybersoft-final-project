package utils;

import com.NguyenHoangPhiTuyet.utils.ExtendReportManager;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver; //Quan he cha con xai protected
    protected ExtentTest extentTest;

//    ham khoi tao extent report truoc khi chay tat ca cac test
    @BeforeSuite
    public void setUpSuite(){
        ExtendReportManager.getInstance();
    }

    @AfterSuite
    public void tearDownSuite(){
        ExtendReportManager.flushReport();
    }

//    Tao ham khoi tao moi truong test
    @BeforeMethod
    public void setUp(ITestResult result){
//        tao test case trong report
        String testName = result.getMethod().getMethodName();
        String testDescription = result.getMethod().getDescription();
        if(testDescription == null || testDescription.isEmpty()){
            testDescription = "test case" + testName;
        }
        extentTest = ExtendReportManager.createTest(testName, testDescription);
        extentTest.info("Bat dau test case: "+testName);

//        B1: cau hinh Chromedriver
        WebDriverManager.chromedriver().setup();
        extentTest.info("Chrome Driver Setup");
//        B2: cau hinh cac tuy chon
        extentTest.info("Cau hinh chrome full screen");
        ChromeOptions options = new ChromeOptions();
//        Mo chrome o che do full man hinh
        options.addArguments("--start-maximized");
//        B3: khoi tao Chrome driver
        extentTest.info("Khoi tao ChromeDriver:");
        driver = new ChromeDriver(options);
//        B4: setting thoi gian doi khoi tao Chrome
//        neu chrome tao som hon 10s => tiep tuc ngay
//        neu chrome tao lau hon 10s => bao loi error
        extentTest.info("Setup implicit wait 10s");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

//    Ham clear moi truong test sau khi test xong 1 test case
    @AfterMethod
    public void tearDown(ITestResult result){
//        kiem tra ket qua va luu vao trong report
        if(result.getStatus() == ITestResult.SUCCESS){
            extentTest.pass("Test case passed:" + result.getMethod().getMethodName());
        }

        if(result.getStatus() == ITestResult.FAILURE){
            extentTest.fail("Test case failed:" + result.getMethod().getMethodName());
            if(result.getThrowable()!=null){
                extentTest.fail(result.getThrowable());
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }




}
