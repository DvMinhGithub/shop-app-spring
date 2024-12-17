package com.project.shopapp.service.impl;

import org.springframework.stereotype.Service;

import com.project.shopapp.dto.request.UserCreateRequest;
import com.project.shopapp.dto.request.UserLoginRequest;
import com.project.shopapp.entity.Role;
import com.project.shopapp.entity.User;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.exception.InvalidPasswordException;
import com.project.shopapp.mapper.UserMapper;
import com.project.shopapp.repository.RoleRepository;
import com.project.shopapp.repository.UserRepository;
import com.project.shopapp.service.IUserService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserServiceImpl implements IUserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;

    @Override
    public User createUser(UserCreateRequest request) {
        String phoneNumber = request.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new RuntimeException("User already exists");
        }
        Role role = roleRepository
                .findById(request.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        User user = userMapper.toUser(request);
        user.setRole(role);
        if (request.getGoogleAccountId() == 0 || request.getFacebookAccountId() == 0) {
            String password = request.getPassword();
            user.setPassword(password);
        }
        return userRepository.save(user);
    }

    @Override
    public String login(UserLoginRequest request) {
        String phoneNumber = request.getPhoneNumber();
        String password = request.getPassword();
        User user = userRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid password");
        }
        return "User logged in successfully";
    }
}
