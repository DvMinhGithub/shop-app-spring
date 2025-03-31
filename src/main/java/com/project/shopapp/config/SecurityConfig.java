package com.project.shopapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.shopapp.model.enums.UserRole;
import com.project.shopapp.security.jwt.AuthEntryPoint;
import com.project.shopapp.security.jwt.AuthTokenFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthTokenFilter authTokenFilter;
    private final AuthEntryPoint AuthEntryPoint;

    private static final String[] PUBLIC_POST_ENDPOINTS = {"/users/login", "/users/register", "/files/**"};

    private static final String[] PUBLIC_GET_ENDPOINTS = {"/products/**", "/categories/**", "/roles/**", "/files/**"};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(this::configureAuthorization)
                .exceptionHandling(handling -> handling.authenticationEntryPoint(AuthEntryPoint))
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private void configureAuthorization(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request) {
        request
                // Public endpoints
                .requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINTS)
                .permitAll()
                .requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINTS)
                .permitAll()

                // Categories
                .requestMatchers(HttpMethod.GET, "/categories/**")
                .hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                .requestMatchers(HttpMethod.POST, "/categories/**")
                .hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/categories/**")
                .hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/categories/**")
                .hasRole(UserRole.ADMIN.name())

                // Products
                .requestMatchers(HttpMethod.POST, "/products/**")
                .hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/products/**")
                .hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/products/**")
                .hasRole(UserRole.ADMIN.name())

                // Orders
                .requestMatchers(HttpMethod.POST, "/orders/**")
                .hasRole(UserRole.USER.name())
                .requestMatchers(HttpMethod.GET, "/orders/**")
                .hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                .requestMatchers(HttpMethod.PUT, "/orders/**")
                .hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/orders/**")
                .hasRole(UserRole.ADMIN.name())

                // Order details
                .requestMatchers(HttpMethod.POST, "/order-details/**")
                .hasRole(UserRole.USER.name())
                .requestMatchers(HttpMethod.GET, "/order-details/**")
                .hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                .requestMatchers(HttpMethod.PUT, "/order-details/**")
                .hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/order-details/**")
                .hasRole(UserRole.ADMIN.name())
                .anyRequest()
                .authenticated();
    }
}
