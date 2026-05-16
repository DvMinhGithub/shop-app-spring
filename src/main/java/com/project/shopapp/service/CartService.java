package com.project.shopapp.service;

import java.util.List;

import com.project.shopapp.dto.request.CartCheckoutRequest;
import com.project.shopapp.dto.request.CartItemAddRequest;
import com.project.shopapp.dto.request.CartItemMergeRequest;
import com.project.shopapp.dto.request.CartItemUpdateRequest;
import com.project.shopapp.dto.response.CartResponse;
import com.project.shopapp.dto.response.OrderResponse;

public interface CartService {
    CartResponse getCart(Long userId);

    CartResponse addItem(Long userId, CartItemAddRequest request);

    CartResponse updateItem(Long userId, Long itemId, CartItemUpdateRequest request);

    void deleteItem(Long userId, Long itemId);

    CartResponse mergeCart(Long userId, List<CartItemMergeRequest> items);

    OrderResponse checkout(Long userId, CartCheckoutRequest request);
}
