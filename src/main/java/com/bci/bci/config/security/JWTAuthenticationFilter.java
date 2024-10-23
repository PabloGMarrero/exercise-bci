package com.bci.bci.config.security;

import com.bci.bci.user.infrastructure.adapters.in.rest.request.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        var credentials = new CreateUserRequest();
        try {
            credentials = new ObjectMapper().readValue(request.getReader(), CreateUserRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);//TODO ver mejora exception
        }

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getEmail(),
                credentials.getPassword(),
                Collections.emptyList()
        );
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        var userDetailsWrapper = (UserDetailsWrapper) authResult.getPrincipal();
        String token = JWTUtil.generateToken(userDetailsWrapper.getUsername());

        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
