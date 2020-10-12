package com.piano.net;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.validation.constraints.Null;

public class RespUtils {
    private static final Logger sLOG = LoggerFactory.getLogger(RespUtils.class);

    private static HttpResponse errorResp(@NonNull HttpStatus status) {
        return errorResp(status, null);
    }

    private static HttpResponse errorResp(@NonNull HttpStatus status, @Nullable String msg) {
        if(StringUtils.isEmpty(msg)) {
            msg = status.getReason();
        }
        sLOG.warn("http error: {}, {}", status.getCode(), msg);
        return HttpResponse.status(HttpStatus.OK).body(new CommonRsp<>(status.getCode(), msg, null));
    }

    public static HttpResponse BAD_REQUEST(@Null String msg) {
        return errorResp(HttpStatus.BAD_REQUEST, msg);
    }
    public static HttpResponse NOT_ACCEPTABLE(@Null String msg) {
        return errorResp(HttpStatus.NOT_ACCEPTABLE, msg);
    }

    public static HttpResponse UNPROCESSABLE_ENTITY(@Null String msg) {
        return errorResp(HttpStatus.UNPROCESSABLE_ENTITY, msg);
    }

    public static HttpResponse NOT_FOUND(@Null String msg) {
        return errorResp(HttpStatus.NOT_FOUND, msg);
    }

    public static HttpResponse UNAUTHORIZED(@Null String msg) {
        return errorResp(HttpStatus.UNAUTHORIZED, msg);
    }

    public static HttpResponse NO_CONTENT(String msg) {
        return errorResp(HttpStatus.NO_CONTENT, msg);
    }

    public static HttpResponse INTERNAL_SERVER_ERROR() {
        return errorResp(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <TPayload> HttpResponse SUCCESS(TPayload payload) {
        sLOG.info("resp success");
        return HttpResponse.status(HttpStatus.OK).body(new CommonRsp<>(HttpStatus.OK.getCode(), HttpStatus.OK.getReason(), payload));
    }

    public static HttpResponse SUCCESS_EMPTY() {
        sLOG.info("resp success");
        return HttpResponse.status(HttpStatus.OK).body(new CommonRsp<>(HttpStatus.OK.getCode(), HttpStatus.OK.getReason(), null));
    }

}
