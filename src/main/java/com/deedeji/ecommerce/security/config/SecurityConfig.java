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
        filter.setFilterProcessesUrl("/api/v1/login");

        return http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/customer/register", "/api/v1/admin/register",  "/api/v1/login")
                .permitAll()

                .antMatchers(HttpMethod.GET, "/api/v1/customer/all", "/api/v1/admin/all", "/api/v1/vendor/all", "/api/v1/customer/{id}", "/api/v1/admin/{id}", "/api/v1/vendor/{id}").hasAnyAuthority("MODERATE")
                .antMatchers(HttpMethod.PUT, "/api/v1/customer").hasAnyAuthority("BUY")
                .antMatchers(HttpMethod.PUT, "/api/v1/admin", "/api/v1/admin/suspendUser/{email}").hasAnyAuthority("MODERATE")
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
