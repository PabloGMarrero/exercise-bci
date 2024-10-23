package com.bci.bci.user.domain.ports.out;

import com.bci.bci.user.domain.models.User;

public interface UpdateUserProvider {
    User update(User updateUser);
}
