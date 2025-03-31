package com.project.shopapp.service;

import java.util.List;

import com.project.shopapp.model.dto.request.OrderDetailRequest;
import com.project.shopapp.model.dto.response.OrderDetailResponse;

public interface OrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailRequest orderDetailRequest);

    OrderDetailResponse getOrderDetail(Long id);

    List<OrderDetailResponse> getAllOrderDetails(Long orderId);

    OrderDetailResponse updateOrderDetail(Long id, OrderDetailRequest orderDetailRequest);

    void deleteOrderDetail(Long id);
}
