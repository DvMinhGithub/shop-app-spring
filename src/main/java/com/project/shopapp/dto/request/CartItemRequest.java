package com.project.shopapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {
    Long productId;
    Long quantity;
}
