package com.project.shopapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dto.request.UserCreateRequest;
import com.project.shopapp.dto.request.UserLoginRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.entity.User;
import com.project.shopapp.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserLoginRequest request) {
        return new ApiResponse<>(HttpStatus.OK.value(), "User logged in successfully", userService.login(request));
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody @Valid UserCreateRequest request) {
        return new ApiResponse<>(
                HttpStatus.CREATED.value(), "User registered successfully", userService.createUser(request));
    }
}
