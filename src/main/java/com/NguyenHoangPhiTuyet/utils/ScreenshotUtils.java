package com.NguyenHoangPhiTuyet.utils;

import com.NguyenHoangPhiTuyet.constants.FrameworkConstants;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ScreenshotUtils {

    private ScreenshotUtils() {}

    public static String takeScreenshot(String testName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) return null;

        try {
            ensureFolderExists(FrameworkConstants.SCREENSHOTS_FOLDER);

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String fileName = testName + "_" + System.currentTimeMillis() + ".png";
            Path dest = Path.of(FrameworkConstants.SCREENSHOTS_FOLDER, fileName);

            Files.copy(src.toPath(), dest);
            return dest.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static void ensureFolderExists(String folderName) throws IOException {
        File folder = new File(folderName);
        if (!folder.exists()) {
            Files.createDirectories(folder.toPath());
        }
    }
}
