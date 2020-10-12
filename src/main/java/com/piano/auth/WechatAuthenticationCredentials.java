package com.piano.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.security.authentication.AuthenticationRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class WechatAuthenticationCredentials implements AuthenticationRequest<String, String> {
    final String secret = "";
    @NotBlank
    @NotNull
    private String openId;
    @Override
    public String getIdentity() {
        return openId;
    }

    @Override
    public String getSecret() {
        return secret;
    }

}
