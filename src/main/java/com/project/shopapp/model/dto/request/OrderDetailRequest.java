package com.project.shopapp.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
public class OrderDetailRequest {

    @NotNull(message = "Order id is required")
    @Positive(message = "Order id must be greater than 0")
    Long orderId;

    @NotNull(message = "Product id is required")
    @Positive(message = "Product id must be greater than 0")
    Long productId;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be greater than or equal to 0")
    Float price;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than 0")
    Long quantity;

    @NotNull(message = "Total money is required")
    @PositiveOrZero(message = "Total money must be greater than or equal to 0")
    Long totalMoney;

    @NotNull(message = "Color is required")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Color must be a valid HEX color code")
    String color;
}
