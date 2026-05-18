package com.project.shopapp.service;

import com.project.shopapp.dto.response.OrderResponse;
import com.project.shopapp.model.enums.PaymentStatus;

public interface PaymentService {
    OrderResponse getPayment(Long orderId, Long userId, boolean admin);

    OrderResponse mockPay(Long orderId, Long userId, boolean admin);

    OrderResponse updateStatus(Long orderId, PaymentStatus status, String transactionId);
}
