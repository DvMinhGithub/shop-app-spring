package com.project.shopapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.project.shopapp.dto.request.OrderDetailRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.dto.response.OrderDetailResponse;
import com.project.shopapp.service.impl.OrderDetailServiceImpl;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order-details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailServiceImpl orderDetailService;
    private final MessageUtils messageUtils;

    @PostMapping
    public ApiResponse<OrderDetailResponse> createOrderDetail(@RequestBody @Valid OrderDetailRequest request) {
        return ApiResponse.<OrderDetailResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_DETAIL_CREATE_SUCCESS))
                .result(orderDetailService.createOrderDetail(request))
                .build();
    }

    @GetMapping("/{orderDetailId}")
    public ApiResponse<?> getOrderDetail(@PathVariable Long orderDetailId) {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_DETAIL_GET_SUCCESS))
                .result(orderDetailService.getOrderDetail(orderDetailId))
                .build();
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<?> getOrderDetailByOrderId(@PathVariable Long orderId) {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_DETAIL_LIST_BY_ORDER_SUCCESS))
                .result(orderDetailService.getAllOrderDetails(orderId))
                .build();
    }

    @PutMapping("/{orderDetailId}")
    public ApiResponse<OrderDetailResponse> updateOrderDetail(
            @PathVariable Long orderDetailId, @RequestBody @Valid OrderDetailRequest request) {
        return ApiResponse.<OrderDetailResponse>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_DETAIL_UPDATE_SUCCESS))
                .result(orderDetailService.updateOrderDetail(orderDetailId, request))
                .build();
    }

    @DeleteMapping("/{orderDetailId}")
    public ApiResponse<?> deleteOrderDetail(@PathVariable Long orderDetailId) {
        return ApiResponse.builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_DETAIL_DELETE_SUCCESS))
                .build();
    }
}
