package com.project.shopapp.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.shopapp.config.JwtConfig;
import com.project.shopapp.dto.request.UserCreateRequest;
import com.project.shopapp.dto.request.UserLoginRequest;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.exception.InvalidPasswordException;
import com.project.shopapp.mapper.UserMapper;
import com.project.shopapp.model.Role;
import com.project.shopapp.model.User;
import com.project.shopapp.repository.RoleRepository;
import com.project.shopapp.repository.UserRepository;
import com.project.shopapp.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    JwtConfig jwtConfig;
    AuthenticationManager authenticationManager;

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
            String password = passwordEncoder.encode(request.getPassword());
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

        if (user.getGoogleAccountId() != 0 || user.getFacebookAccountId() != 0) {
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new InvalidPasswordException("Invalid password");
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(phoneNumber, password);
        authenticationManager.authenticate(authenticationToken);

        return jwtConfig.generateToken(user);
    }
}
