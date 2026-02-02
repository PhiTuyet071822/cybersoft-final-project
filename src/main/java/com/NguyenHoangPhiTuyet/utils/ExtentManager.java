package com.NguyenHoangPhiTuyet.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.NguyenHoangPhiTuyet.constants.FrameworkConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentManager {

    private ExtentManager() {}

    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    private static ExtentReports createInstance() {
        ensureFolderExists(FrameworkConstants.REPORTS_FOLDER);

        String reportPath = Path.of(FrameworkConstants.REPORTS_FOLDER, "ExtentReport.html").toString();

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("CyberSoft Final - Selenium Java");

        ExtentReports ext = new ExtentReports();
        ext.attachReporter(spark);

        ext.setSystemInfo("Project", "CyberSoft Final Automation");
        ext.setSystemInfo("Browser", "Chrome");
        ext.setSystemInfo("Tester", "NguyenHoangPhiTuyet");

        return ext;
    }

    private static void ensureFolderExists(String folderName) {
        try {
            File folder = new File(folderName);
            if (!folder.exists()) {
                Files.createDirectories(folder.toPath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create folder: " + folderName, e);
        }
    }
}
