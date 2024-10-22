package com.bci.bci.signup.domain.ports.out;

import com.bci.bci.signup.domain.models.User;
import com.bci.bci.signup.infrastructure.adapters.in.rest.request.CreateUserRequest;

public interface CreateUserProvider {
    User createUser(CreateUserRequest request);
}
