package com.bci.bci.user.domain.usecases;

import com.bci.bci.config.security.JWTUtil;
import com.bci.bci.user.domain.models.Phone;
import com.bci.bci.user.domain.models.User;
import com.bci.bci.user.domain.ports.in.LoginUserPort;
import com.bci.bci.user.domain.ports.out.GetUserProvider;
import com.bci.bci.user.domain.ports.out.UpdateUserProvider;
import com.bci.bci.user.infrastructure.adapters.in.rest.request.LoginUserRequest;
import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserLoginResponse;
import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserPhoneResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class LoginUserUseCase implements LoginUserPort {

    private final GetUserProvider getUserProvider;
    private final UpdateUserProvider updateUserProvider;

    public LoginUserUseCase(GetUserProvider getUserProvider, UpdateUserProvider updateUserProvider) {
        this.getUserProvider = getUserProvider;
        this.updateUserProvider = updateUserProvider;
    }

    @Override
    public UserLoginResponse login(LoginUserRequest request, String token) {

        JWTUtil.generateToken(request.getEmail());

        var user = getUserProvider.getByEmail(request.getEmail()).orElseThrow(()-> new SecurityException("There was a error trying to get user."));
        var updateUser = User.builder()
                .id(user.getId())
                .created(user.getCreated())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .name(user.getName())
                .password(user.getPassword())
                .phones(user.getPhones())
                .lastLogin(LocalDateTime.now())
                //.version()
                .build();
        updateUserProvider.update(updateUser);

        return UserLoginResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phones(user.getPhones().stream().map(phone -> UserPhoneResponse.builder()
                        .number(phone.getNumber())
                        .cityCode(phone.getCityCode())
                        .countryCode(phone.getCountryCode())
                        .build()).collect(Collectors.toSet()))
                .build();
    }
}
