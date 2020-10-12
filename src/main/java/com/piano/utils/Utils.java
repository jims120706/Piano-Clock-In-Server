package com.piano.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;

public class Utils {
    public static String getHintTag() {
        return RandomStringUtils.random(8, true, true);
    }
}
