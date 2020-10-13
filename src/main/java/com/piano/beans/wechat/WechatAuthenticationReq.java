package com.piano.beans.wechat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatAuthenticationReq {
    private String wxCode;
    private String openId;
}
