package com.bci.bci.config.security;

import com.bci.bci.user.domain.ports.out.GetUserProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final GetUserProvider getUserProvider;

    public CustomUserDetailsService(GetUserProvider getUserProvider) {
        this.getUserProvider = getUserProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = getUserProvider.getByEmail(email);
        var roles = new ArrayList<String>();
        roles.add("USER");
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
    }
}
