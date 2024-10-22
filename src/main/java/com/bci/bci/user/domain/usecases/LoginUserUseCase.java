package com.bci.bci.user.domain.usecases;

import com.bci.bci.user.domain.ports.in.LoginUserPort;
import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserLoginResponse;
import org.springframework.stereotype.Service;

@Service
public class LoginUserUseCase implements LoginUserPort {

    @Override
    public UserLoginResponse login(String token) {
        return null;
    }
}
