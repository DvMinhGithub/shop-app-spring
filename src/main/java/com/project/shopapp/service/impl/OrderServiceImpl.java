package com.project.shopapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.shopapp.dto.request.OrderRequest;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.mapper.OrderMapper;
import com.project.shopapp.model.Order;
import com.project.shopapp.model.User;
import com.project.shopapp.repository.OrderRepository;
import com.project.shopapp.repository.UserRepository;
import com.project.shopapp.service.OrderService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {
    UserRepository userRepository;
    OrderRepository orderRepository;
    MessageUtils messageUtils;
    OrderMapper orderMapper;

    @Override
    public Order createOrder(OrderRequest request) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.USER_NOT_FOUND)));

        LocalDateTime shippingDate =
                request.getShippingDate() == null ? LocalDateTime.now().plusDays(1) : request.getShippingDate();

        if (shippingDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    messageUtils.getMessage(MessageKeys.SHIPPING_DATE_MUST_BE_GREATER_THAN_ORDER_DATE));
        }

        Order order = orderMapper.toOrder(request);
        order.setUser(user);
        order.setShippingDate(shippingDate);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public Order updateOrder(Long id, OrderRequest request) {
        Order existOrder = orderRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_NOT_FOUND)));

        User existUser = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.USER_NOT_FOUND)));

        LocalDateTime shippingDate =
                request.getShippingDate() == null ? existOrder.getShippingDate() : request.getShippingDate();

        if (shippingDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    messageUtils.getMessage(MessageKeys.SHIPPING_DATE_MUST_BE_GREATER_THAN_ORDER_DATE));
        }

        orderMapper.updateOrderFromRequest(request, existOrder);

        existOrder.setUser(existUser);
        existOrder.setShippingDate(shippingDate);
        // existOrder.setTotalPrice(request.getTotalPrice());
        // existOrder.setOrderStatus(request.getOrderStatus());

        return orderRepository.save(existOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        Order existOrder = orderRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_NOT_FOUND)));

        existOrder.setActive(false);
        orderRepository.save(existOrder);
    }
}
