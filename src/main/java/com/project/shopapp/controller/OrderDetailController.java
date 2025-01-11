package com.project.shopapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.project.shopapp.dto.request.OrderDetailRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.service.impl.OrderDetailServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/order-details")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class OrderDetailController {
    OrderDetailServiceImpl orderDetailService;

    @PostMapping
    public ApiResponse<?> createOrderDetail(@RequestBody @Valid OrderDetailRequest request) {
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Order created successfully")
                .result(orderDetailService.createOrderDetail(request))
                .build();
    }

    @GetMapping("/{orderId}")
    public ApiResponse<?> getOrderDetail(@PathVariable Long orderId) {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get order successfully")
                .result(orderDetailService.getOrderDetail(orderId))
                .build();
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<?> getOrderDetailByOrderId(@PathVariable Long orderId) {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get order successfully")
                .result(orderDetailService.getAllOrderDetails(orderId))
                .build();
    }

    @PutMapping("/{orderId}")
    public ApiResponse<?> updateOrderDetail(
            @PathVariable Long orderId, @RequestBody @Valid OrderDetailRequest request) {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Order updated successfully")
                .result(orderDetailService.updateOrderDetail(orderId, request))
                .build();
    }

    @DeleteMapping("{orderId}")
    public ApiResponse<?> deleteOrder(@PathVariable Long orderId) {
        orderDetailService.deleteOrderDetail(orderId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Order deleted successfully")
                .result(null)
                .build();
    }
}
