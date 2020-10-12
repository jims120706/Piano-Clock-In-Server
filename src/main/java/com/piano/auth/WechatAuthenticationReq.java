package com.piano.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatAuthenticationReq {
    private String wxCode;
    private String openId;
}
