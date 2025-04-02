package com.project.shopapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.shopapp.model.dto.request.OrderRequest;
import com.project.shopapp.model.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> findByUserId(Long userId);

    Page<OrderResponse> findAllByKeyword(String keyword, Pageable pageable);

    OrderResponse getOrder(Long id);

    OrderResponse updateOrder(Long id, OrderRequest request);

    void deleteOrder(Long id);
}
