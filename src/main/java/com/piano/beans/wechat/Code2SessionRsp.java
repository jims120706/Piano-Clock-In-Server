package com.piano.beans.wechat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;


@Introspected
@JsonIgnoreProperties(ignoreUnknown = true)
public class Code2SessionRsp extends WechatBaseRsp {
    @JsonProperty(value = "openid")
    private String openId;
    @JsonProperty(value = "session_key")
    private String sessionKey;
    @JsonProperty(value = "unionid")
    private String unionId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
