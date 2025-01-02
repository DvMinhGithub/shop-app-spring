package com.project.shopapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.project.shopapp.dto.request.OderDetailRequest;
import com.project.shopapp.dto.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController {
    @PostMapping
    public ApiResponse<?> createOrderDetail(@RequestBody @Valid OderDetailRequest request) {

        return new ApiResponse<>(HttpStatus.OK.value(), "Order created successfully", null);
    }

    @GetMapping("/{orderId}")
    public ApiResponse<?> getOrderDetail(@PathVariable Long orderId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Get order successfully", null);
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<?> getOrderDetailByOrderId(@PathVariable Long orderId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Get order successfully", null);
    }

    @PutMapping("/{orderId}")
    public ApiResponse<?> updateOrderDetail(@PathVariable Long orderId, @RequestBody @Valid OderDetailRequest request) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Order updated successfully", null);
    }

    @DeleteMapping("{orderId}")
    public ApiResponse<?> deleteOrder(@PathVariable Long orderId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Order deleted successfully", null);
    }
}
