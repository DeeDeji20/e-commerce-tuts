package com.deedeji.ecommerce.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AppUserAuthorizationFilter extends OncePerRequestFilter {

    private ObjectMapper mapper = new ObjectMapper();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);

        if (request.getServletPath().equals("/api/v1/customer/login")){
            filterChain.doFilter(request, response);
        }else {
            if (authHeader != null && authHeader.startsWith("Bearer ")){
                try {
                    String token = authHeader.substring("Bearer ".length());
                    log.info("authorization token --> {}", token);

                    JWTVerifier verifier = JWT.require(Algorithm.HMAC512("this-is-my-application-secret"))
                            .build();

                    DecodedJWT decodedJWT = verifier.verify(token);
                    String subject = decodedJWT.getSubject();
                    List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(subject,
                            null,
                            roles
                                    .stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList()));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    filterChain.doFilter(request, response);
                }catch (Exception exception){
                    response.setContentType(APPLICATION_JSON_VALUE);
                    mapper.writeValue(response.getOutputStream(), exception.getMessage());
                }
            }else filterChain.doFilter(request, response);
        }
    }
}
