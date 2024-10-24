package com.bci.bci.user.infrastructure.adapters.out;

import com.bci.bci.config.security.JWTUtil;
import com.bci.bci.user.domain.models.User;
import com.bci.bci.user.domain.ports.out.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JWTAuthentication implements AuthenticationProvider {
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public JWTAuthentication(JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String generateToken(User user) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
                );
        return jwtUtil.createToken(user);
    }

    @Override
    public void validateUser(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
