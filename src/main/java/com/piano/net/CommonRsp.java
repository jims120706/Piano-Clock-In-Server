package com.piano.net;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(name="CommonRsp")
public class CommonRsp<TPayload> {
    @AllArgsConstructor()
    @Getter
    @Setter
    @Schema(description = "返回体")
    class PayloadWrapper{
        private int code;
        private String msg;
        private TPayload item;
    }

    @JsonProperty(value = "result")
    protected PayloadWrapper payloadWrapper;

    public CommonRsp(int code, String msg, TPayload item){
        payloadWrapper = new PayloadWrapper(code, msg, item);
    }

    @JsonIgnore
    public int getCode() {
        return payloadWrapper.code;
    }

    @JsonIgnore
    public void setCode(int code) {
        payloadWrapper.code = code;
    }

    @JsonIgnore
    public String getMsg() {
        return payloadWrapper.msg;
    }

    @JsonIgnore
    public void setMsg(String msg) {
        payloadWrapper.msg = msg;
    }

    @JsonIgnore
    public TPayload getItem() {
        return payloadWrapper.item;
    }

    @JsonIgnore
    public void setItem(TPayload item) {
        payloadWrapper.item = item;
    }

    @Override
    public String toString() {
        return String.format("{code: %d, msg: %s}", payloadWrapper.code, payloadWrapper.msg);
    }
}
