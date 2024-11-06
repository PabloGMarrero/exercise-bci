package com.bci.bci.user.infrastructure.adapters.in.rest;

import com.bci.bci.handler.ErrorMessageResponse;
import com.bci.bci.user.domain.exceptions.CreateUserException;
import com.bci.bci.user.domain.ports.in.CreateUserPort;
import com.bci.bci.user.domain.ports.in.LoginUserPort;
import com.bci.bci.user.infrastructure.adapters.in.rest.request.CreateUserRequest;
import com.bci.bci.user.infrastructure.adapters.in.rest.request.LoginUserRequest;
import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserCreatedResponse;
import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserLoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@Tag(name = "Sign-up Controller", description = "User administration services")
public class SignupController {

    private final CreateUserPort createUserPort;
    private final LoginUserPort loginUserPort;

    public SignupController(CreateUserPort createUserPort, LoginUserPort loginUserPort) {
        this.createUserPort = createUserPort;
        this.loginUserPort = loginUserPort;
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Create user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created.", content = {@Content(schema = @Schema(implementation = UserCreatedResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorMessageResponse.class)))})
    public ResponseEntity<UserCreatedResponse> createUser(
            @Parameter(description = "Create user request", required = true) @Valid @RequestBody CreateUserRequest request) throws CreateUserException {

        var response = createUserPort.create(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful.", content = {@Content(schema = @Schema(implementation = UserLoginResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorMessageResponse.class)))})
    public ResponseEntity<UserLoginResponse> loginUser(
            @Parameter(description = "Login user request", required = true) @Valid @RequestBody LoginUserRequest request) {

        var response = loginUserPort.login(request);
        return ResponseEntity.ok(response);
    }
}
