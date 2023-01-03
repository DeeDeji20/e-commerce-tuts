package com.deedeji.ecommerce.security.filters;

import com.deedeji.ecommerce.security.managers.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
public class AppUserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final CustomAuthenticationManager customAuthenticationManager;
}
