package com.project.shopapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.project.shopapp.model.dto.request.OrderRequest;
import com.project.shopapp.model.dto.response.ApiResponse;
import com.project.shopapp.model.dto.response.OrderResponse;
import com.project.shopapp.service.impl.OrderServiceImpl;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class OrderController {
    OrderServiceImpl orderService;
    MessageUtils messageUtils;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_CREATE_SUCCESS))
                .result(orderService.createOrder(request))
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {

        return ApiResponse.<List<OrderResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_LIST_BY_USER_SUCCESS))
                .result(orderService.findByUserId(userId))
                .build();
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable Long orderId) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_DETAIL_SUCCESS))
                .result(orderService.getOrder(orderId))
                .build();
    }

    @PutMapping("/{orderId}")
    public ApiResponse<OrderResponse> updateOrder(
            @PathVariable Long orderId, @RequestBody @Valid OrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_UPDATE_SUCCESS))
                .result(orderService.updateOrder(orderId, request))
                .build();
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_DELETE_SUCCESS))
                .build();
    }
}
