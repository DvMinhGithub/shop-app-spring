package com.project.shopapp.dto.request;

import com.project.shopapp.model.enums.PaymentStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentStatusUpdateRequest {
    @NotNull(message = "Payment status is required")
    PaymentStatus paymentStatus;

    String transactionId;
}
