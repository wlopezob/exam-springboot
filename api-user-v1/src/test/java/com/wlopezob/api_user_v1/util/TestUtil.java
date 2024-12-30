package com.wlopezob.api_user_v1.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtil {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static <T> T loadJsonFromFile(String filePath, Class<T> valueType) {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return objectMapper.readValue(content, valueType);
    }

    @SneakyThrows
    public static <T> T loadJsonFromResource(String resourcePath, Class<T> valueType) {
        objectMapper.registerModule(new JavaTimeModule());
        ClassLoader classLoader = TestUtil.class.getClassLoader();
        File file = new File(classLoader.getResource(resourcePath).getFile());
        return objectMapper.readValue(file, valueType);
    }
}
