package com.project.shopapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dto.request.WishlistRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.dto.response.WishlistItemResponse;
import com.project.shopapp.security.CustomUserDetailsService;
import com.project.shopapp.service.WishlistService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final CustomUserDetailsService userDetailsService;

    @GetMapping
    public ApiResponse<List<WishlistItemResponse>> getWishlist() {
        return ApiResponse.<List<WishlistItemResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Wishlist fetched successfully")
                .result(wishlistService.getWishlist(userDetailsService.getCurrentUserId()))
                .build();
    }

    @PostMapping
    public ApiResponse<WishlistItemResponse> addItem(@RequestBody @Valid WishlistRequest request) {
        return ApiResponse.<WishlistItemResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Wishlist item saved successfully")
                .result(wishlistService.addItem(userDetailsService.getCurrentUserId(), request.getProductId()))
                .build();
    }

    @DeleteMapping("/products/{productId}")
    public ApiResponse<Void> removeItem(@PathVariable Long productId) {
        wishlistService.removeItem(userDetailsService.getCurrentUserId(), productId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Wishlist item deleted successfully")
                .build();
    }
}
