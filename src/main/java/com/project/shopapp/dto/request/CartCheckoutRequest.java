package com.project.shopapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartCheckoutRequest {
    Long userId;

    String fullName;
    String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters")
    String phoneNumber;

    String address;
    String note;
    String shippingMethod;
    String shippingAddress;
    String paymentMethod;
}
