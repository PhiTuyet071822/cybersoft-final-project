package com.NguyenHoangPhiTuyet.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.NguyenHoangPhiTuyet.pages.HomePage;
import utils.BaseTest;

public class SearchGigTest extends BaseTest {
    private static final String valid_keyword = "video";
    private static final String invalid_keyword = "girl";

    @Test
    public void testSearchGigPass(){
        // B1: tao doi tuong HomePage
        HomePage homePage = new HomePage(driver);

        // B2: mo trang home
        homePage.openHomePage();

        // B3: thuc hien search
        homePage.searchKeyword(valid_keyword);

        // B4: doi server xu ly
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // B5: verify URL dung
        String currentUrl = homePage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/result/" + valid_keyword),
                "URL khong dung sau khi search!");

        // B6: verify co ket qua tra ve
        Assert.assertTrue(homePage.isSearchResultDisplayed(),
                "Khong co ket qua tra ve!");
    }

    @Test
    public void testSearchGigFail(){
        // B1: tao doi tuong HomePage
        HomePage homePage = new HomePage(driver);

        // B2: mo trang home
        homePage.openHomePage();

        // B3: thuc hien search
        homePage.searchKeyword(invalid_keyword);

        // B4: doi server xu ly
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // B5: verify URL dung
        String currentUrl = homePage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/result/" + invalid_keyword),
                "URL khong dung sau khi search!");

        // Verify KHÔNG có kết quả
        Assert.assertFalse(homePage.isSearchResultDisplayed(),
                "Dang le khong co ket qua tra ve!");
    }
}
