package com.project.shopapp.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.project.shopapp.dto.request.OrderRequest;
import com.project.shopapp.dto.request.OrderStatusUpdateRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.dto.response.OrderResponse;
import com.project.shopapp.exception.ForbiddenException;
import com.project.shopapp.security.CustomUserDetailsService;
import com.project.shopapp.service.OrderService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;
    MessageUtils messageUtils;
    CustomUserDetailsService customUserDetailsService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_CREATE_SUCCESS))
                .result(orderService.createOrder(customUserDetailsService.getCurrentUserId(), request))
                .build();
    }

    @GetMapping("/my")
    public ApiResponse<List<OrderResponse>> getMyOrders() {
        return ApiResponse.<List<OrderResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_LIST_BY_USER_SUCCESS))
                .result(orderService.findByUserId(customUserDetailsService.getCurrentUserId()))
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        boolean admin = customUserDetailsService.isCurrentUserAdmin();
        if (!admin && !customUserDetailsService.getCurrentUserId().equals(userId)) {
            throw new ForbiddenException(messageUtils.getMessage(MessageKeys.FORBIDDEN));
        }
        return ApiResponse.<List<OrderResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_LIST_BY_USER_SUCCESS))
                .result(orderService.findByUserId(userId))
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<Page<OrderResponse>> searchOrders(
            @RequestParam(defaultValue = "", name = "keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ApiResponse.<Page<OrderResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(orderService.findAllByKeyword(keyword, pageable))
                .build();
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable Long orderId) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_DETAIL_SUCCESS))
                .result(orderService.getOrderForUser(
                        orderId,
                        customUserDetailsService.getCurrentUserId(),
                        customUserDetailsService.isCurrentUserAdmin()))
                .build();
    }

    @PatchMapping("/{orderId}/cancel")
    public ApiResponse<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_CANCEL_SUCCESS))
                .result(orderService.cancelOrder(
                        orderId,
                        customUserDetailsService.getCurrentUserId(),
                        customUserDetailsService.isCurrentUserAdmin()))
                .build();
    }

    @PatchMapping("/{orderId}/status")
    public ApiResponse<OrderResponse> updateStatus(
            @PathVariable Long orderId, @RequestBody @Valid OrderStatusUpdateRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.ORDER_STATUS_UPDATE_SUCCESS))
                .result(orderService.updateStatus(orderId, request.getStatus()))
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
