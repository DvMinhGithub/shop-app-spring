package com.project.shopapp.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {
    Long productId;
    Long quantity;
}
