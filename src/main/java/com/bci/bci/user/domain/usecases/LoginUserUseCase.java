package com.bci.bci.user.domain.usecases;

import com.bci.bci.user.domain.ports.in.LoginUserPort;
import com.bci.bci.user.domain.ports.out.AuthenticationProvider;
import com.bci.bci.user.domain.ports.out.GetUserProvider;
import com.bci.bci.user.domain.ports.out.UpdateUserProvider;
import com.bci.bci.user.infrastructure.adapters.in.rest.request.LoginUserRequest;
import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserLoginResponse;
import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserPhoneResponse;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LoginUserUseCase implements LoginUserPort {

    private final GetUserProvider getUserProvider;
    private final UpdateUserProvider updateUserProvider;
    private final AuthenticationProvider authenticationProvider;

    public LoginUserUseCase(GetUserProvider getUserProvider, UpdateUserProvider updateUserProvider, AuthenticationProvider authenticationProvider) {
        this.getUserProvider = getUserProvider;
        this.updateUserProvider = updateUserProvider;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public UserLoginResponse login(LoginUserRequest request) {
        authenticationProvider.validateUser(request.getEmail(), request.getPassword());

        var user = getUserProvider.getByEmail(request.getEmail());
        var token = authenticationProvider.generateToken(user);

        user.updateLastLogin();

        var updatedUser = updateUserProvider.update(user);

        return UserLoginResponse.builder()
                .id(user.getId())
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .password(updatedUser.getPassword())
                .phones(user.getPhones().stream().map(phone -> UserPhoneResponse.builder()
                        .number(phone.getNumber())
                        .cityCode(phone.getCityCode())
                        .countryCode(phone.getCountryCode())
                        .build()).collect(Collectors.toSet()))
                .created(updatedUser.getCreated())
                .lastLogin(updatedUser.getLastLogin())
                .isActive(updatedUser.getIsActive())
                .token(token)
                .build();
    }
}
