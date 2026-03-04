package com.NguyenHoangPhiTuyet.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public final class JsonUtils {

    private JsonUtils() {}

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T readFromResource(String resourcePath, Class<T> clazz) {
        try (InputStream is = JsonUtils.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new RuntimeException("Cannot find resource: " + resourcePath);
            }
            return MAPPER.readValue(is, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON: " + resourcePath, e);
        }
    }

    public static <T> T readFromFile(String filePath, Class<T> clazz) {
        try {
            return MAPPER.readValue(new java.io.File(filePath), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }
}
