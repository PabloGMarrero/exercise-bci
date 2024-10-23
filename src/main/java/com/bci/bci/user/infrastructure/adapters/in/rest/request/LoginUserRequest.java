package com.bci.bci.user.infrastructure.adapters.in.rest.request;

import lombok.Getter;

@Getter
public class LoginUserRequest {
    private String email;
    private String password;
}
