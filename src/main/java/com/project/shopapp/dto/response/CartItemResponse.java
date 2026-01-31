package com.project.shopapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartItemResponse {
    Long id;
    Long productId;
    String productName;
    Float price;
    Long quantity;
    Float totalMoney;
}
