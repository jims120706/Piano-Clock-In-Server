package com.piano.beans.db;

import com.piano.beans.BaseBean;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.model.naming.NamingStrategies;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedEntity(value = "dailyCheck", namingStrategy = NamingStrategies.UpperCase.class)
public class DailyCheck extends BaseBean {
    private int hours;
    private int remarks;
    private int userId;
    private int voiceId;
    private int videoId;
}
