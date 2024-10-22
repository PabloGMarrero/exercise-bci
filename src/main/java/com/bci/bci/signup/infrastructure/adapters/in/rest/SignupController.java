package com.bci.bci.signup.infrastructure.adapters.in.rest;

import com.bci.bci.handler.ErrorMessageResponse;
import com.bci.bci.signup.domain.ports.in.CreateUserPort;
import com.bci.bci.signup.infrastructure.adapters.in.rest.request.CreateUserRequest;
import com.bci.bci.signup.infrastructure.adapters.in.rest.response.UserCreatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sign-up")
@Validated
public class SignupController {

    private final CreateUserPort createUserPort;

    public SignupController(CreateUserPort createUserPort) {
        this.createUserPort = createUserPort;
    }

    @PostMapping
    @Operation(summary = "Create user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created.", content = { @Content(schema = @Schema(implementation = UserCreatedResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content (schema = @Schema(implementation = ErrorMessageResponse.class))) })
    public ResponseEntity<UserCreatedResponse> createUser(@Valid @RequestBody CreateUserRequest request) {

        var response = createUserPort.create(request);
        return ResponseEntity.ok(response);
    }
}
