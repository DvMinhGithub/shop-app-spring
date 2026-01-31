package com.project.shopapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dto.request.CartCheckoutRequest;
import com.project.shopapp.dto.request.CartItemAddRequest;
import com.project.shopapp.dto.request.CartItemUpdateRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.dto.response.CartResponse;
import com.project.shopapp.dto.response.OrderResponse;
import com.project.shopapp.service.CartService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final MessageUtils messageUtils;

    @GetMapping
    public ApiResponse<CartResponse> getCart(@RequestParam Long userId) {
        return ApiResponse.<CartResponse>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.CART_GET_SUCCESS))
                .result(cartService.getCart(userId))
                .build();
    }

    @PostMapping("/items")
    public ApiResponse<CartResponse> addItem(@RequestBody @Valid CartItemAddRequest request) {
        return ApiResponse.<CartResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message(messageUtils.getMessage(MessageKeys.CART_ITEM_ADD_SUCCESS))
                .result(cartService.addItem(request))
                .build();
    }

    @PutMapping("/items/{itemId}")
    public ApiResponse<CartResponse> updateItem(
            @PathVariable Long itemId, @RequestBody @Valid CartItemUpdateRequest request) {
        return ApiResponse.<CartResponse>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.CART_ITEM_UPDATE_SUCCESS))
                .result(cartService.updateItem(itemId, request))
                .build();
    }

    @DeleteMapping("/items/{itemId}")
    public ApiResponse<Void> deleteItem(@PathVariable Long itemId) {
        cartService.deleteItem(itemId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message(messageUtils.getMessage(MessageKeys.CART_ITEM_DELETE_SUCCESS))
                .build();
    }

    @PostMapping("/checkout")
    public ApiResponse<OrderResponse> checkout(@RequestBody @Valid CartCheckoutRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message(messageUtils.getMessage(MessageKeys.CART_CHECKOUT_SUCCESS))
                .result(cartService.checkout(request))
                .build();
    }
}
