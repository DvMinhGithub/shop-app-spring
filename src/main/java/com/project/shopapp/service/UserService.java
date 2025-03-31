package com.project.shopapp.service;

import com.project.shopapp.model.dto.request.UserCreateRequest;
import com.project.shopapp.model.dto.request.UserLoginRequest;
import com.project.shopapp.model.dto.response.LoginResponse;
import com.project.shopapp.model.entity.User;

public interface UserService {
    User createUser(UserCreateRequest request);

    LoginResponse login(UserLoginRequest request);
}
