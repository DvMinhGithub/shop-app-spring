package com.project.shopapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] PUBLIC_POST_ENDPOINTS = {"/users/login", "/users/register"};
    private static final String[] PUBLIC_GET_ENDPOINTS = {"/products/**", "/categories/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINTS)
                            .permitAll();
                    request.requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINTS)
                            .permitAll();
                    request.anyRequest().authenticated();
                });

        http.exceptionHandling(handling -> handling.authenticationEntryPoint(new JwtAuthenticationEntryPoints()));

        return http.build();
    }
}
