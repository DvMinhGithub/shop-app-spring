package com.project.shopapp.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.shopapp.dto.response.OrderResponse;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.exception.ForbiddenException;
import com.project.shopapp.mapper.OrderMapper;
import com.project.shopapp.model.entity.Order;
import com.project.shopapp.model.enums.OrderStatus;
import com.project.shopapp.model.enums.PaymentStatus;
import com.project.shopapp.repository.OrderRepository;
import com.project.shopapp.service.PaymentService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    MessageUtils messageUtils;

    @Override
    public OrderResponse getPayment(Long orderId, Long userId, boolean admin) {
        Order order = findActiveOrder(orderId);
        checkOwner(order, userId, admin);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse mockPay(Long orderId, Long userId, boolean admin) {
        Order order = findActiveOrder(orderId);
        checkOwner(order, userId, admin);

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot pay a cancelled order");
        }
        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            return orderMapper.toOrderResponse(order);
        }

        order.setPaymentStatus(PaymentStatus.PAID);
        order.setPaidAt(LocalDateTime.now());
        order.setTransactionId("MOCK-" + UUID.randomUUID());
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderResponse updateStatus(Long orderId, PaymentStatus status, String transactionId) {
        Order order = findActiveOrder(orderId);
        order.setPaymentStatus(status);
        order.setTransactionId(transactionId);
        order.setPaidAt(status == PaymentStatus.PAID ? LocalDateTime.now() : null);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    private Order findActiveOrder(Long orderId) {
        return orderRepository
                .findById(orderId)
                .filter(Order::isActive)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_NOT_FOUND)));
    }

    private void checkOwner(Order order, Long userId, boolean admin) {
        if (!admin && !order.getUser().getId().equals(userId)) {
            throw new ForbiddenException(messageUtils.getMessage(MessageKeys.FORBIDDEN));
        }
    }
}
