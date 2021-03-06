package com.piano.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piano.beans.wechat.WechatAccessTokenRsp;
import com.piano.beans.wechat.WechatBaseRsp;
import com.piano.beans.wechat.WechatCodeSession;
import com.piano.cache.LocalMapCache;
import com.piano.client.WechatApiClient;
import com.piano.constants.CacheConstants;
import com.piano.exception.WechatException;
import com.piano.exception.handler.WechatExceptionHandler;
import com.piano.utils.JacksonUtils;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;
import java.util.Optional;


@Singleton
public class WechatApiService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WechatApiService.class);

    @Inject
    private WechatApiClient wechatApiClient;

    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.appsecret}")
    private String appSecret;

    private final ObjectMapper mapper = new ObjectMapper();


    public Optional<WechatCodeSession> getSessionKey(String code) {
        HttpResponse resp = wechatApiClient.getAuthByWxcode(appId, appSecret, code);
        WechatCodeSession wechatCodeSession = JacksonUtils.fromJson(Objects.requireNonNull(resp.body()).toString(), WechatCodeSession.class);
        return Optional.ofNullable(wechatCodeSession);
    }


    public Optional<WechatAccessTokenRsp> getOpenIdByWxCode() {
        WechatAccessTokenRsp resp = wechatApiClient.getAccessToken("client_credential", appId, appSecret).blockingGet();
        return Optional.of(resp);
    }

    public WechatAccessTokenRsp getAccessToken() {
        String token = LocalMapCache.get(CacheConstants.ACCESS_TOKEN);
        if (!StringUtils.isEmpty(token)) {
            WechatAccessTokenRsp wechatAccessTokenRsp = JacksonUtils.fromJson(token, WechatAccessTokenRsp.class);
            return wechatAccessTokenRsp;
        }
        Optional<WechatAccessTokenRsp> webAccessTokenRsp = this.getOpenIdByWxCode();
        if (webAccessTokenRsp.isEmpty()) {
            throw new WechatException();
        }
        WechatAccessTokenRsp wechatAccessTokenRsp = webAccessTokenRsp.get();
        LocalMapCache.put(CacheConstants.ACCESS_TOKEN, JacksonUtils.toJson(wechatAccessTokenRsp));
        return wechatAccessTokenRsp;
    }


    public Optional<String> getUserInfoWithoutFollowing(String webAccessToken, String openId) {
        try {

            //微信API返回的JSON结构字符串，但是header里面写的是plain/text，这里需要自己转换成对象
            String wechatRspString = wechatApiClient.getUserInfoWithoutFollowing(webAccessToken, openId, "zh_CN").blockingGet();
            WechatBaseRsp rsp = mapper.readValue(wechatRspString, WechatBaseRsp.class);

            if (rsp.getErrorCode() == 0) {
                return Optional.of(wechatRspString);
            } else {
                logger.warn("Failed to get user information from wechat server: {} - {}", rsp.getErrorCode(), rsp.getErrMessage());
                return Optional.empty();
            }
        } catch (Exception ex) {
            logger.error("Failed to get user information: {}", ex.getMessage());
            logger.error(ExceptionUtils.getStackTrace(ex));

            return Optional.empty();
        }
    }
}
