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
import com.project.shopapp.service.impl.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    UserServiceImpl userService;

    @PostMapping("/login")
    public ApiResponse<User> login(@RequestBody UserLoginRequest request) {
        return ApiResponse.<User>builder()
                .code(HttpStatus.OK.value())
                .message("User logged in successfully")
                .result(userService.login(request))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody @Valid UserCreateRequest request) {
        return new ApiResponse<>(
                HttpStatus.CREATED.value(), "User registered successfully", userService.createUser(request));
    }
}
