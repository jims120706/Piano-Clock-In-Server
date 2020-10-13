package com.piano.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.piano.net.RespUtils;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {JsonProcessingException.class})
public class JsonProcessingExceptionHandler implements ExceptionHandler<JsonProcessingException, HttpResponse> {
    @Override
    public HttpResponse handle(HttpRequest request, JsonProcessingException exception) {
        return RespUtils.BAD_REQUEST("微信接口数据格式转换异常");
    }
}
