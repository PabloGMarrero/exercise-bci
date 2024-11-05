package com.bci.bci.user.domain.ports.out;

import com.bci.bci.user.domain.models.User;

public interface GetUserProvider {
    User getByEmail(String email);
}
