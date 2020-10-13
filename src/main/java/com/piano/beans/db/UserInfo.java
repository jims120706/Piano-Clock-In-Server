package com.piano.beans.db;

import com.piano.beans.BaseBean;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Transient;
import io.micronaut.data.model.naming.NamingStrategies;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedEntity(value="userInfo",namingStrategy = NamingStrategies.UpperCase.class)
public class UserInfo extends BaseBean {
    private String openId;
    private String nickName;
    private String avatarUrl;
    private int gender;
    private String province;
    private String city;
    private String country;
    @Transient
    private String language;
}
