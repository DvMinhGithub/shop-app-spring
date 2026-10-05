package com.project.shopapp.service;

import java.util.List;

import com.project.shopapp.dto.response.WishlistItemResponse;

public interface WishlistService {
    List<WishlistItemResponse> getWishlist(Long userId);

    WishlistItemResponse addItem(Long userId, Long productId);

    void removeItem(Long userId, Long productId);
}
