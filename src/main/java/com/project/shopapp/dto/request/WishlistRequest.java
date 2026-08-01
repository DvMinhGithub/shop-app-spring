package com.project.shopapp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistRequest {
    @NotNull(message = "Product ID is required")
    Long productId;
}
