package com.piano.exception.handler;

import com.piano.exception.DailyCheckException;
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
@Requires(classes = {DailyCheckException.class})
public class DailyCheckExceptionHandler implements ExceptionHandler<DailyCheckException, HttpResponse> {
    @Override
    public HttpResponse handle(HttpRequest request, DailyCheckException exception) {
        return RespUtils.BAD_REQUEST(exception.getMessage());
    }
}
