package com.bci.bci.user.domain.exceptions;

import lombok.Getter;

@Getter
public class CreateUserException extends Throwable {
    public static final String ALREADY_EXIST = "User already registered";

    private final String message;

    public CreateUserException(String message) {
        this.message = message;
    }
}
