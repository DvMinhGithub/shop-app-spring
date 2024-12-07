package com.project.shopapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dto.request.OderDetailRequest;
import com.project.shopapp.dto.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController {
    @PostMapping
    public ApiResponse<?> createOrderDetail(@RequestBody @Valid OderDetailRequest request) {

        return new ApiResponse<>(200, "Order created successfully", null);
    }

    @GetMapping("/{orderId}")
    public ApiResponse<?> getOrderDetail(@PathVariable Long orderId) {
        return new ApiResponse<>(200, "Get order successfully", null);
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<?> getOrderDetailByOrderId(@PathVariable Long orderId) {
        return new ApiResponse<>(200, "Get order successfully", null);
    }

    @PutMapping("/{orderId}")
    public ApiResponse<?> updateOrderDetail(@PathVariable Long orderId, @RequestBody @Valid OderDetailRequest request) {
        return new ApiResponse<>(200, "Order updated successfully", null);
    }

    @DeleteMapping("{orderId}")
    public ApiResponse<?> deleteOrder(@PathVariable Long orderId) {
        return new ApiResponse<>(200, "Order deleted successfully", null);
    }
}
