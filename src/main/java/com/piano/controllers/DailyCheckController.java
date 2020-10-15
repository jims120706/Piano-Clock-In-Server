package com.piano.controllers;

import com.piano.net.RespUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

@Controller("/wechat")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class DailyCheckController {

    @Post("/uploadDailyCheck")
    public HttpResponse uploadDailyCheck(Authentication authentication,String startTime,String endTime) {
        String userId = authentication.getName();
        return RespUtils.SUCCESS_EMPTY();
    }
}
