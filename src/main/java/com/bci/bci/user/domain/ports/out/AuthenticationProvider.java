package com.bci.bci.user.domain.ports.out;

import com.bci.bci.user.domain.models.User;

public interface AuthenticationProvider {
    String generateToken(User user);

    String validateUser(String token);

}
