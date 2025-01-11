package com.project.shopapp.service;

import java.util.List;

import com.project.shopapp.dto.request.OrderRequest;
import com.project.shopapp.model.Order;

public interface OrderService {
    Order createOrder(OrderRequest request);

    List<Order> findByUserId(Long userId);

    Order getOrder(Long id);

    Order updateOrder(Long id, OrderRequest request);

    void deleteOrder(Long id);
}
