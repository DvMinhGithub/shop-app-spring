package com.project.shopapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.shopapp.enums.UserRole;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] PUBLIC_POST_ENDPOINTS = {"/users/login", "/users/register"};
    private static final String[] PUBLIC_GET_ENDPOINTS = {"/products/**", "/categories/**"};
    private static final String ORDERS_ENDPOINT = "/orders/**";
    private static final String PRODUCTS_ENDPOINT = "/products/**";
    private static final String CATEGORIES_ENDPOINT = "/categories/**";
    private static final String ORDER_DETAILS_ENDPOINT = "/order-details/**";

    private final AuthTokenFilter authTokenFilter;
    private final AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(this::configureAuthorization)
                .exceptionHandling(handling -> handling.authenticationEntryPoint(authEntryPointJwt))
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private void configureAuthorization(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request) {
        // Public endpoints
        request.requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINTS)
                .permitAll()
                .requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINTS)
                .permitAll()

                // Categories endpoints
                .requestMatchers(HttpMethod.GET, CATEGORIES_ENDPOINT)
                .hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                .requestMatchers(HttpMethod.POST, CATEGORIES_ENDPOINT)
                .hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, CATEGORIES_ENDPOINT)
                .hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, CATEGORIES_ENDPOINT)
                .hasRole(UserRole.ADMIN.name())

                // Products endpoints
                .requestMatchers(HttpMethod.GET, PRODUCTS_ENDPOINT)
                .hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                .requestMatchers(HttpMethod.POST, PRODUCTS_ENDPOINT)
                .hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, PRODUCTS_ENDPOINT)
                .hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, PRODUCTS_ENDPOINT)
                .hasRole(UserRole.ADMIN.name())

                // Orders endpoints
                .requestMatchers(HttpMethod.POST, ORDERS_ENDPOINT)
                .hasRole(UserRole.USER.name())
                .requestMatchers(HttpMethod.GET, ORDERS_ENDPOINT)
                .hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                .requestMatchers(HttpMethod.PUT, ORDERS_ENDPOINT)
                .hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, ORDERS_ENDPOINT)
                .hasRole(UserRole.ADMIN.name())

                // Order details endpoints
                .requestMatchers(HttpMethod.POST, ORDER_DETAILS_ENDPOINT)
                .hasRole(UserRole.USER.name())
                .requestMatchers(HttpMethod.GET, ORDER_DETAILS_ENDPOINT)
                .hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                .requestMatchers(HttpMethod.PUT, ORDER_DETAILS_ENDPOINT)
                .hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, ORDER_DETAILS_ENDPOINT)
                .hasRole(UserRole.ADMIN.name())

                // All other requests require authentication
                .anyRequest()
                .authenticated();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization");
            }
        };
    }
}
