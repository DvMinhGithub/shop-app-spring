package com.project.shopapp.service;

import com.project.shopapp.dto.request.UserCreateRequest;
import com.project.shopapp.dto.request.UserLoginRequest;
import com.project.shopapp.entity.User;

public interface IUserService {
    User createUser(UserCreateRequest request);

    User login(UserLoginRequest request);
}
