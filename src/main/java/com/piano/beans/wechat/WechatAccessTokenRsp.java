package com.piano.beans.wechat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WechatAccessTokenRsp extends WechatBaseRsp {
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "expires_in")
    private int expiresTime;

    private int errcode;
    private String errmsg;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(int expiresTime) {
        this.expiresTime = expiresTime;
    }
}
