package com.NguyenHoangPhiTuyet.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.NguyenHoangPhiTuyet.pages.HomePage;
import com.NguyenHoangPhiTuyet.pages.SearchResultPage;
import utils.BaseTest;

import java.util.List;

public class FilterGigTest extends BaseTest {
    private static final String keyword = "video";
    @Test
    public void testFilterByCategory() throws Exception {

        HomePage homePage = new HomePage(driver);
        SearchResultPage resultPage = new SearchResultPage(driver);

        homePage.openHomePage();
        homePage.searchKeyword(keyword);

        try { Thread.sleep(1500); } catch (Exception e){}

        // Lấy danh sách trước khi filter
        List<String> beforeFilter = resultPage.getGigTitles();
        try { Thread.sleep(3000); } catch (Exception e){}
        resultPage.selectCategory("Web Programing");

        try { Thread.sleep(3000); } catch (Exception e){}

        // Lấy danh sách sau khi filter
        List<String> afterFilter = resultPage.getGigTitles();
        System.out.println(driver.getCurrentUrl());
        // Verify danh sách thay đổi
        Assert.assertNotEquals(beforeFilter, afterFilter,
                "Filter category khong thay doi du lieu!");

    }

    @Test
    public void testFilterByPrice() throws Exception {

        HomePage homePage = new HomePage(driver);
        SearchResultPage resultPage = new SearchResultPage(driver);

        homePage.openHomePage();
        homePage.searchKeyword(keyword);

        try { Thread.sleep(3000); } catch (Exception e){}

        resultPage.selectPrice("0 - $55");

        try { Thread.sleep(2000); } catch (Exception e){}

        Assert.assertTrue(resultPage.isPriceValid(55),
                "Gia khong nam trong khoang 0 - 55$!");
    }

        @Test
    public void testClearAllFilter() throws Exception {

        HomePage homePage = new HomePage(driver);
        SearchResultPage resultPage = new SearchResultPage(driver);

        homePage.openHomePage();
        homePage.searchKeyword(keyword);

        try { Thread.sleep(3000); } catch (Exception e){}

        resultPage.selectCategory("Web Programing");
        resultPage.selectPrice("0 - 55");

        try { Thread.sleep(2000); } catch (Exception e){}

        resultPage.clickClearAll();

        try { Thread.sleep(2000); } catch (Exception e){}

        String currentUrl = driver.getCurrentUrl();

        Assert.assertFalse(currentUrl.contains("price"),
                "Clear All khong reset filter!");
    }
}
