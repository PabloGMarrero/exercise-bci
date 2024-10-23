package com.bci.bci.config.security;

import com.bci.bci.user.domain.ports.out.GetUserProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final GetUserProvider getUserProvider;

    public UserDetailsServiceImpl(GetUserProvider getUserProvider) {
        this.getUserProvider = getUserProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = getUserProvider.getByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("The user does not exist."));

        return new UserDetailsWrapper(user);
    }
}
