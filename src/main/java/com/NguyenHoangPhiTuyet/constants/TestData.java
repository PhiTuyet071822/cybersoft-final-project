package com.NguyenHoangPhiTuyet.constants;

public final class TestData {

    private TestData() {}

    public static String uniqueEmail() {
        return "tet" + System.currentTimeMillis() + "@mailinator.com";
    }

    public static final String VALID_PASSWORD = "Tet@12345";
    public static final String VALID_NAME = "Phi Tuyet";
    public static final String VALID_PHONE = "0862173946";
    public static final String VALID_BIRTHDAY = "30/07/1999";
    public static final String EXISTING_EMAIL = "hoangphituyet_nguyen@gmail.com";

}
