package com.project.shopapp.dto.request;

import com.project.shopapp.model.enums.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderStatusUpdateRequest {
    @NotNull(message = "Order status is required")
    OrderStatus status;
}
