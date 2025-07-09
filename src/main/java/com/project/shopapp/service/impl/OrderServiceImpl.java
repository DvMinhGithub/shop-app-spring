package com.project.shopapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.mapper.OrderMapper;
import com.project.shopapp.model.dto.request.CartItemRequest;
import com.project.shopapp.model.dto.request.OrderRequest;
import com.project.shopapp.model.dto.response.OrderResponse;
import com.project.shopapp.model.entity.Order;
import com.project.shopapp.model.entity.OrderDetail;
import com.project.shopapp.model.entity.Product;
import com.project.shopapp.model.entity.User;
import com.project.shopapp.model.enums.OrderStatus;
import com.project.shopapp.repository.OrderDetailRepository;
import com.project.shopapp.repository.OrderRepository;
import com.project.shopapp.repository.ProductRepository;
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
    OrderDetailRepository orderDetailRepository;
    ProductRepository productRepository;
    MessageUtils messageUtils;
    OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
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
        order.setActive(true);
        order.setStatus(OrderStatus.PENDING);

        orderRepository.save(order);

        for (CartItemRequest cartItemRequest : request.getCartItems()) {
            Product product = productRepository
                    .findById(cartItemRequest.getProductId())
                    .orElseThrow(() -> new DataNotFoundException(
                            messageUtils.getMessage(MessageKeys.PRODUCT_NOT_FOUND, cartItemRequest.getProductId())));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(cartItemRequest.getQuantity());
            orderDetail.setPrice(product.getPrice());
            orderDetail.setTotalMoney(product.getPrice() * cartItemRequest.getQuantity());
            orderDetail.setProduct(product);

            orderDetailRepository.save(orderDetail);
        }

        return orderMapper.toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> findByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<OrderResponse> findAllByKeyword(String keyword, Pageable pageable) {
        return orderRepository.findAllByKeyword(keyword, pageable).map(orderMapper::toOrderResponse);
    }

    @Override
    public OrderResponse getOrder(Long orderId) {
        return orderRepository
                .findById(orderId)
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_NOT_FOUND)));
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest request) {
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

        return orderMapper.toOrderResponse(orderRepository.save(existOrder));
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
