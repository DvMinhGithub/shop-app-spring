package com.project.shopapp.service;

import com.project.shopapp.dto.request.UserCreateRequest;
import com.project.shopapp.dto.request.UserLoginRequest;
import com.project.shopapp.model.User;

public interface UserService {
    User createUser(UserCreateRequest request);

    String login(UserLoginRequest request);
}
