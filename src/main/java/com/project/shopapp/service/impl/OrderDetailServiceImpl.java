package com.project.shopapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.shopapp.dto.request.OrderDetailRequest;
import com.project.shopapp.dto.response.OrderDetailResponse;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.mapper.OrderDetailMapper;
import com.project.shopapp.model.Order;
import com.project.shopapp.model.OrderDetail;
import com.project.shopapp.model.Product;
import com.project.shopapp.repository.OrderDetailRepository;
import com.project.shopapp.repository.OrderRepository;
import com.project.shopapp.repository.ProductRepository;
import com.project.shopapp.service.OrderDetailService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    OrderRepository orderRepository;
    ProductRepository productRepository;
    OrderDetailRepository orderDetailRepository;

    OrderDetailMapper orderDetailMapper;

    static String orderDetailNotFOund = "Order detail not found";

    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailRequest orderDetailRequest) {
        Order existOrder = orderRepository
                .findById(orderDetailRequest.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found"));

        Product existProduct = productRepository
                .findById(orderDetailRequest.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));

        OrderDetail orderDetail = orderDetailMapper.toOrderDetail(orderDetailRequest);

        orderDetail.setOrder(existOrder);
        orderDetail.setProduct(existProduct);

        return orderDetailMapper.toOrderDetailResponse(orderDetailRepository.save(orderDetail));
    }

    @Override
    public OrderDetailResponse getOrderDetail(Long id) {
        OrderDetail orderDetail =
                orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException(orderDetailNotFOund));

        return orderDetailMapper.toOrderDetailResponse(orderDetail);
    }

    @Override
    public List<OrderDetailResponse> getAllOrderDetails(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId).stream()
                .map(orderDetailMapper::toOrderDetailResponse)
                .toList();
    }

    @Override
    public OrderDetailResponse updateOrderDetail(Long id, OrderDetailRequest orderDetailRequest) {
        OrderDetail existOrderDetail =
                orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException(orderDetailNotFOund));

        Order existOrder = orderRepository
                .findById(orderDetailRequest.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found"));

        Product existProduct = productRepository
                .findById(orderDetailRequest.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));

        orderDetailMapper.updateOrderDetailFromRequest(orderDetailRequest, existOrderDetail);

        existOrderDetail.setOrder(existOrder);
        existOrderDetail.setProduct(existProduct);

        return orderDetailMapper.toOrderDetailResponse(orderDetailRepository.save(existOrderDetail));
    }

    @Override
    public void deleteOrderDetail(Long id) {
        OrderDetail existOrderDetail =
                orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException(orderDetailNotFOund));

        orderDetailRepository.delete(existOrderDetail);
    }
}
