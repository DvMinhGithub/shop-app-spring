package com.project.shopapp.service;

import com.project.shopapp.dto.request.CartCheckoutRequest;
import com.project.shopapp.dto.request.CartItemAddRequest;
import com.project.shopapp.dto.request.CartItemUpdateRequest;
import com.project.shopapp.dto.response.CartResponse;
import com.project.shopapp.dto.response.OrderResponse;

public interface CartService {
    CartResponse getCart(Long userId);

    CartResponse addItem(CartItemAddRequest request);

    CartResponse updateItem(Long itemId, CartItemUpdateRequest request);

    void deleteItem(Long itemId);

    OrderResponse checkout(CartCheckoutRequest request);
}
