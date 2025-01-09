package com.project.shopapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.project.shopapp.dto.request.OrderRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.entity.Order;
import com.project.shopapp.service.impl.OrderServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class OrderController {
    OrderServiceImpl orderService;

    @PostMapping
    public ApiResponse<Order> createOrder(@RequestBody @Valid OrderRequest request) {
        return ApiResponse.<Order>builder()
                .code(HttpStatus.OK.value())
                .message("Order created successfully")
                .result(orderService.createOrder(request))
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        return ApiResponse.<List<Order>>builder()
                .code(HttpStatus.OK.value())
                .message("Get orders by user id successfully")
                .result(orderService.findByUserId(userId))
                .build();
    }

    @GetMapping("/{orderId}")
    public ApiResponse<Order> getOrder(@PathVariable Long orderId) {
        return ApiResponse.<Order>builder()
                .code(HttpStatus.OK.value())
                .message("Get order detail successfully")
                .result(orderService.getOrder(orderId))
                .build();
    }

    @PutMapping("{orderId}")
    public ApiResponse<Order> updateOrder(@PathVariable Long orderId, @RequestBody @Valid OrderRequest request) {
        return ApiResponse.<Order>builder()
                .code(HttpStatus.OK.value())
                .message("Order updated successfully")
                .result(orderService.updateOrder(orderId, request))
                .build();
    }

    @DeleteMapping("{orderId}")
    public ApiResponse<?> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Order deleted successfully")
                .build();
    }
}
