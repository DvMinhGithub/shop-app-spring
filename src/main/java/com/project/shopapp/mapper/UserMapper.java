package com.project.shopapp.mapper;

import org.mapstruct.Mapper;

import com.project.shopapp.dto.request.UserCreateRequest;
import com.project.shopapp.dto.request.UserLoginRequest;
import com.project.shopapp.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);

    UserLoginRequest toUser(UserLoginRequest request);
}
