package com.project.shopapp.model.dto.request;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import com.project.shopapp.model.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class OrderRequest {
    Long userId;
    String fullName;
    String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters")
    String phoneNumber;

    String address;
    String note;
    Date orderDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'PENDING'")
    OrderStatus status;

    @Min(value = 0, message = "Total money must be greater than 0")
    float totalMoney;

    String shippingMethod;
    String shippingAddress;
    LocalDateTime shippingDate;
    String paymentMethod;

    List<CartItemRequest> cartItems;
}
