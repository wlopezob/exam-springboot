package com.wlopezo.api_data_v1.util;

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

}
