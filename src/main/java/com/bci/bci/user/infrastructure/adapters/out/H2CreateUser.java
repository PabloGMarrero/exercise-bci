package com.bci.bci.user.infrastructure.adapters.out;

import com.bci.bci.user.domain.models.Phone;
import com.bci.bci.user.domain.models.User;
import com.bci.bci.user.domain.ports.out.CreateUserProvider;
import com.bci.bci.user.infrastructure.adapters.in.rest.request.CreateUserRequest;
import com.bci.bci.user.infrastructure.adapters.out.db.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class H2CreateUser implements CreateUserProvider {

    private final UserRepository userRepository;

    public H2CreateUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(CreateUserRequest request) {

        var entity = User.builder()
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .isActive(true)
                .build();

        var phones = request.getPhones().stream()
                .map(phoneRequest -> Phone.builder()
                        .number(phoneRequest.getNumber())
                        .countryCode(phoneRequest.getContryCode())
                        .cityCode(phoneRequest.getCityCode())
                        .user(entity)
                        .build())
                .map(entity::addPhone)
                .collect(Collectors.toSet());



        return userRepository.save(entity);
    }
}
