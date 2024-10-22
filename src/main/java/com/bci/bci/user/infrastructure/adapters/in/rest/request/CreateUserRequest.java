package com.bci.bci.user.infrastructure.adapters.in.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
public class CreateUserRequest {

    @Schema(description = "User name")
    private String name;

    @Schema(description = "User email")
    @NotBlank(message = "Email can not be blank.")
    @Pattern(
            regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email format is not valid.")
    private String email;

    @Schema(description = "User password")
    @NotBlank(message = "Password can not be blank.")
    private String password;

    @Schema(description = "User phones")
    private List<UserPhoneRequest> phones;
}
