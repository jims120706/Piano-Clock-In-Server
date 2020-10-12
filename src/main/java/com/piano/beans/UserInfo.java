package com.piano.beans;

import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.UserDetails;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfo {
    private String userName;
    private String openId;
    private String unionId;


}
