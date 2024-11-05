package com.bci.bci.user.infrastructure.adapters.out;

import com.bci.bci.user.domain.models.User;
import com.bci.bci.user.domain.ports.out.GetUserProvider;
import com.bci.bci.user.infrastructure.adapters.out.db.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class H2GetUser implements GetUserProvider {

    private final UserRepository userRepository;

    public H2GetUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }
}
