package com.project.shopapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.project.shopapp.dto.request.OrderRequest;
import com.project.shopapp.dto.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @PostMapping
    public ApiResponse<?> createOrder(@RequestBody @Valid OrderRequest request) {

        return new ApiResponse<>(HttpStatus.OK.value(), "Order created successfully", null);
    }

    @GetMapping("{userId}")
    public ApiResponse<?> getAllOrders(@PathVariable Long userId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Get all orders successfully", null);
    }

    @PutMapping("{orderId}")
    public ApiResponse<?> updateOrder(@PathVariable Long orderId, @RequestBody @Valid OrderRequest request) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Order updated successfully", null);
    }

    @DeleteMapping("{orderId}")
    public ApiResponse<?> deleteOrder(@PathVariable Long orderId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Order deleted successfully", null);
    }
}
