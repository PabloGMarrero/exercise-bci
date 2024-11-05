package com.bci.bci.config.security;

import com.bci.bci.user.domain.ports.out.GetUserProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final GetUserProvider getUserProvider;

    public CustomUserDetailsService(GetUserProvider getUserProvider) {
        this.getUserProvider = getUserProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = getUserProvider.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return UserWrapper.builder().user(user).build();
    }

}
