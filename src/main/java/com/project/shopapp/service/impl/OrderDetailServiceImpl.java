package com.project.shopapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.mapper.OrderDetailMapper;
import com.project.shopapp.model.dto.request.OrderDetailRequest;
import com.project.shopapp.model.dto.response.OrderDetailResponse;
import com.project.shopapp.model.entity.Order;
import com.project.shopapp.model.entity.OrderDetail;
import com.project.shopapp.model.entity.Product;
import com.project.shopapp.repository.OrderDetailRepository;
import com.project.shopapp.repository.OrderRepository;
import com.project.shopapp.repository.ProductRepository;
import com.project.shopapp.service.OrderDetailService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    OrderRepository orderRepository;
    ProductRepository productRepository;
    OrderDetailRepository orderDetailRepository;
    MessageUtils messageUtils;
    OrderDetailMapper orderDetailMapper;

    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailRequest orderDetailRequest) {
        Order existOrder = orderRepository
                .findById(orderDetailRequest.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_NOT_FOUND)));

        Product existProduct = productRepository
                .findById(orderDetailRequest.getProductId())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.PRODUCT_NOT_FOUND)));

        OrderDetail orderDetail = orderDetailMapper.toOrderDetail(orderDetailRequest);

        orderDetail.setOrder(existOrder);
        orderDetail.setProduct(existProduct);

        return orderDetailMapper.toOrderDetailResponse(orderDetailRepository.save(orderDetail));
    }

    @Override
    public OrderDetailResponse getOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_DETAIL_NOT_FOUND)));

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
        OrderDetail existOrderDetail = orderDetailRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_DETAIL_NOT_FOUND)));

        Order existOrder = orderRepository
                .findById(orderDetailRequest.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_NOT_FOUND)));

        Product existProduct = productRepository
                .findById(orderDetailRequest.getProductId())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.PRODUCT_NOT_FOUND)));

        orderDetailMapper.updateOrderDetailFromRequest(orderDetailRequest, existOrderDetail);

        existOrderDetail.setOrder(existOrder);
        existOrderDetail.setProduct(existProduct);

        return orderDetailMapper.toOrderDetailResponse(orderDetailRepository.save(existOrderDetail));
    }

    @Override
    public void deleteOrderDetail(Long id) {
        OrderDetail existOrderDetail = orderDetailRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_DETAIL_NOT_FOUND)));

        orderDetailRepository.delete(existOrderDetail);
    }
}
