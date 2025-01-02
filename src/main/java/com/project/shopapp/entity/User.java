package com.project.shopapp.entity;

import java.sql.Date;

import jakarta.persistence.*;
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
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fullName;

    @Column(nullable = false, length = 10)
    String phoneNumber;

    String address;

    @Column(nullable = false)
    String password;

    boolean isActive;
    Date dateOfBirth;
    int facebookAccountId;
    int googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
}
