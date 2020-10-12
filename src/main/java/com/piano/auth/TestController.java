package com.piano.auth;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;

@Controller("/test")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class TestController {
    @Operation(summary = "微信openid登录")
    @Get("/abc")
    public HttpResponse abc(){
        return HttpResponse.status(HttpStatus.OK);
    }
}
