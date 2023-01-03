package com.deedeji.ecommerce.security.config;

import com.deedeji.ecommerce.security.config.jwt.JwtUtil;
import com.deedeji.ecommerce.security.providers.CustomAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    private final CustomAuthenticationProvider customAuthenticationProvider;
}
