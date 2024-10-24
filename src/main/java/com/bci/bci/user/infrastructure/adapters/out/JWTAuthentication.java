package com.bci.bci.user.infrastructure.adapters.out;

import com.bci.bci.config.security.JWTUtil;
import com.bci.bci.user.domain.models.User;
import com.bci.bci.user.domain.ports.out.AuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthentication implements AuthenticationProvider {
    private final JWTUtil jwtUtil;


    public JWTAuthentication(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String generateToken(User user) {
        return jwtUtil.createToken(user.getEmail());
    }

    @Override
    public String validateUser(String token) {
        return jwtUtil.extractEmail(token);
    }


}
