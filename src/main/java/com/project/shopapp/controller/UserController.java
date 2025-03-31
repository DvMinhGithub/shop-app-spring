package com.project.shopapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.model.dto.request.UserCreateRequest;
import com.project.shopapp.model.dto.request.UserLoginRequest;
import com.project.shopapp.model.dto.response.ApiResponse;
import com.project.shopapp.model.dto.response.LoginResponse;
import com.project.shopapp.model.entity.User;
import com.project.shopapp.service.impl.UserServiceImpl;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    UserServiceImpl userService;
    MessageUtils messageUtils;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody UserLoginRequest request) {
        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.LOGIN_SUCCESSFUL))
                .result(userService.login(request))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody @Valid UserCreateRequest request) {
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                messageUtils.getMessage(MessageKeys.REGISTRATION_SUCCESSFUL),
                userService.createUser(request));
    }
}
