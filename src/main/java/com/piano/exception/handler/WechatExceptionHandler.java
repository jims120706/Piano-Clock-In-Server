package com.piano.exception.handler;

import com.piano.exception.WechatException;
import com.piano.net.RespUtils;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {WechatException.class})
public class WechatExceptionHandler implements ExceptionHandler<WechatException, HttpResponse> {
    @Override
    public HttpResponse handle(HttpRequest request, WechatException exception) {
        return RespUtils.BAD_REQUEST("微信接口异常");
    }
}
