package com.bci.bci.user.domain.usecases;

import com.bci.bci.config.security.JWTUtil;
import com.bci.bci.user.domain.exceptions.CreateUserException;
import com.bci.bci.user.domain.ports.in.CreateUserPort;
import com.bci.bci.user.domain.ports.out.CreateUserProvider;
import com.bci.bci.user.domain.ports.out.GetUserProvider;
import com.bci.bci.user.infrastructure.adapters.in.rest.request.CreateUserRequest;
import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserCreatedResponse;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase implements CreateUserPort {

    private final CreateUserProvider createUserProvider;
    private final GetUserProvider getUserProvider;

    public CreateUserUseCase(CreateUserProvider createUserProvider, GetUserProvider getUserProvider) {
        this.createUserProvider = createUserProvider;
        this.getUserProvider = getUserProvider;
    }

    @Override
    public UserCreatedResponse create(CreateUserRequest request) throws CreateUserException {
        var response = getUserProvider.getByEmail(request.getEmail());

        if (response.isPresent()) {
            throw new CreateUserException(CreateUserException.ALREADY_EXIST);
        }

        var user = createUserProvider.createUser(request);
        return UserCreatedResponse.builder()
                .id(user.getId())
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .token(JWTUtil.generateToken(request.getEmail()))
                .isActive(user.getIsActive())
                .build();
    }
}
