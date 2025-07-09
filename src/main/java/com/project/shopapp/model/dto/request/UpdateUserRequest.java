package com.project.shopapp.model.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "0\\d{9}")
    private String phoneNumber;

    private String address;

    private Date dateOfBirth;
}
