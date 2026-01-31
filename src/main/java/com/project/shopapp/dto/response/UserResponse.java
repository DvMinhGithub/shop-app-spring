package com.project.shopapp.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String fullName;
    String phoneNumber;
    String address;

    @JsonProperty("isActive")
    boolean active;

    Date dateOfBirth;
    Long facebookAccountId;
    Long googleAccountId;
    RoleResponse role;
}
