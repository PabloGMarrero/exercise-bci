package com.bci.bci.user.domain.usecases;

import com.bci.bci.user.domain.exceptions.CreateUserException;
import com.bci.bci.user.domain.ports.in.CreateUserPort;
import com.bci.bci.user.domain.ports.out.AuthenticationProvider;
import com.bci.bci.user.domain.ports.out.CreateUserProvider;
import com.bci.bci.user.domain.ports.out.GetUserProvider;
import com.bci.bci.user.infrastructure.adapters.in.rest.request.CreateUserRequest;
import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserCreatedResponse;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase implements CreateUserPort {

    private final CreateUserProvider createUserProvider;
    private final GetUserProvider getUserProvider;
    private final AuthenticationProvider authenticationProvider;


    public CreateUserUseCase(CreateUserProvider createUserProvider, GetUserProvider getUserProvider, AuthenticationProvider authenticationProvider) {
        this.createUserProvider = createUserProvider;
        this.getUserProvider = getUserProvider;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public UserCreatedResponse create(CreateUserRequest request) throws CreateUserException {
        var response = getUserProvider.getByEmail(request.getEmail());

        if (response != null) {
            throw new CreateUserException(CreateUserException.ALREADY_EXIST);
        }

        //TODO encodear pass
        //request.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        var user = createUserProvider.createUser(request);

        var token = authenticationProvider.generateToken(user);

        return UserCreatedResponse.builder()
                .id(user.getId())
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .token(token)
                .isActive(user.getIsActive())
                .build();
    }
}
