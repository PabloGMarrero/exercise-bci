package com.bci.bci.config.security;

import io.jsonwebtoken.lang.Strings;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    
    public static final String BEARER_PREFIX = "Bearer ";

    @Override
    //Is already registered, trying to use resources from service
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearer = request.getHeader("Authorization");

        if (bearer != null && bearer.startsWith(BEARER_PREFIX)){
            var token = bearer.replace(BEARER_PREFIX, Strings.EMPTY);
            var user = JWTUtil.validateToken(token);

            SecurityContextHolder.getContext().setAuthentication(user);
        }

        //continues filter flow
        filterChain.doFilter(request, response);
    }
}
