package com.piano.net;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="CommonRsp")
public class CommonRsp<TPayload> {
    private int code;
    private String msg;
    private TPayload item;

    public CommonRsp(int code, String msg, TPayload item) {
        this.code = code;
        this.msg = msg;
        this.item = item;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TPayload getItem() {
        return item;
    }

    public void setItem(TPayload item) {
        this.item = item;
    }
}
