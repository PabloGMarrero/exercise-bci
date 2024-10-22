package com.bci.bci.user.domain.ports.out;

import com.bci.bci.user.domain.models.User;

import java.util.Optional;

public interface GetUserProvider {
    Optional<User> getByEmail(String email);
}
