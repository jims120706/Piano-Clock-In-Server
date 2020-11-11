package com.piano.services;

import com.piano.beans.db.DailyCheck;
import com.piano.beans.db.DailyCheckLog;
import com.piano.beans.db.UserInfo;
import com.piano.beans.request.SerchCondition;
import com.piano.beans.vo.HoursTotalVO;
import com.piano.constants.CommonConstants;
import com.piano.exception.DailyCheckException;
import com.piano.repositories.DailyCheckLogRepository;
import com.piano.repositories.DailyCheckRepository;
import com.piano.utils.JacksonUtils;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class DailyCheckService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DailyCheckService.class);

    @Inject
    DailyCheckRepository dailyCheckRepository;
    @Inject
    DailyCheckLogRepository dailyCheckLogRepository;


    public List<DailyCheckLog> checkOverlap(int userId, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime today){
        List<DailyCheckLog> checkLogList = dailyCheckLogRepository.findByUserIdAndCreateTimeBetween(userId,today,today.plusDays(1));
        List<DailyCheckLog> collect = checkLogList.stream().filter(x -> (startTime.isBefore(x.getEndTime().minusSeconds(1)) && startTime.isAfter(x.getStartTime().minusSeconds(1))) ||
                (endTime.isBefore(x.getEndTime().plusSeconds(1)) && endTime.isAfter(x.getStartTime().plusSeconds(1)))).collect(Collectors.toList());
        return collect;
    }


    @Transactional
    public void commitDailyCheck(UserInfo userInfo, String startTimeStr, String endTimeStr){
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, CommonConstants.dateTimeFormatterMinute);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, CommonConstants.dateTimeFormatterMinute);
        LocalDateTime today = startTime.toLocalDate().atStartOfDay();
        List<DailyCheckLog> dailyCheckLogs = checkOverlap(userInfo.getId(), startTime, endTime, today);
        if(dailyCheckLogs.size()>0){
            throw new DailyCheckException("打卡时间重叠,请重新选择时间:"+ JacksonUtils.toJson(dailyCheckLogs));
        }
        Duration duration = java.time.Duration.between(startTime, endTime);
        long minutes = duration.toMinutes();
        BigDecimal hours = new BigDecimal(minutes).divide(new BigDecimal(60), 2,RoundingMode.HALF_UP);
        logger.info("用户{}本次打卡时长:{}分,累计:{}小时",userInfo.getNickName(),minutes,hours);
        Optional<DailyCheck> todayCheckOptional = dailyCheckRepository.findByUserIdAndCheckDateEquals(userInfo.getId(), today);
        DailyCheck dailyCheck = new DailyCheck();
        DailyCheckLog checkLog = new DailyCheckLog();
        if(todayCheckOptional.isEmpty()){
            dailyCheck.setUserId(userInfo.getId());
            dailyCheck.setCheckDate(today);
            dailyCheck.setHours(hours);
            dailyCheckRepository.save(dailyCheck);
        }else{
            dailyCheck = todayCheckOptional.get();
            //时长累加
            BigDecimal newHours = dailyCheck.getHours().add(hours);
            dailyCheck.setHours(newHours);
            dailyCheckRepository.update(dailyCheck);
        }
        checkLog.setDailyCheckId(dailyCheck.getId());
        checkLog.setStartTime(startTime);
        checkLog.setEndTime(endTime);
        checkLog.setUserId(userInfo.getId());
        checkLog.setHours(hours);
        dailyCheckLogRepository.save(checkLog);
    }

    @Transactional
    public void supplyDailyCheck(UserInfo userInfo, String dateStr, long minutes) {
        LocalDateTime dateTime = LocalDate.parse(dateStr, CommonConstants.dateFormatter).atStartOfDay();
        if(dateTime.isAfter(dateTime)){
            throw new DailyCheckException("只能补过去的时间");
        }
        Optional<DailyCheck> todayCheckOptional = dailyCheckRepository.findByUserIdAndCheckDateEquals(userInfo.getId(), dateTime);
        BigDecimal hours = new BigDecimal(minutes).divide(new BigDecimal(60), 2,RoundingMode.HALF_UP);

        DailyCheck dailyCheck = new DailyCheck();
        if(todayCheckOptional.isEmpty()){
            dailyCheck.setUserId(userInfo.getId());
            dailyCheck.setHours(hours);
            dailyCheck.setCheckDate(dateTime);
            dailyCheckRepository.save(dailyCheck);
        }else{
            dailyCheck = todayCheckOptional.get();
            dailyCheck.setHours(dailyCheck.getHours().add(hours));
            dailyCheckRepository.update(dailyCheck);
        }
        DailyCheckLog checkLog = new DailyCheckLog();
        checkLog.setDailyCheckId(dailyCheck.getId());
        checkLog.setStartTime(dateTime);
        checkLog.setEndTime(dateTime);
        checkLog.setUserId(userInfo.getId());
        //补卡类型为2
        checkLog.setType(2);
        checkLog.setHours(hours);
        dailyCheckLogRepository.save(checkLog);
    }

    public HoursTotalVO userDetailInfo(UserInfo userInfo){
        HoursTotalVO hoursTotalVO = new HoursTotalVO();
        hoursTotalVO.setToday(this.hoursToday(userInfo));
        hoursTotalVO.setWeek(this.hoursWeek(userInfo));
        hoursTotalVO.setMonth(this.hoursMonth(userInfo));
        hoursTotalVO.setTotal(this.hoursTotal(userInfo));
        return hoursTotalVO;
    }

    public BigDecimal hoursTotal(UserInfo userInfo) {
        return dailyCheckRepository.sumHoursByUserId(userInfo.getId()).orElse(BigDecimal.ZERO);
    }

    public Page<DailyCheck> dailyCheckHoursPages(UserInfo userInfo, Pageable from) {
        return dailyCheckRepository.findByUserIdOrderByCheckDateDesc(userInfo.getId(),from);
    }

    public List<DailyCheck> hoursWeek(UserInfo userInfo) {
        LocalDateTime startTime = LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();
        return dailyCheckRepository.findByUserIdAndCheckDateBetweenOrderByCheckDateDesc(userInfo.getId(),startTime,endTime);
    }

    public List<DailyCheck> hoursMonth(UserInfo userInfo) {
        LocalDateTime startTime = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();
        return dailyCheckRepository.findByUserIdAndCheckDateBetweenOrderByCheckDateDesc(userInfo.getId(),startTime,endTime);
    }

    public Page<DailyCheck> findByCondition(UserInfo userInfo, SerchCondition condition) {
        return dailyCheckRepository.findByUserIdAndCheckDateBetweenOrderByCheckDateDesc(userInfo.getId(),condition.getStartTime(),condition.getEndTime(),Pageable.from(condition.getIndex(),condition.getSize()));
    }

    public List<DailyCheckLog> checkDetails(UserInfo userInfo, int dailyCheckId) {
        return dailyCheckLogRepository.findByUserIdAndDailyCheckId(userInfo.getId(),dailyCheckId);
    }

    public BigDecimal hoursToday(UserInfo userInfo) {
        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();
        List<DailyCheckLog> byUserIdAndStartTimeBetween = dailyCheckLogRepository.findByUserIdAndStartTimeBetween(userInfo.getId(), startTime, endTime);
        BigDecimal bigDecimal = new BigDecimal(0);
        for (DailyCheckLog x : byUserIdAndStartTimeBetween) {
            bigDecimal = bigDecimal.add(x.getHours());
        }
        return bigDecimal;
    }
}
