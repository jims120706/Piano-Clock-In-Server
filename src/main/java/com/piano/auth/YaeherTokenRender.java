/*
package com.piano.auth;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpHeaderValues;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.security.token.jwt.render.BearerTokenRenderer;
import io.micronaut.security.token.jwt.render.TokenRenderer;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Singleton
@Slf4j
@Replaces(bean = BearerTokenRenderer.class)
public class YaeherTokenRender implements TokenRenderer {
    private final String BEARER_TOKEN_TYPE = HttpHeaderValues.AUTHORIZATION_PREFIX_BEARER;

    @Override
    public AccessRefreshToken render(Integer expiresIn, String accessToken, String refreshToken) {
        return new AccessRefreshToken(accessToken, refreshToken, BEARER_TOKEN_TYPE, expiresIn);
    }

    @Override
    public AccessRefreshToken render(UserDetails userDetails, Integer expiresIn, String accessToken, String refreshToken) {
        // 这里有问题，有key有值，值就是null，就不是默认值
        //String openId = userDetails.getAttributes("role", "username").getOrDefault(AuthConstants.ATTR_OPENID, "UNKNOWN").toString();
        Object openId = userDetails.getAttributes("role", "username").get(AuthConstants.ATTR_OPENID);
        if (openId == null) {
            openId = "UNKNOWN";
            log.error("unkown JWT , userId:{}", userDetails.getUsername());
        }
        return new BearerAccessRefreshToken(openId.toString(), userDetails.getRoles(), expiresIn, accessToken, refreshToken, BEARER_TOKEN_TYPE);
    }
}
*/
