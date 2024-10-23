package com.bci.bci.user.infrastructure.adapters.in.rest.response;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@SuperBuilder
public class UserCreatedResponse {

    private UUID id;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;
}
