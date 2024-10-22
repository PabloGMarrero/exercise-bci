package com.bci.bci.signup.infrastructure.adapters.in.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class CreateUserRequest {

    @Schema(description = "User name")
    private String name;

    @Schema(description = "User email")
    @NotBlank(message = "Email can not be blank.")
    private String email;

    @Schema(description = "User password")
    @NotBlank(message = "Password can not be blank.")
    private String password;

    @Schema(description = "User phones")
    private List<UserPhoneRequest> phones;
}
