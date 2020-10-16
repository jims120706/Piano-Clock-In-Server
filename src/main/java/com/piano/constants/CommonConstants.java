package com.piano.constants;

import java.time.format.DateTimeFormatter;

public interface CommonConstants {
    DateTimeFormatter dateTimeFormatterMinute = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
