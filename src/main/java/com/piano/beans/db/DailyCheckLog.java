package com.piano.beans.db;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.piano.beans.BaseBean;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.model.naming.NamingStrategies;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedEntity(value = "dailyCheckLog",namingStrategy = NamingStrategies.UpperCase.class)
public class DailyCheckLog extends BaseBean {
    private int userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}