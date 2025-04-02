package com.project.shopapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
@EnableWebSecurity()
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthTokenFilter authTokenFilter;
    private final AuthEntryPoint authEntryPoint;

    private static final String PRODUCT_ENDPOINTS = "/products/**";
    private static final String CATEGORY_ENDPOINTS = "/categories/**";
    private static final String ORDER_ENDPOINTS = "/orders/**";
    private static final String ORDER_DETAIL_ENDPOINTS = "/order-details/**";

    private static final String[] PUBLIC_POST_ENDPOINTS = {"/users/login", "/users/register", "/files/**"};

    private static final String[] PUBLIC_GET_ENDPOINTS = {
        PRODUCT_ENDPOINTS, CATEGORY_ENDPOINTS, "/roles/**", "/files/**"
    };

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
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(this::configureAuthorization)
                .exceptionHandling(handling -> handling.authenticationEntryPoint(authEntryPoint))
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
                .requestMatchers(HttpMethod.GET, CATEGORY_ENDPOINTS)
                .hasAnyAuthority(UserRole.ADMIN.name(), UserRole.USER.name())
                .requestMatchers(HttpMethod.POST, CATEGORY_ENDPOINTS)
                .hasAuthority(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, CATEGORY_ENDPOINTS)
                .hasAuthority(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, CATEGORY_ENDPOINTS)
                .hasAuthority(UserRole.ADMIN.name())

                // Products
                .requestMatchers(HttpMethod.POST, PRODUCT_ENDPOINTS)
                .hasAuthority(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, PRODUCT_ENDPOINTS)
                .hasAuthority(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, PRODUCT_ENDPOINTS)
                .hasAuthority(UserRole.ADMIN.name())

                // Orders
                .requestMatchers(HttpMethod.POST, ORDER_ENDPOINTS)
                .hasAnyAuthority(UserRole.USER.name())
                .requestMatchers(HttpMethod.GET, ORDER_ENDPOINTS)
                .hasAnyAuthority(UserRole.ADMIN.name(), UserRole.USER.name())
                .requestMatchers(HttpMethod.PUT, ORDER_ENDPOINTS)
                .hasAuthority(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, ORDER_ENDPOINTS)
                .hasAuthority(UserRole.ADMIN.name())

                // Order details
                .requestMatchers(HttpMethod.POST, ORDER_DETAIL_ENDPOINTS)
                .hasAuthority(UserRole.USER.name())
                .requestMatchers(HttpMethod.GET, ORDER_DETAIL_ENDPOINTS)
                .hasAnyAuthority(UserRole.ADMIN.name(), UserRole.USER.name())
                .requestMatchers(HttpMethod.PUT, ORDER_DETAIL_ENDPOINTS)
                .hasAuthority(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, ORDER_DETAIL_ENDPOINTS)
                .hasAuthority(UserRole.ADMIN.name())
                .anyRequest()
                .authenticated();
    }
}
