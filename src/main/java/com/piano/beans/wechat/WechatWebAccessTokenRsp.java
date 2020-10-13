package com.piano.beans.wechat;

import lombok.Data;

@Data
public class WechatWebAccessTokenRsp extends WechatBaseRsp {
    private String accessToken;
    private int expiresIn;
    private String refreshToken;
    private String openId;
    private String scope;
    private String unionId;
    private String wxCode;
}
