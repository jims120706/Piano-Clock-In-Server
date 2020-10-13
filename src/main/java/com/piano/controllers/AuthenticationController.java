package com.piano.controllers;

import com.piano.beans.wechat.WechatAuthenticationCredentials;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.*;
import io.micronaut.security.event.LoginFailedEvent;
import io.micronaut.security.event.LoginSuccessfulEvent;
import io.micronaut.security.handlers.LoginHandler;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.token.jwt.bearer.AccessRefreshTokenLoginHandler;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;

import javax.inject.Inject;


@Controller("/authentication")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AuthenticationController{
    protected final Authenticator authenticator;
    @Inject
    protected final LoginHandler loginHandler;
    protected final ApplicationEventPublisher eventPublisher;

    public AuthenticationController(Authenticator authenticator,
                                    AccessRefreshTokenLoginHandler loginHandler,
                                    ApplicationEventPublisher eventPublisher) {
        this.authenticator = authenticator;
        this.loginHandler = loginHandler;
        this.eventPublisher = eventPublisher;
    }


    @Operation(summary = "微信openid登录")
    @Post("/login")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public Single<HttpResponse> loginWithOpenId(@Body WechatAuthenticationCredentials authenticationCredentials, HttpRequest<?> request){
        return doAuthentication(authenticationCredentials, request);
    }

    private Single<HttpResponse> doAuthentication(AuthenticationRequest authenticationRequest, HttpRequest<?> request){
        Flowable<AuthenticationResponse> authenticationResponseFlowable = Flowable.fromPublisher(authenticator.authenticate(request,authenticationRequest));
        return authenticationResponseFlowable.map(authenticationResponse -> {
            if (authenticationResponse.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authenticationResponse;
                eventPublisher.publishEvent(new LoginSuccessfulEvent(userDetails));
                return loginHandler.loginSuccess(userDetails, request);
            } else {
                AuthenticationFailed authenticationFailed = (AuthenticationFailed) authenticationResponse;
                eventPublisher.publishEvent(new LoginFailedEvent(authenticationFailed));
                if(authenticationFailed.getReason() == AuthenticationFailureReason.ACCOUNT_EXPIRED){
                    return HttpResponse.status(HttpStatus.PAYMENT_REQUIRED);
                }else{
                    return HttpResponse.status(HttpStatus.UNAUTHORIZED);
                }
            }
        }).first(HttpResponse.status(HttpStatus.UNAUTHORIZED));
    }
}
