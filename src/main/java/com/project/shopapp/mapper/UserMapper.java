package com.project.shopapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.project.shopapp.model.dto.request.UpdateUserRequest;
import com.project.shopapp.model.dto.request.UserCreateRequest;
import com.project.shopapp.model.dto.request.UserLoginRequest;
import com.project.shopapp.model.dto.response.UserResponse;
import com.project.shopapp.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(UserCreateRequest request);

    UserLoginRequest toUser(UserLoginRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "facebookAccountId", ignore = true)
    @Mapping(target = "googleAccountId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User updateUserFromRequest(UpdateUserRequest request, @MappingTarget User user);
}
