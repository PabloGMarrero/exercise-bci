package com.bci.bci.user.infrastructure.adapters.out;

import com.bci.bci.user.domain.models.User;
import com.bci.bci.user.domain.ports.out.UpdateUserProvider;
import com.bci.bci.user.infrastructure.adapters.out.db.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class H2UpdateUser implements UpdateUserProvider {
    private final UserRepository userRepository;

    public H2UpdateUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User update(User updateUser) {
        return userRepository.save(updateUser);
    }
}
