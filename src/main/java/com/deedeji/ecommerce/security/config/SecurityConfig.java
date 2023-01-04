package com.deedeji.ecommerce.security.config;

import com.deedeji.ecommerce.security.jwt.JwtUtil;
import com.deedeji.ecommerce.security.filters.AppUserAuthenticationFilter;
import com.deedeji.ecommerce.security.filters.AppUserAuthorizationFilter;
import com.deedeji.ecommerce.security.managers.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    private final CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        UsernamePasswordAuthenticationFilter filter =
                new AppUserAuthenticationFilter(customAuthenticationManager, jwtUtil);
        filter.setFilterProcessesUrl("api/v1/customer/login");

        return http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/customer/register", "api/v1/customer/login")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/customer/all").hasAnyAuthority("BUY")
                .and()
                .addFilter(filter)
                .addFilterBefore(new AppUserAuthorizationFilter(), AppUserAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .build();
    }
}
