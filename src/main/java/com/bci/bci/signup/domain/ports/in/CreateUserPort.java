package com.bci.bci.signup.domain.ports.in;

import com.bci.bci.signup.infrastructure.adapters.in.rest.request.CreateUserRequest;
import com.bci.bci.signup.infrastructure.adapters.in.rest.response.UserCreatedResponse;

import javax.validation.Valid;

public interface CreateUserPort {
    UserCreatedResponse create(@Valid CreateUserRequest request);
}
