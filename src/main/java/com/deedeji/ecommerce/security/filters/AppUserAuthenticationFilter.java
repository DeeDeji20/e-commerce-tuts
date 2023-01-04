package com.deedeji.ecommerce.security.filters;

import com.deedeji.ecommerce.data.models.AppUser;
import com.deedeji.ecommerce.security.jwt.JwtUtil;
import com.deedeji.ecommerce.security.managers.CustomAuthenticationManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
public class AppUserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final CustomAuthenticationManager customAuthenticationManager;

    private final JwtUtil jwtUtil;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //TODO: 1. create an Authentication object(which contains auth credentials)
        // that isn't authenticated
        AppUser user;

        try {
            user =mapper.readValue(request.getReader(), AppUser.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String email = user.getEmail();
        String password = user.getPassword();
        log.info("email->{}  password->{}", email, password);
        var authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        //TODO: 2. use AuthenticationManager to authenticate the user
        // whose auth credentials are now contained within the authentication object

        //TODO: 3. get back the authentication object which has just been authenticated
        // by the AuthenticationManager
        Authentication authenticatedToken =
                customAuthenticationManager.authenticate(authenticationToken);
        //TODO: 4. store authentication in SecurityContext

        if (authenticatedToken!=null) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authenticationToken);
            return authenticatedToken;
        }
        throw new BadCredentialsException("Oh Oh!!");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("authResult->{}", authResult);
        UserDetails userDetails =  (UserDetails)authResult.getPrincipal();
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), tokens);
    }

}
