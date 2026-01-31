package com.project.shopapp.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartResponse {
    Long cartId;
    Long userId;
    List<CartItemResponse> items;
    Long totalItems;
    Float totalMoney;
}
