package com.project.shopapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dto.request.UserCreateRequest;
import com.project.shopapp.dto.request.UserLoginRequest;
import com.project.shopapp.dto.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest request) {
        return "login";
    }

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody @Valid UserCreateRequest request) {
        return new ApiResponse<>(200, "User registered successfully", null);
    }
}
