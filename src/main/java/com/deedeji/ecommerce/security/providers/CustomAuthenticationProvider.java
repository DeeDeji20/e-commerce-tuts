package com.deedeji.ecommerce.security.providers;

import com.deedeji.ecommerce.security.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername((String) authentication.getPrincipal());
        if (userDetails != null){
            if (isPasswordMatch(authentication, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                authentication.getCredentials(),
                                userDetails.getAuthorities());
                return authenticationToken;
            }
            throw new BadCredentialsException("Incorrect password");
        }
        throw new AuthenticationCredentialsNotFoundException("email does not exist");
    }

    private boolean isPasswordMatch(Authentication authentication, UserDetails userDetails) {
        return passwordEncoder.matches((String)authentication.getCredentials(),
                userDetails.getPassword());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
