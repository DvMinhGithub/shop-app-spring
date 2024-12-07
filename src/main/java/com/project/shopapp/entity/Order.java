package com.project.shopapp.entity;

import java.sql.Date;

import com.project.shopapp.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    String userId;
    String fullName;
    String email;
    String phoneNumber;
    String address;
    String note;
    Date orderDate;

    @Enumerated(EnumType.STRING)
    Status status;

    float totalMoney;
    String shippingMethod;
    String shippingAddress;
    Date shippingDate;
    String trackingNumber;
    String paymentMethod;
    boolean active;
}
