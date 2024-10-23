package com.bci.bci.config;

import com.bci.bci.config.security.JWTAuthenticationFilter;
import com.bci.bci.config.security.JWTAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    public WebSecurityConfig(UserDetailsService userDetailsService, JWTAuthorizationFilter jwtAuthorizationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        //jwtAuthenticationFilter.setFilterProcessesUrl("/api/sign-up");

        httpSecurity.headers().frameOptions().disable();

        return httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/sign-up").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/swagger-ui.html").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/login").authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }
}
