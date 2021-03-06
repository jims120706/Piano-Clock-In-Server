package com.piano.controllers;

import com.piano.beans.db.DailyCheck;
import com.piano.beans.db.DailyCheckLog;
import com.piano.beans.db.UserInfo;
import com.piano.beans.request.SerchCondition;
import com.piano.beans.vo.HoursTotalVO;
import com.piano.exception.DailyCheckException;
import com.piano.net.RespUtils;
import com.piano.services.DailyCheckService;
import com.piano.services.UserInfoService;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller("/dailycheck")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class DailyCheckController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DailyCheckController.class);

    @Inject
    DailyCheckService dailycheckService;
    @Inject
    UserInfoService userInfoService;


    @Get("/userTotalHours")
    public HttpResponse userTotalHours(Authentication authentication) {
        UserInfo userInfo = getUserInfo(authentication);
        HoursTotalVO hoursTotalVO = dailycheckService.userDetailInfo(userInfo);
        return RespUtils.SUCCESS(hoursTotalVO);
    }


    @Get("/hoursToday")
    public HttpResponse hoursToday(Authentication authentication) {
        UserInfo userInfo = getUserInfo(authentication);
        BigDecimal hours = dailycheckService.hoursToday(userInfo);
        return RespUtils.SUCCESS(hours);
    }

    @Get("/hoursTotal")
    public HttpResponse hoursTotal(Authentication authentication) {
        UserInfo userInfo = getUserInfo(authentication);
        BigDecimal hoursTotal = dailycheckService.hoursTotal(userInfo);
        return RespUtils.SUCCESS(hoursTotal);
    }

    @Get("/hoursWeek")
    public HttpResponse hoursWeek(Authentication authentication) {
        UserInfo userInfo = getUserInfo(authentication);
        List<DailyCheck> weeksCheck = dailycheckService.hoursWeek(userInfo);
        return RespUtils.SUCCESS(weeksCheck);
    }

    @Get("/hoursMonths")
    public HttpResponse hoursMonths(Authentication authentication) {
        UserInfo userInfo = getUserInfo(authentication);
        List<DailyCheck> monthsCheck = dailycheckService.hoursMonth(userInfo);
        return RespUtils.SUCCESS(monthsCheck);
    }
    @Get("/hoursYear")
    public HttpResponse hoursYear(Authentication authentication) {
        UserInfo userInfo = getUserInfo(authentication);
        List<DailyCheck> yearsList = dailycheckService.hoursYear(userInfo);
        yearsList.forEach(x->x.setMonth(x.getCheckDate().getMonthValue()));
        Map<Integer, List<DailyCheck>> result = yearsList.stream().collect(Collectors.groupingBy(DailyCheck::getMonth));
        return RespUtils.SUCCESS(result);
    }

    @Post("/findByCondition")
    public HttpResponse findByCondition(Authentication authentication, @Body SerchCondition condition) {
        UserInfo userInfo = getUserInfo(authentication);
        Page<DailyCheck> monthsCheck = dailycheckService.findByCondition(userInfo,condition);
        return RespUtils.SUCCESS(monthsCheck);
    }

    @Post("/details")
    public HttpResponse details(Authentication authentication,int dailycheckId) {
        UserInfo userInfo = getUserInfo(authentication);
        List<DailyCheckLog> monthsCheck = dailycheckService.checkDetails(userInfo,dailycheckId);
        return RespUtils.SUCCESS(monthsCheck);
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

    @Post("/hoursCommit")
    public HttpResponse hoursCommit(Authentication authentication, String date,long minutes) {
        UserInfo userInfo = getUserInfo(authentication);
        dailycheckService.hoursCommitDailyCheck(userInfo,date,minutes);
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

    @Post("/batchSupply")
    public HttpResponse batchSupply(Authentication authentication, String startDate,String endDate, long minutes) {
        UserInfo userInfo = getUserInfo(authentication);
        dailycheckService.batchSupplyDailyCheck(userInfo,startDate,endDate,minutes);
        return RespUtils.SUCCESS_EMPTY();
    }
}
