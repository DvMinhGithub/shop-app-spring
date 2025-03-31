package com.project.shopapp.model.dto.request;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserCreateRequest {
    String fullName;

    @NotBlank(message = "Phone number is required")
    String phoneNumber;

    String address;

    @NotBlank(message = "Password is required")
    String password;

    @NotBlank(message = "Retyped password is required")
    String retypedPassword;

    Date dateOfBirth;
    int facebookAccountId;
    int googleAccountId;

    Long roleId;
}
