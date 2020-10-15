package com.piano.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piano.beans.db.UserInfo;
import com.piano.beans.wechat.Code2SessionRsp;
import com.piano.constants.AuthConstants;
import com.piano.services.UserInfoService;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class WechatAuthenticationProvider implements AuthenticationProvider {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WechatAuthenticationProvider.class);
    private static final ObjectMapper sObjectMapper = new ObjectMapper();

    @Inject
    UserInfoService userInfoService;


    @Override
    public Publisher<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        String openId = authenticationRequest.getIdentity().toString();
        if(!openId.equalsIgnoreCase("aaa")){
            return Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.UNKNOWN));
        }
        return Flowable.just(new UserDetails("aaa", Collections.emptyList(), new HashMap<>()));
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> request, AuthenticationRequest<?, ?> authenticationRequest) {
        String userInfoStr = authenticationRequest.getIdentity().toString();
        logger.info("request params:{}",userInfoStr);

        try {
            UserInfo userInfo = sObjectMapper.readValue(Objects.requireNonNull(userInfoStr), UserInfo.class);
            String openId = userInfo.getOpenId();
            Optional<UserInfo> userInfoOptional = userInfoService.findByOpenId(openId);
            if(userInfoOptional.isEmpty()){
                logger.info("不存在的openId,创建新用户");
                userInfo = userInfoService.create(userInfo);
            }
            logger.info("返回数据openId:{},userName:{}",openId,userInfo.getNickName());
            return Flowable.just(new UserDetails(String.valueOf(userInfo.getId()), Collections.emptyList(), new HashMap<>()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.UNKNOWN));
        }
    }
    /**
     * wxcode获取sessionkey
     *
     * @param wxCode
     * @return
     */
    private Code2SessionRsp getCode2Session(String wxCode) {
       /* SystemToken appletToken = tokenService.getToken("Applet");
        String appletAppId = appletToken.getAppid();
        String appletSecret = appletToken.getAppSecret();
        String StrRsp = wechatApiClient.getAppletLogin(appletAppId, appletSecret, wxCode, "authorization_code").blockingGet();
        Code2SessionRsp code2SessionRsp = null;
        try {
            code2SessionRsp = mapper.readValue(StrRsp, Code2SessionRsp.class);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }

        return code2SessionRsp;*/
       return null;
    }


    private UserDetails generateUserDetails(int userId, String openId) {
        logger.info("generateUserDetails: {}, {}", userId, openId);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(AuthConstants.ATTR_OPENID, openId);
        UserDetails userDetails = new UserDetails(Integer.toString(userId), Collections.emptyList(), attributes);

        return userDetails;
    }
}
