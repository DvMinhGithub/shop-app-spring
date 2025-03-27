package com.project.shopapp.service;

import com.project.shopapp.dto.request.UserCreateRequest;
import com.project.shopapp.dto.request.UserLoginRequest;
import com.project.shopapp.dto.response.LoginResponse;
import com.project.shopapp.model.User;

public interface UserService {
    User createUser(UserCreateRequest request);

    LoginResponse login(UserLoginRequest request);
}
