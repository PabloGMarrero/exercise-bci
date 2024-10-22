package com.bci.bci.user.domain.ports.out;

import com.bci.bci.user.domain.models.User;
import com.bci.bci.user.infrastructure.adapters.in.rest.request.CreateUserRequest;

public interface CreateUserProvider {
    User createUser(CreateUserRequest request);
}
