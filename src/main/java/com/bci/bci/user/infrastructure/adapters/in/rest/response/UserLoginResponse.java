package com.bci.bci.user.infrastructure.adapters.in.rest.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserLoginResponse extends UserCreatedResponse {
    private String name;
    private String email;
    private String password;
    private Set<UserPhoneResponse> phones;
}
