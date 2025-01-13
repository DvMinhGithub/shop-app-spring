package com.project.shopapp.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.shopapp.enums.OrderStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
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

    @NotBlank(message = "Full name is required")
    String fullName;

    @Email(message = "Email should be valid")
    String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    String phoneNumber;

    @NotBlank(message = "Address is required")
    String address;

    String note;

    LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @PositiveOrZero(message = "Total money must be positive or zero")
    float totalMoney;

    String shippingMethod;
    String shippingAddress;
    LocalDateTime shippingDate;
    String trackingNumber;
    String paymentMethod;
    boolean active = true;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<OrderDetail> orderDetails;

    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }
}
