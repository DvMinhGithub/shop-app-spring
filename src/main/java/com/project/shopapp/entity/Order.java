package com.project.shopapp.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.project.shopapp.enums.Status;

import jakarta.persistence.*;
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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    User user;

    String fullName;
    String email;

    @Column(length = 10)
    String phoneNumber;

    String address;
    String note;
    LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    Status status;

    Integer totalMoney;
    String shippingMethod;
    String shippingAddress;
    LocalDateTime shippingDate;
    String trackingNumber;
    String paymentMethod;
    boolean active;

    @OneToMany
    List<OderDetail> orderDetails;
}
