package com.piano.controllers;

import com.piano.beans.db.DailyCheck;
import com.piano.beans.db.UserInfo;
import com.piano.exception.DailyCheckException;
import com.piano.net.RespUtils;
import com.piano.services.DailyCheckService;
import com.piano.services.UserInfoService;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;

@Controller("/dailycheck")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class DailyCheckController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DailyCheckController.class);

    @Inject
    DailyCheckService dailycheckService;
    @Inject
    UserInfoService userInfoService;

    @Get("/hoursTotal")
    public HttpResponse hoursTotal(Authentication authentication) {
        UserInfo userInfo = getUserInfo(authentication);
        BigDecimal hours = dailycheckService.hoursTotal(userInfo);
        return RespUtils.SUCCESS(hours);
    }

    @Get("/pages")
    public HttpResponse pages(Authentication authentication,int index,int size) {
        UserInfo userInfo = getUserInfo(authentication);
        Page<DailyCheck> pages = dailycheckService.dailyCheckHoursPages(userInfo, Pageable.from(index,size));
        return RespUtils.SUCCESS(pages);
    }



    @Post("/commit")
    public HttpResponse commit(Authentication authentication, String startTimeStr, String endTimeStr) {
        UserInfo userInfo = getUserInfo(authentication);
        dailycheckService.commitDailyCheck(userInfo,startTimeStr,endTimeStr);
        return RespUtils.SUCCESS_EMPTY();
    }

    private UserInfo getUserInfo(Authentication authentication) {
        String userId = authentication.getName();
        Optional<UserInfo> userInfoOptional = userInfoService.findById(Integer.valueOf(userId));
        if(userInfoOptional.isEmpty()){
            throw new DailyCheckException("找不到该用户");
        }
        return userInfoOptional.get();
    }


    @Post("/supply")
    public HttpResponse supply(Authentication authentication, String date, long minutes) {
        UserInfo userInfo = getUserInfo(authentication);
        dailycheckService.supplyDailyCheck(userInfo,date,minutes);
        return RespUtils.SUCCESS_EMPTY();
    }
}
