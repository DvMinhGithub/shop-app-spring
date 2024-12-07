package com.project.shopapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dto.request.OrderRequest;
import com.project.shopapp.dto.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @PostMapping
    public ApiResponse<?> createOrder(@RequestBody @Valid OrderRequest request) {

        return new ApiResponse<>(200, "Order created successfully", null);
    }

    @GetMapping("{userId}")
    public ApiResponse<?> getAllOrders(@PathVariable Long userId) {
        return new ApiResponse<>(200, "Get all orders successfully", null);
    }

    @PutMapping("{orderId}")
    public ApiResponse<?> updateOrder(@PathVariable Long orderId, @RequestBody @Valid OrderRequest request) {
        return new ApiResponse<>(200, "Order updated successfully", null);
    }

    @DeleteMapping("{orderId}")
    public ApiResponse<?> deleteOrder(@PathVariable Long orderId) {
        return new ApiResponse<>(200, "Order deleted successfully", null);
    }
}
