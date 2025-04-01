package com.project.shopapp.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.exception.DuplicateEntryException;
import com.project.shopapp.exception.InvalidPasswordException;
import com.project.shopapp.mapper.UserMapper;
import com.project.shopapp.model.dto.request.UserCreateRequest;
import com.project.shopapp.model.dto.request.UserLoginRequest;
import com.project.shopapp.model.dto.response.LoginResponse;
import com.project.shopapp.model.dto.response.UserResponse;
import com.project.shopapp.model.entity.Role;
import com.project.shopapp.model.entity.User;
import com.project.shopapp.model.enums.UserRole;
import com.project.shopapp.repository.RoleRepository;
import com.project.shopapp.repository.UserRepository;
import com.project.shopapp.security.CustomUserDetailsService;
import com.project.shopapp.security.jwt.JwtUtils;
import com.project.shopapp.service.UserService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

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
    JwtUtils jwtUtil;
    MessageUtils messageUtils;
    AuthenticationManager authenticationManager;
    CustomUserDetailsService customUserDetailsService;

    @Override
    public User createUser(UserCreateRequest request) {
        String phoneNumber = request.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicateEntryException(messageUtils.getMessage(MessageKeys.USER_ALREADY_EXISTS));
        }
        Role role = roleRepository
                .findByName(UserRole.USER.name())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ROLE_NOT_FOUND)));

        User user = userMapper.toUser(request);
        user.setRole(role);
        user.setActive(true);
        if (request.getGoogleAccountId() == 0 || request.getFacebookAccountId() == 0) {
            String password = passwordEncoder.encode(request.getPassword());
            user.setPassword(password);
        }
        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(UserLoginRequest request) {
        String phoneNumber = request.getPhoneNumber();
        String password = request.getPassword();
        User user = userRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.USER_NOT_FOUND)));

        if ((user.getGoogleAccountId() != 0 || user.getFacebookAccountId() != 0)
                && !passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException(messageUtils.getMessage(MessageKeys.INVALID_PASSWORD));
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(phoneNumber, password);
        authenticationManager.authenticate(authenticationToken);
        return new LoginResponse(jwtUtil.generateToken(user));
    }

    @Override
    public UserResponse getUserDetails() {
        Long userId = customUserDetailsService.getCurrentUserId();

        return userRepository
                .findById(userId)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.USER_NOT_FOUND)));
    }
}
