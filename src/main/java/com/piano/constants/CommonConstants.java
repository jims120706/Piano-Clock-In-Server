package com.piano.constants;

import java.time.format.DateTimeFormatter;

public interface CommonConstants {
    DateTimeFormatter dateTimeFormatterMinute = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    int DAYLY_CHECK_TYPE_HOURS = 2;
    int DAYLY_CHECK_TYPE_START_END = 0;
    int DAYLY_CHECK_TYPE_SUPPLY = 1;
}
