package com.bci.bci.user.domain.ports.in;

import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserLoginResponse;

public interface LoginUserPort {
    UserLoginResponse login(String token);
}