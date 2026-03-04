package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginAdminPage;
import pages.SellerPage;
import utils.BaseTest;

public class CreateGigTest extends BaseTest {
    private static final String Test_EMAIL = "hiem@gmail.com";
    private static final String Test_PASSWORD = "123456";
    @BeforeMethod
    public void loginBeforeEachTest(){

        LoginAdminPage loginPage = new LoginAdminPage(driver);
        loginPage.login(Test_EMAIL, Test_PASSWORD);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // verify login thành công
        Assert.assertTrue(
                loginPage.getCurrentUrl().contains("profile"),
                "Login khong thanh cong!"
        );
    }
    @Test
    public void testCreateGig() throws Exception {

        SellerPage sellerPage = new SellerPage(driver);

        sellerPage.openHomePage();
        sellerPage.clickBecomeSeller();
        Assert.assertTrue(
                sellerPage.getCurrentUrl().contains("seller"),
                "Chua chuyen sang trang seller!"
        );

        sellerPage.clickCreateNewGig();
        Assert.assertTrue(
                sellerPage.getCurrentUrl().contains("create"),
                "Chua vao trang create gig!"
        );
        // giả lập vào trang create gig trước

        sellerPage.fillCreateGigForm(
                "Automation Test Job",
                "Mo ta cong viec automation",
                "Mo ta ngan",
                "100",
                "5",
                "123",
                "5"
        );

        sellerPage.clickSave();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
   @Test
   public void testEditGig() throws Exception {

            SellerPage sellerPage = new SellerPage(driver);

            sellerPage.openHomePage();
            sellerPage.clickBecomeSeller();

            // vào list gig có sẵn
            sellerPage.goToGigList();

            // bấm edit gig đầu tiên
            sellerPage.clickEditGig();

            // verify vào trang edit
            Assert.assertTrue(
                    sellerPage.getCurrentUrl().contains("edit"),
                    "Chua vao trang edit!"
            );

            // update name
            sellerPage.updateNameJob("Updated Automation Job");

            sellerPage.clickSave();

            // verify quay lại list
            Assert.assertTrue(
                    sellerPage.getCurrentUrl().contains("seller"),
                    "Edit khong thanh cong!"
            );
        }
    @Test
    public void testDeleteGig() throws Exception {

        SellerPage sellerPage = new SellerPage(driver);

        sellerPage.openHomePage();
        sellerPage.clickBecomeSeller();

        // vào list gig
        sellerPage.goToGigList();

        // xóa gig đầu tiên
        sellerPage.clickDeleteGig();

        // confirm xóa
        sellerPage.confirmDelete();

        Thread.sleep(2000);

        // verify không còn gig cũ (hoặc verify message)
        Assert.assertFalse(
                sellerPage.isGigStillDisplayed(),
                "Gig chua bi xoa!"
        );
    }
    @Test
    public void testValidateRequiredField() throws Exception {

        SellerPage sellerPage = new SellerPage(driver);

        sellerPage.openHomePage();
        sellerPage.clickBecomeSeller();

        // vào trang create gig
        sellerPage.clickCreateNewGig();

        // không nhập gì hết

        sellerPage.clickSave();

        Thread.sleep(500);

        // verify có thông báo lỗi required
        Assert.assertTrue(
                sellerPage.isRequiredErrorDisplayed(),
                "Khong hien thi loi required!"
        );
    }

}
