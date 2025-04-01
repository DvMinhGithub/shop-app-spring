package com.project.shopapp.model.dto.response;

import java.util.Date;

import com.project.shopapp.model.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    Long id;
    String fullName;
    String phoneNumber;
    String address;
    boolean isActive;
    Date dateOfBirth;
    int facebookAccountId;
    int googleAccountId;
    Role role;
}
