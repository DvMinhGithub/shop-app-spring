package com.project.shopapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class OderDetailRequest {

    @NotNull(message = "Order id is required")
    @Min(value = 1, message = "Order id must be greater than 0")
    long orderId;

    @NotNull(message = "Product id is required")
    @Min(value = 1, message = "Product id must be greater than 0")
    long productId;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than 0")
    float price;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    Long quantity;

    @NotNull(message = "Total money is required")
    @Min(value = 0, message = "Total money must be greater than 0")
    long totalMoney;

    String color;
}
