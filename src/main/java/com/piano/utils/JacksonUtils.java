package com.piano.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piano.beans.wechat.WechatAccessTokenRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JacksonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> tClass){
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("read json string failure: ", e);
        }
        return null;
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("to json error:{}", obj);
        }
        return null;
    }
}
