package com.bci.bci.user.infrastructure.adapters.in.rest.response;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@SuperBuilder
public class UserLoginResponse extends UserCreatedResponse{
    private String name;
    private String email;
    private String password;
    private Set<UserPhoneResponse> phones;
}
