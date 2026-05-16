package com.project.shopapp.service;

import java.util.List;

import com.project.shopapp.dto.request.UpdateUserRequest;
import com.project.shopapp.dto.request.UserCreateRequest;
import com.project.shopapp.dto.request.UserLoginRequest;
import com.project.shopapp.dto.response.LoginResponse;
import com.project.shopapp.dto.response.UserResponse;
import com.project.shopapp.model.entity.User;

public interface UserService {
    User createUser(UserCreateRequest request);

    LoginResponse login(UserLoginRequest request);

    UserResponse getUserDetails();

    void updateUser(UpdateUserRequest request);

    List<UserResponse> getUsers();

    UserResponse updateActive(Long id, boolean active);
}
