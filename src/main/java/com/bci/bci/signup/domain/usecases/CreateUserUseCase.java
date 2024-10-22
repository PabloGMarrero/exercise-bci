package com.bci.bci.signup.domain.usecases;

import com.bci.bci.signup.domain.ports.out.CreateUserProvider;
import com.bci.bci.signup.domain.ports.in.CreateUserPort;
import com.bci.bci.signup.infrastructure.adapters.in.rest.request.CreateUserRequest;
import com.bci.bci.signup.infrastructure.adapters.in.rest.response.UserCreatedResponse;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase implements CreateUserPort {

    private final CreateUserProvider createUserProvider;

    public CreateUserUseCase(CreateUserProvider createUserProvider) {
        this.createUserProvider = createUserProvider;
    }

    @Override
    public UserCreatedResponse create(CreateUserRequest request) {

        var user = createUserProvider.createUser(request);
        return UserCreatedResponse.builder()
                .id(user.getId())
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .token("")
                .isActive(user.getIsActive())
                .build();
    }
}
