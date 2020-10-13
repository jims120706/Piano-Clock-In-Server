package com.piano.client;

import com.piano.beans.wechat.WechatAccessTokenRsp;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;

@Client("https://api.weixin.qq.com")
public interface WechatApiClient {
    @Get(value = "/sns/oauth2/access_token")
    Single<String> getWebAccessToken(@QueryValue String appid, @QueryValue String secret, @QueryValue String code,
                                     @QueryValue String grant_type);
    @Get(value = "/cgi-bin/token")
    Single<WechatAccessTokenRsp> getAccessToken(@QueryValue String grant_type, @QueryValue String appid, @QueryValue String secret);

    @Get(value = "/sns/jscode2session?appid={appid}&secret={secret}&js_code={jscode}&grant_type=authorization_code")
    HttpResponse getAuthByWxcode(@PathVariable String appid, @PathVariable String secret, @PathVariable String jscode);

    @Get(value = "/sns/userinfo")
    Single<String> getUserInfoWithoutFollowing(@QueryValue String access_token, @QueryValue String openid, @QueryValue String lang);

    @Get(value = "/cgi-bin/user/info")
    Single<String> getDetailUserInfo(@QueryValue String access_token, @QueryValue String openid, @QueryValue String lang);
}
