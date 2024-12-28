package com.wlopezob.api_user_v1.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String formatDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(formatter);
    }

    public static final String getKeyRedis(String key) {
        return Constans.KEY_REDIS.concat("_").concat(key);
    }

    @SneakyThrows
    public static <T> String toJson(T object) {
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(object);
    }
}
