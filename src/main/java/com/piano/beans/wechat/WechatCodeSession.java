package com.piano.beans.wechat;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatCodeSession {
    String openid;
    String session_key;
    String unionid;
    int expires_in;
    int errcode;
    String errmsg;
}

