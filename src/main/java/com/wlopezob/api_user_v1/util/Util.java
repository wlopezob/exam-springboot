package com.wlopezob.api_user_v1.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Formats a LocalDateTime object to a string using a predefined formatter.
     * If the input date is null, returns null.
     *
     * @param date the LocalDateTime to be formatted
     * @return the formatted date string, or null if input is null
     */
    public static String formatDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(formatter);
    }

    /**
     * Generates a Redis key by concatenating a prefix constant with the provided key.
     *
     * @param key The key string to be concatenated with the Redis prefix
     * @return A string representing the complete Redis key with prefix
     */
    public static final String getKeyRedis(String key) {
        return Constans.KEY_REDIS.concat("_").concat(key);
    }
}
