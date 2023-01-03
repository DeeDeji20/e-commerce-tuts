package com.deedeji.ecommerce.security.providers;

import com.deedeji.ecommerce.security.CustomerDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomerDetailsService customerDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
