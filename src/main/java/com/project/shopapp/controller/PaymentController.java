package com.project.shopapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dto.request.PaymentStatusUpdateRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.dto.response.OrderResponse;
import com.project.shopapp.security.CustomUserDetailsService;
import com.project.shopapp.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final CustomUserDetailsService userDetailsService;

    @GetMapping("/orders/{orderId}")
    public ApiResponse<OrderResponse> getPayment(@PathVariable Long orderId) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Payment detail fetched successfully")
                .result(paymentService.getPayment(
                        orderId, userDetailsService.getCurrentUserId(), userDetailsService.isCurrentUserAdmin()))
                .build();
    }

    @PostMapping("/orders/{orderId}/mock-pay")
    public ApiResponse<OrderResponse> mockPay(@PathVariable Long orderId) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Mock payment completed successfully")
                .result(paymentService.mockPay(
                        orderId, userDetailsService.getCurrentUserId(), userDetailsService.isCurrentUserAdmin()))
                .build();
    }

    @PatchMapping("/orders/{orderId}/status")
    public ApiResponse<OrderResponse> updatePaymentStatus(
            @PathVariable Long orderId, @RequestBody @Valid PaymentStatusUpdateRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Payment status updated successfully")
                .result(paymentService.updateStatus(orderId, request.getPaymentStatus(), request.getTransactionId()))
                .build();
    }
}
