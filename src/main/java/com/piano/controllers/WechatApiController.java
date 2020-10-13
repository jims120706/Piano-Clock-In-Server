package com.piano.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.piano.beans.wechat.WechatAccessTokenRsp;
import com.piano.beans.wechat.WechatCodeSession;
import com.piano.net.RespUtils;
import com.piano.services.WechatApiService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;


@Controller("/wechat")
@Secured(SecurityRule.IS_ANONYMOUS)
public class WechatApiController {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WechatApiController.class);

    @Inject
    WechatApiService wechatApiService;

    @Get("/getSessionKey")
    public HttpResponse getSessionKey(String code) throws JsonProcessingException {
        Optional<WechatCodeSession> session = wechatApiService.getSessionKey(code);
        if(session.isEmpty()){
            return RespUtils.BAD_REQUEST("系统异常");
        }
        return RespUtils.SUCCESS(session);
    }



    @Get("/getAccessToken")
    public HttpResponse getAccessToken() {
        Optional<WechatAccessTokenRsp> webAccessTokenRsp = wechatApiService.getOpenIdByWxCode();
        if(webAccessTokenRsp.isEmpty()){
            return RespUtils.BAD_REQUEST("系统异常");
        }
        return RespUtils.SUCCESS(webAccessTokenRsp);
    }

    @Get("/userInfo")
    public HttpResponse getUserInfoFromCode(String accessToken,String openId){
        Optional<String> userInfo = wechatApiService.getUserInfoWithoutFollowing(accessToken,openId);
        if(userInfo.isPresent()){
            return HttpResponse.status(HttpStatus.OK).body(userInfo.get());
        }else{
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
