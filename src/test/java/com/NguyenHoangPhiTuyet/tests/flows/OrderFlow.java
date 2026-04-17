package com.NguyenHoangPhiTuyet.tests.flows;

import com.NguyenHoangPhiTuyet.pages.JobDetailOrderPage;
import com.NguyenHoangPhiTuyet.pages.ProfileOrdersPage;

public class OrderFlow {

    /**
     * Assumption: user đã login trước khi gọi.
     * Return true nếu:
     * - Click submit
     * - Có toast success
     * - Profile có đơn hiển thị
     */
    public static boolean completeOrderForJob(int jobId) {
        JobDetailOrderPage job = new JobDetailOrderPage().openById(jobId);

        if (!job.isJobDetailVisible()) return false;

        job.clickSubmitOrder();

        String toastText = job.waitAndGetToastText();
        boolean success = job.isToastSuccessPresent();

        
        job.waitToastDisappear();

        if (!success) return false;

        job.goToProfile();

        ProfileOrdersPage profile = new ProfileOrdersPage();
        if (!profile.isOrdersSectionVisible()) return false;

        return profile.getOrderedItemsCount() > 0;
    }
}