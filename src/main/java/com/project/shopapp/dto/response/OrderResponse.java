package com.project.shopapp.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.project.shopapp.model.enums.OrderStatus;
import com.project.shopapp.model.enums.PaymentStatus;

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
public class OrderResponse {
    Long id;

    Long userId;

    String fullName;

    String email;

    String phoneNumber;

    String address;

    String note;

    LocalDateTime orderDate;

    OrderStatus status;

    float totalMoney;

    String shippingMethod;

    String shippingAddress;

    LocalDateTime shippingDate;

    String paymentMethod;

    PaymentStatus paymentStatus;

    LocalDateTime paidAt;

    String transactionId;

    boolean active = true;

    List<OrderDetailResponse> orderDetails;
}
