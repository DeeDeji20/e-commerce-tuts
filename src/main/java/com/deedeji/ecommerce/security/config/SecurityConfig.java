package com.deedeji.ecommerce.security.config;

import com.deedeji.ecommerce.security.config.jwt.JwtUtil;
import com.deedeji.ecommerce.security.filters.AppUserAuthenticationFilter;
import com.deedeji.ecommerce.security.providers.CustomAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        UsernamePasswordAuthenticationFilter filter =
                new AppUserAuthenticationFilter(customAuthenticationProvider, jwtUtil);
    }
}
