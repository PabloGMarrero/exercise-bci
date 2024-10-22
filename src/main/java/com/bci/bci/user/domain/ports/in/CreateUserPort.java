package com.bci.bci.user.domain.ports.in;

import com.bci.bci.user.domain.exceptions.CreateUserException;
import com.bci.bci.user.infrastructure.adapters.in.rest.request.CreateUserRequest;
import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserCreatedResponse;

import javax.validation.Valid;

public interface CreateUserPort {
    UserCreatedResponse create(@Valid CreateUserRequest request) throws CreateUserException;
}
