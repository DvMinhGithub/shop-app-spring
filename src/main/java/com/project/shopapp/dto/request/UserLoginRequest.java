package com.project.shopapp.dto.request;

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
public class UserLoginRequest {
    @NotBlank(message = "Phone number is required")
    String phoneNumber;

    @NotBlank(message = "Password is required")
    String password;

    Long roleId;
}
