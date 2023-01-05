package com.deedeji.ecommerce.security;

import com.deedeji.ecommerce.data.models.AppUser;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import com.deedeji.ecommerce.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = null;
        try {
            user = userService.getUserByUsername(username);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        log.info("User from userDetail service {} ", user);
        return new SecureUser(user);
    }
}
