package com.project.shopapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.shopapp.dto.request.OrderRequest;
import com.project.shopapp.dto.response.OrderResponse;
import com.project.shopapp.model.enums.OrderStatus;

public interface OrderService {
    OrderResponse createOrder(Long userId, OrderRequest request);

    List<OrderResponse> findByUserId(Long userId);

    Page<OrderResponse> findAllByKeyword(String keyword, Pageable pageable);

    OrderResponse getOrder(Long id);

    OrderResponse getOrderForUser(Long id, Long userId, boolean admin);

    OrderResponse updateOrder(Long id, OrderRequest request);

    OrderResponse updateStatus(Long id, OrderStatus status);

    OrderResponse cancelOrder(Long id, Long userId, boolean admin);

    void deleteOrder(Long id);
}
