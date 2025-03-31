package com.project.shopapp.service;

import java.util.List;

import com.project.shopapp.model.dto.request.OrderRequest;
import com.project.shopapp.model.entity.Order;

public interface OrderService {
    Order createOrder(OrderRequest request);

    List<Order> findByUserId(Long userId);

    Order getOrder(Long id);

    Order updateOrder(Long id, OrderRequest request);

    void deleteOrder(Long id);
}
